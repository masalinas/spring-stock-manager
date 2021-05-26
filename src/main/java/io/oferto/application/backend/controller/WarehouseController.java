package io.oferto.application.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.oferto.application.backend.model.Warehouse;
import io.oferto.application.backend.service.WarehouseService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Api(value = "Set of endpoints for CRUD Warehouse operations")
public class WarehouseController {
	@Autowired
	WarehouseService warehouseService;
	
	@GetMapping("warehouses")
	@ApiOperation(value = "Get all warehouses", nickname = "findAll")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<List<Warehouse>> findAll() {
		try {
		   List<Warehouse> warehouses = warehouseService.findAll();
					
		    return new ResponseEntity<>(warehouses, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("warehouses/{id}")
	@ApiOperation(value = "Get warehouse by Id", nickname = "findById")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Warehouse> findById(@ApiParam(value = "The warehouse id", required = true)  @PathVariable Long id) {
		try {
		    Optional<Warehouse> warehouse = warehouseService.findById(id);
					
		    return new ResponseEntity<>(warehouse.get(), HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@PostMapping("warehouses")
	@ApiOperation(value = "Create a warehouse", nickname = "save")
	@ResponseStatus(HttpStatus.CREATED)
	@RolesAllowed({"admin"})
	public ResponseEntity<Warehouse> save(@ApiParam(value="Warehouse entity") @RequestBody final Warehouse warehouse) {
		try {
		    Warehouse _warehouse = warehouseService.save(warehouse);
		    return new ResponseEntity<>(_warehouse, HttpStatus.CREATED);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@DeleteMapping("warehouses/{id}")
	@ApiOperation(value = "Delete a warehouse", nickname = "delete")
	@RolesAllowed({"admin"})
	public ResponseEntity<HttpStatus> delete(@ApiParam(value = "The warehouse id", required = true) @PathVariable Long id) {		
		try {
			warehouseService.deleteById(id);
			
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Count number warehousea", nickname = "count")
	@GetMapping("warehouses/count")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Long> count() {
		try {
			long result = warehouseService.count();
			
		    return new ResponseEntity<Long>(result, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
