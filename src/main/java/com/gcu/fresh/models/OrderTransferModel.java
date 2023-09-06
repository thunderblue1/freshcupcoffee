package com.gcu.fresh.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("order")
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

	public OrderTransferModel(Long itemNumber, Long purchaseNumber, Integer quantity) {
		super();
		this.itemNumber = itemNumber;
		this.purchaseNumber = purchaseNumber;
		this.quantity = quantity;
	}
	
	public OrderTransferModel(Long orderNumber, Long itemNumber, Long purchaseNumber, Integer quantity) {
		super();
		this.orderNumber = orderNumber;
		this.itemNumber = itemNumber;
		this.purchaseNumber = purchaseNumber;
		this.quantity = quantity;
	}
}
