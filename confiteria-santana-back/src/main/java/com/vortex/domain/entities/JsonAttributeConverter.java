package com.vortex.domain.entities;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

public abstract class JsonAttributeConverter<T> implements AttributeConverter<T, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final Class<T> clazz;

	public JsonAttributeConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (Exception e) {
			throw new RuntimeException("Error serializing object to JSON", e);
		}
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		try {
			// Fuerza a parsear como array y tomar el primero
			List<T> list = objectMapper.readValue(dbData,
					objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
			return list.get(0); // ⚠️ solo si sabes que siempre hay uno
		} catch (Exception e) {
			throw new RuntimeException("Error deserializing JSON array", e);
		}
	}
}
