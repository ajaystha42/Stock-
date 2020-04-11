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
@Table(name = "currentstock")
public class CurrentStock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "product")
	private ProductDetails product;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "unit")
	private Unit unit;

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

	public ProductDetails getProduct() {
		return product;
	}

	public void setProduct(ProductDetails product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public MainStock getMainStock() {
		return mainStock;
	}

	public void setMainStock(MainStock mainStock) {
		this.mainStock = mainStock;
	}

	@Column(name = "quantity")
	private String quantity;

	@ManyToOne
	@JoinColumn(name = "main_stock_id")
	private MainStock mainStock;
	

}
