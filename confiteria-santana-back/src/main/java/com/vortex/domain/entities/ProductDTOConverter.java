package com.vortex.domain.entities;

import com.vortex.domain.dto.ProductDTO;

import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ProductDTOConverter extends JsonAttributeConverter<ProductDTO> {
	public ProductDTOConverter( ) {
		super(ProductDTO.class);
	}
	
}
