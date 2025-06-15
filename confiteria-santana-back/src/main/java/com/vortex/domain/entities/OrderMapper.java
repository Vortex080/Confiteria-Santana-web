package com.vortex.domain.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vortex.domain.dto.AddressDTO;
import com.vortex.domain.dto.OrderDTO;

public class OrderMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setTotal(order.getTotal());

        dto.setUserId(order.getUser().getId());
        dto.setUsername(order.getUser().getUsername());

        dto.setPaymentMethodId(order.getPaymentMethod().getId());
        dto.setPaymentMethodName(order.getPaymentMethod().getBrand());

        if (order.getSale() != null) {
            dto.setSaleId(order.getSale().getId()); // 🔁 Corrección aquí
        }

        dto.setCreated_at(order.getCreated_at());

        try {
            String shipping = order.getShippingAddress();
            String billing = order.getBillingAddress();

            if (shipping != null && !shipping.isBlank()) {
                dto.setShipping(mapper.readValue(shipping, AddressDTO.class));
            }

            if (billing != null && !billing.isBlank()) {
                dto.setBillingAddress(mapper.readValue(billing, AddressDTO.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir JSON de direcciones", e);
        }

        return dto;
    }

    // ✅ Método para convertir una lista de Orders a una lista de OrderDTOs
    public static List<OrderDTO> toDTOs(List<Order> orders) {
        return orders.stream()
                     .map(OrderMapper::toDTO)
                     .collect(Collectors.toList());
    }
}
