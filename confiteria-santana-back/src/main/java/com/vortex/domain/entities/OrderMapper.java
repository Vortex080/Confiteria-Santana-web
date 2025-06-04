package com.vortex.domain.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.vortex.domain.dto.OrderDTO;

/**
 * Conversor manual de entidades Order a OrderDTO sin usar MapStruct.
 */
public class OrderMapper {

	/**
	 * Convierte una entidad Order a su DTO.
	 * 
	 * @param order entidad Order
	 * @return OrderDTO con los mismos valores básicos y solo los IDs de relaciones
	 */
	public static OrderDTO toDTO(Order order) {
		if (order == null) {
			return null;
		}
		OrderDTO dto = new OrderDTO();
		dto.setId(order.getId());
		dto.setTotal(order.getTotal());
		dto.setShipping(order.getShipping());
		dto.setUser(order.getUser() != null ? order.getUser().getId() : null);
		dto.setShipping(order.getShippingAddress() != null ? order.getShippingAddress().getId() : null);
		dto.setBillingAddress(order.getBillingAddress() != null ? order.getBillingAddress().getId() : null);
		dto.setPaymentMethod(order.getPaymentMethod() != null ? order.getPaymentMethod().getId() : null);
		dto.setSale(order.getSale() != null ? order.getSale().getId() : null);
		dto.setCreated_at(order.getCreated_at());
		return dto;
	}

	/**
	 * Convierte una lista de entidades Order a una lista de OrderDTO.
	 * 
	 * @param orders lista de entidades Order
	 * @return lista de OrderDTO
	 */
	public static List<OrderDTO> toDTOs(List<Order> orders) {
		return orders.stream().map(OrderMapper::toDTO).collect(Collectors.toList());
	}
}
