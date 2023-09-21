package com.gcu.fresh.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("single_order")
public class OrderTransferModel {
	@Column("order_number")
	@Id
	private Long orderNumber;
	
	@Column("item_number")
	private Long itemNumber;	
	
	@Column("purchase_number")
	private Long purchaseNumber;
	
	@Column("quantity")
	private Integer quantity;
	
	public OrderTransferModel() {
		super();
	}

	public OrderTransferModel(Long itemNumber, Long purchaseNumber, Integer quantity) {
		super();
		this.itemNumber = itemNumber;
		this.purchaseNumber = purchaseNumber;
		this.quantity = quantity;
	}
	
	public OrderTransferModel(Long orderNumber, Long purchaseNumber, Long itemNumber, Integer quantity) {
		super();
		this.orderNumber = orderNumber;
		this.itemNumber = itemNumber;
		this.purchaseNumber = purchaseNumber;
		this.quantity = quantity;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Long itemNumber) {
		this.itemNumber = itemNumber;
	}

	public Long getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(Long purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
