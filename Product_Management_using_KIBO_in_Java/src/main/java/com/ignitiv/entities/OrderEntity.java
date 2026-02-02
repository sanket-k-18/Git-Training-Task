package com.ignitiv.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OrderEntity {
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private UUID orderId;
	
	@Column(name = "kibo_order_id")
	private String kiboOrderId;
	
	@Column(name = "customer_id")
	private Integer customerAccountId;
	
	@Column(name = "order_number")
	private Integer orderNumber;
	
	private String status;
	
	@Column(name = "total_amount")
	private Double totalAmount;
	
	private String currency;
	
	@Column(name = "order_date")
	private LocalDateTime  orderDate;
	
	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public String getKiboOrderId() {
		return kiboOrderId;
	}

	public void setKiboOrderId(String kiboOrderId) {
		this.kiboOrderId = kiboOrderId;
	}



	public Integer getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(Integer customerAccountId) {
		this.customerAccountId = customerAccountId;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	
	
	
	
	
}
