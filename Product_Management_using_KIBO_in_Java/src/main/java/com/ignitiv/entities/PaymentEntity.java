package com.ignitiv.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class PaymentEntity {
	@Id
	@GeneratedValue
	@Column(name = "payment_id")
	private UUID paymentId;
	
	@Column(name = "kibo_payment_id")
	private String kiboPaymentId;
	
	@Column(name = "order_id")
	private String orderId;
	
	@Column(name = "payment_type")
	private String paymentType;
	
	private String status;
	
	private Double amount;
	
	private String currency;

	public UUID getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}

	public String getKiboPaymentId() {
		return kiboPaymentId;
	}

	public void setKiboPaymentId(String kiboPaymentId) {
		this.kiboPaymentId = kiboPaymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	
}
