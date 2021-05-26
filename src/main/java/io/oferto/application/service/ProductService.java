package io.oferto.application.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import io.oferto.application.backend.model.Product;
import io.oferto.application.backend.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> findAll() {
        return productRepository.findAll();
    }

	public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }
	
    public long count() {
        return productRepository.count();
    }

    public void delete(Product product) {
    	productRepository.delete(product);
    }

    public void deleteById(Long id) {
    	productRepository.deleteById(id);
    }
    
    public Product save(Product product) {
        if (product == null) {
        	LOGGER.log(Level.SEVERE,
                "product is null. Are you sure you have connected your form to the application?");
            return null;
        }
        
        return productRepository.save(product);
    }   
}
