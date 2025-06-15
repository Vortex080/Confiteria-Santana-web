package com.vortex.domain.dto;

import java.time.LocalDateTime;

import com.vortex.domain.entities.Address;
import com.vortex.domain.entities.Sale;
import com.vortex.domain.entities.User;

public class OrderCreateDTO {
	private Long id;
    private User user;
    private Address shipping;
    private Address billingAddress;
    private PaymentMethodDTO paymentMethod;
    private Long total;
    private Sale sale;
    private LocalDateTime created_at;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Address getShipping() {
		return shipping;
	}
	public void setShipping(Address shipping) {
		this.shipping = shipping;
	}
	public Address getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}
	public PaymentMethodDTO getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethodDTO paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
    
    
}
