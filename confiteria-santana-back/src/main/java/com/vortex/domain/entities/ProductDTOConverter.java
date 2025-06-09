package com.vortex.domain.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.domain.dto.ProductDTO;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ProductDTOConverter implements AttributeConverter<ProductDTO, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(ProductDTO attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error serializing ProductDTO", e);
		}
	}

	@Override
	public ProductDTO convertToEntityAttribute(String dbData) {
		try {
			JsonNode node = objectMapper.readTree(dbData);

			// Si es un array, tomar el primer elemento
			if (node.isArray() && node.size() > 0) {
				return objectMapper.treeToValue(node.get(0), ProductDTO.class);
			}

			// Si es un objeto
			return objectMapper.treeToValue(node, ProductDTO.class);

		} catch (Exception e) {
			throw new RuntimeException("Error deserializing ProductDTO", e);
		}
	}
}
