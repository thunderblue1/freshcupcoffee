package com.gcu.fresh.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Table("item")
public class ProductModel {
	@Column("item_number")
	@Id
	private Long itemNumber;
	
	@Column("name")
    @NotNull(message="Product name is a required field")
    @Size(min=1, max=254, message="Product Name must be between 1 and 254 characters")
	private String name;
	
	@Column("description")
    @NotNull(message="Description is a required field")
    @Size(min=1, max=3000, message="Description must be between 1 and 3000 characters")
	private String description;
	
	@Column("price")
	@NotNull(message="Price is a required field")
    @Min(message="Price in pennies must be between 1 and 1,000,000,000", value = 0)
    @Max(message="Price in pennies must be between 1 and 1,000,000,000", value = 1000000000)
	private Integer price;
	
	@Column("photo")
	@NotNull(message="Image name is a required field")
    @Size(min=1, max=254, message="Image name must be between 1 and 254 characters")
	private String photo;
	
	public ProductModel() {
		super();
		this.itemNumber = null;
		this.name = "";
		this.description = "";
		this.price = null;
		this.photo ="";
	}

	public ProductModel(Long itemNumber,
			@NotNull(message = "Product name is a required field") @Size(min = 1, max = 254, message = "Product Name must be between 1 and 254 characters") String name,
			@NotNull(message = "Description is a required field") @Size(min = 1, max = 3000, message = "Description must be between 1 and 3000 characters") String description,
			@NotNull(message = "Price is a required field") @Min(message = "Price in pennies must be between 1 and 1,000,000,000", value = 0) @Max(message = "Price in pennies must be between 1 and 1,000,000,000", value = 1000000000) Integer price,
			@NotNull(message = "Image name is a required field") @Size(min = 1, max = 254, message = "Image name must be between 1 and 254 characters") String photo) {
		super();
		this.itemNumber = itemNumber;
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
	
}
