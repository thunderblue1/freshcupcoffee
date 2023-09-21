package com.gcu.fresh.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("purchase")
public class PurchaseModel {
	@Column("purchase_number")
	@Id
	private Long purchaseNumber;
	
	@Column("name_on_card")
    @NotNull(message="Name on credit card is a required field")
    @Size(min=1, max=60, message="Name must be between 1 and 60 characters")
	private String nameOnCard;
	
	@Column("credit_card")
    @NotNull(message="Credit card number is a required field")
    @Size(min=1, max=30, message="Credit card number must be between 1 and 30 characters")
	private String creditCard;
	
	@Column("email")
    @NotNull(message="Name on credit card is a required field")
    @Size(min=1, max=254, message="Name must be between 1 and 254 characters")
	private String email;
	
	@Column("total_price")
	private Integer totalPrice;
	
	@Column("table_number")
	@NotNull(message="Table number is a required field")
    @Min(message="Table number must be between 1 and 200", value = 0)
    @Max(message="Table number must be between 1 and 200", value = 200)
	private Integer tableNumber;

	public PurchaseModel() {
		super();
	}

	public PurchaseModel(
			@NotNull(message = "Name on credit card is a required field") @Size(min = 1, max = 60, message = "Name must be between 1 and 60 characters") String nameOnCard,
			@NotNull(message = "Credit card number is a required field") @Size(min = 1, max = 30, message = "Credit card number must be between 1 and 30 characters") String creditCard,
			@NotNull(message = "Name on credit card is a required field") @Size(min = 1, max = 254, message = "Name must be between 1 and 254 characters") String email,
			@NotNull(message = "Price is a required field") @Min(message = "Price in pennies must be between 1 and 1,000,000,000", value = 0) @Max(message = "Price in pennies must be between 1 and 1,000,000,000", value = 1000000000) Integer tableNumber) {
		super();
		this.nameOnCard = nameOnCard;
		this.creditCard = creditCard;
		this.email = email;
		this.tableNumber = tableNumber;
	}

	public PurchaseModel(Long purchaseNumber,
			@NotNull(message = "Name on credit card is a required field") @Size(min = 1, max = 60, message = "Name must be between 1 and 60 characters") String nameOnCard,
			@NotNull(message = "Credit card number is a required field") @Size(min = 1, max = 30, message = "Credit card number must be between 1 and 30 characters") String creditCard,
			@NotNull(message = "Name on credit card is a required field") @Size(min = 1, max = 254, message = "Name must be between 1 and 254 characters") String email,
			Integer totalPrice,
			@NotNull(message = "Price is a required field") @Min(message = "Price in pennies must be between 1 and 1,000,000,000", value = 0) @Max(message = "Price in pennies must be between 1 and 1,000,000,000", value = 1000000000) Integer tableNumber) {
		super();
		this.purchaseNumber = purchaseNumber;
		this.nameOnCard = nameOnCard;
		this.creditCard = creditCard;
		this.email = email;
		this.totalPrice = totalPrice;
		this.tableNumber = tableNumber;
	}

	public Long getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(Long purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}
	
}

