package com.ignitiv.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class OrderItemEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "order_item_id")
	private UUID orderItemId;
	
	@Column(name = "order_id")
	private UUID orderId;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "unit_price")
	private Double unitPrice;
	
	
	
}
