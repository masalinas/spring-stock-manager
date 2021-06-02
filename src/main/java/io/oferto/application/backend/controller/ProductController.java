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

import io.oferto.application.backend.model.Product;
import io.oferto.application.backend.model.Warehouse;
import io.oferto.application.backend.service.ProductService;
import io.oferto.application.backend.service.WarehouseService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Api(value = "Set of endpoints for CRUD Product operations")
public class ProductController {
	@Autowired
	WarehouseService warehouseService;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("products")
	@ApiOperation(value = "Get all products", nickname = "findAllProduct")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<List<Product>> findAll() {
		try {
		   List<Product> products = productService.findAll();
					
		    return new ResponseEntity<>(products, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("products/warehouses/{warehouseId}")
	@ApiOperation(value = "Get all products by Warehouse Id", nickname = "findAllByWarehouseIdProduct")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<List<Product>> findAllByWarehouseId(@ApiParam(value = "The warehouse id", required = true) @PathVariable Long warehouseId) {
		try {
		   Optional<List<Product>> products = productService.findAllByWarehouseId(warehouseId);
					
		    return new ResponseEntity<>(products.get(), HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("products/{id}")
	@ApiOperation(value = "Get product by Id", nickname = "findByIdProduct")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Product> findById(@ApiParam(value = "The products id", required = true)  @PathVariable Long id) {
		try {
		    Optional<Product> product = productService.findById(id);
					
		    return new ResponseEntity<>(product.get(), HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@PostMapping("products")
	@ApiOperation(value = "Create a product", nickname = "saveProduct")
	@ResponseStatus(HttpStatus.CREATED)
	@RolesAllowed({"admin"})
	public ResponseEntity<Product> save(@ApiParam(value="Product entity") @RequestBody final Product product) {
		try {
		    Product _product = productService.save(product);
		    return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

    @PostMapping("/products/warehouses/{warehouseId}")
    @ApiOperation(value = "Create a product", nickname = "createProduct")
	@ResponseStatus(HttpStatus.CREATED)
	@RolesAllowed({"admin"})
    public ResponseEntity<Product> create(@PathVariable (value = "warehouseId") Long warehouseId, @RequestBody Product product) {
    	try {
    		Optional<Warehouse> warehouse = warehouseService.findById(warehouseId);
    		
    		if (!warehouse.isPresent())
    			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    		
			product.setWarehouse(warehouse.get());
			
			return save(product);            
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}    
    }
    
	@DeleteMapping("products/{id}")
	@ApiOperation(value = "Delete a product", nickname = "deleteProduct")
	@RolesAllowed({"admin"})
	public ResponseEntity<HttpStatus> delete(@ApiParam(value = "The product id", required = true) @PathVariable Long id) {		
		try {
			productService.deleteById(id);
			
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Count number product", nickname = "countProduct")
	@GetMapping("products/count")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Long> count() {
		try {
			long result = productService.count();
			
		    return new ResponseEntity<Long>(result, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
