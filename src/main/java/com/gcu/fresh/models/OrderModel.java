package com.gcu.fresh.models;

public class OrderModel {
	private Long itemNumber;	
	private Long purchaseNumber;
	private Long orderNumber;
	private Integer quantity;
	private String name;
	private String description;
	private Integer price;
	private String photo;
	
	public OrderModel() {
		super();
	}

	public OrderModel(Long itemNumber, Long purchaseNumber, Integer quantity, String name, String description,
			Integer price, String photo) {
		super();
		this.itemNumber = itemNumber;
		this.purchaseNumber = purchaseNumber;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.price = price;
		this.photo = photo;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}
}
