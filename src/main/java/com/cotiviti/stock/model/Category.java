package com.cotiviti.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "if_deleted")
	private String ifDeleted; 
	
	
	public String getIfDeleted() {
		return ifDeleted;
	}

	public void setIfDeleted(String ifDeleted) {
		this.ifDeleted = ifDeleted;
	}

	public Category() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
