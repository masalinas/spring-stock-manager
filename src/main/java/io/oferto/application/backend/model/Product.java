package io.oferto.application.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "product")
@ApiModel(description = "Class representing a product tracked by the application.")
public class Product extends AbstractEntity {
	public enum Family {
		PERISHABLE("Perishable"),
		ELECTRONICS("Electronic"),
	    FASHION("Fashion");

	    public final String label;

	    private Family(String label) {
	        this.label = label;
	    }
	    
	    @Override
		public String toString() {
	    	return label;
	    }
	}

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	@NotNull(message = "Warehouse is required")
	@ApiModelProperty(notes = "Product warehouse.", example = "Accme", required = true, position = 2)
	private Warehouse warehouse;
	
	@ApiModelProperty(notes = "Product name.", example = "Accme", required = true, position = 3)
	@Size(max = 45)
	private String name;
    
	@ApiModelProperty(notes = "Product description.", example = "Accme", required = false, position = 4)
	@Size(max = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Family is required")
	@ApiModelProperty(notes = "Product family.", example = "Fashion", required = true, position = 5)
	private Family family;
		
	@NotNull(message = "Price is required")
	@ApiModelProperty(notes = "Product price.", example = "Fashion", required = true, position = 6)
	private Double price;
	
	@NotNull(message = "Active is required")
	@ApiModelProperty(notes = "Product status.", example = "true", required = true, position = 7)
	private boolean active = true;
	  
	public Product() {
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    public Family getFamily() {
        return family;
    }
    public void setFamily(Family family) {
        this.family = family;
    }
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
