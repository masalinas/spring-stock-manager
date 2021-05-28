package io.oferto.application.backend.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "stock")
@ApiModel(description = "Class representing a stock tracked by the application.")
public class Stock extends AbstractEntity {
	public enum Status {
		STORED("Stored"),
		RESERVED("Reserved");

	    public final String label;

	    private Status(String label) {
	        this.label = label;
	    }
	    
	    @Override
		public String toString() {
	    	return label;
	    }
	}
	
	@ManyToOne
	@JoinColumn(name = "product_id")	
	@NotNull(message = "Product is required")
	private Product product;
			
	private LocalDate expirationDate;
		
	private String lot;
	
	private String serialNumber;	
	
	@NotNull
	@NotNull(message = "Quantity is required")
	private Double quantity;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Status is required")
	private Status status;	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
