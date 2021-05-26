package io.oferto.application.backend.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "Class representing a warehouse tracked by the application.")
public class Warehouse extends AbstractEntity {
	@NotNull(message = "Warehouse name cannot be null")
    @ApiModelProperty(notes = "Name of the warehouse.", example = "Accme", required = true, position = 2)
	private String name;
	
	@ApiModelProperty(notes = "Address of the warehouse.", example = "C/ Profesor Potter", required = false, position = 3)
	private String address;
	
	@NotNull(message = "Longitude is required")
	@ApiModelProperty(notes = "Longitude of the warehouse.", example = "40.4378698", required = true, position = 4)
	private Double longitude;
	
	@NotNull(message = "Latitude is required")
	@ApiModelProperty(notes = "Latitude of the warehouse.", example = "-3.819619", required = true, position = 5)
	private Double latitude;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
}
