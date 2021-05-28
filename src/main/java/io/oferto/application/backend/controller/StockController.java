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
import io.oferto.application.backend.model.Stock;
import io.oferto.application.backend.service.ProductService;
import io.oferto.application.backend.service.StockService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Api(value = "Set of endpoints for CRUD Stock operations")
public class StockController {	
	@Autowired
	ProductService productService;
	
	@Autowired
	StockService stockService;
		
	@GetMapping("stocks")
	@ApiOperation(value = "Get all stocks", nickname = "findAll")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<List<Stock>> findAll() {
		try {
		   List<Stock> stocks = stockService.findAll();
					
		    return new ResponseEntity<>(stocks, HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("stocks/warehouses/{warehouseId}")
	@ApiOperation(value = "Get all stocks by Warehouse Id", nickname = "findByWarehouseId")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<List<Stock>> findAllByWarehouseId(@ApiParam(value = "The warehouse id", required = true) @PathVariable Long warehouseId) {
		try {
		   Optional<List<Stock>> stocks = stockService.findAllByWarehouseId(warehouseId);
					
		    return new ResponseEntity<>(stocks.get(), HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@GetMapping("stocks/{id}")
	@ApiOperation(value = "Get stock by Id", nickname = "findById")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Stock> findById(@ApiParam(value = "The stock id", required = true)  @PathVariable Long id) {
		try {
		    Optional<Stock> stock = stockService.findById(id);
					
		    return new ResponseEntity<>(stock.get(), HttpStatus.OK);
		  } catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		  }
	}
	
	@PostMapping("stocks")
	@ApiOperation(value = "Create a stock", nickname = "save")
	@ResponseStatus(HttpStatus.CREATED)
	@RolesAllowed({"admin"})
	public ResponseEntity<Stock> save(@ApiParam(value="Stock entity") @RequestBody final Stock stock) {
		try {
		    Stock _stock = stockService.save(stock);
		    return new ResponseEntity<>(_stock, HttpStatus.CREATED);
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@PostMapping("/stocks/products/{productId}")
    @ApiOperation(value = "Create a stock", nickname = "create")
	@ResponseStatus(HttpStatus.CREATED)
	@RolesAllowed({"admin"})
    public ResponseEntity<Stock> create(@PathVariable (value = "productId") Long productId, @RequestBody Stock stock) {
    	try {
    		Optional<Product> product = productService.findById(productId);
    		
    		if (!product.isPresent())
    			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    		
			stock.setProduct(product.get());
			
			return save(stock);            
		} catch (Exception e) {
		    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}    
    }
	
	@DeleteMapping("stocks/{id}")
	@ApiOperation(value = "Delete a stock", nickname = "delete")
	@RolesAllowed({"admin"})
	public ResponseEntity<HttpStatus> delete(@ApiParam(value = "The stock id", required = true) @PathVariable Long id) {		
		try {
			stockService.deleteById(id);
			
		    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Count number stocks", nickname = "count")
	@GetMapping("stocks/count")
	@RolesAllowed({"user", "admin"})
	public ResponseEntity<Long> count() {
		try {
			long result = stockService.count();
			
		    return new ResponseEntity<Long>(result, HttpStatus.OK);
		} catch (Exception e) {
		    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
