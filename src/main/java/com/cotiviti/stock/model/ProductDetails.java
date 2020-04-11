package com.cotiviti.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "productDetails")
public class ProductDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	
	@Column(name = "price")
	private String price;
	
	
	@Column(name = "product_name")
	private String productName;


	@Column(name = "if_deleted")
	private String ifDeleted; 
	
	
	
	public String getIfDeleted() {
		return ifDeleted;
	}


	public void setIfDeleted(String ifDeleted) {
		this.ifDeleted = ifDeleted;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	

	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}



	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	

	
	
}
