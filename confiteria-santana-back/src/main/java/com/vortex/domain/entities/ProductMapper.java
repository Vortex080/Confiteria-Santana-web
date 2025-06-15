package com.vortex.domain.entities;


import com.vortex.domain.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        // añade más campos si lo necesitas
        return dto;
    }

    public static Product fromDTO(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setUnit(dto.getUnit());

        // Convertir AlergenDTO a Alergens
        if (dto.getAlergens() != null) {
            List<Alergens> alergens = dto.getAlergens().stream()
                    .map(alergenDTO -> {
                        Alergens a = new Alergens();
                        a.setId(alergenDTO.getId());
                        a.setName(alergenDTO.getName());
                        a.setPhoto(alergenDTO.getPhoto());
                        return a;
                    })
                    .toList();
            product.setAlergens(new ArrayList<>(alergens));
        }


        // Convertir CategoryDTO a Category
        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setId(dto.getCategory().getId());
            category.setName(dto.getCategory().getName());
            category.setDescription(dto.getCategory().getDescription());
            product.setCategory(category);
        }

        // Convertir ProductPhotoDTO a ProductPhoto
        if (dto.getPhotos() != null) {
            List<ProductPhoto> photos = dto.getPhotos().stream()
                    .map(photoDTO -> {
                        ProductPhoto photo = new ProductPhoto();
                        photo.setId(photoDTO.getId());
                        photo.setUrl(photoDTO.getUrl());
                        photo.setAltText(photoDTO.getAltText());
                        photo.setProduct(product); // vincular al producto
                        return photo;
                    })
                    .toList();
            product.setPhotos(photos);
        }

        return product;
    }

}
