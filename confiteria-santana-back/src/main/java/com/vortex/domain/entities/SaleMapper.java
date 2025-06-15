package com.vortex.domain.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.vortex.domain.dto.SaleDTO;
import com.vortex.domain.dto.SaleLineDTO;

public class SaleMapper {

    public static SaleDTO toDTO(Sale sale) {
        if (sale == null) return null;

        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setDate(sale.getDate());
        dto.setMetodoPago(sale.getMetodoPago());
        dto.setTotal(sale.getTotal());

        if (sale.getLine() != null) {
            List<SaleLineDTO> lines = sale.getLine().stream().map(line -> {
                SaleLineDTO lineDTO = new SaleLineDTO();
                lineDTO.setId(line.getId());
                lineDTO.setCuantity(line.getCuantity());
                lineDTO.setPrice(line.getPrice());
                lineDTO.setSubtotal(line.getSubtotal());
                lineDTO.setProduct(line.getProduct()); // ✅ se guarda el producto completo

                return lineDTO;
            }).collect(Collectors.toList());

            dto.setLine(lines);
        }

        return dto;
    }
}

