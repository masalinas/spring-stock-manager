package io.oferto.application.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import io.oferto.application.backend.model.Stock;
import io.oferto.application.backend.repository.StockRepository;

@Service
public class StockService {
	private static final Logger LOGGER = Logger.getLogger(StockService.class.getName());
	private StockRepository stockRepository;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	
	public List<Stock> findAll() {
        return stockRepository.findAll();
    }

	public Optional<List<Stock>> findAllByWarehouseId(Long warehouseId) {
        return stockRepository.findAllByWarehouseId(warehouseId);
    }
	
	public Optional<Stock> findById(long id) {
        return stockRepository.findById(id);
    }
	
    public long count() {
        return stockRepository.count();
    }

    public void delete(Stock stock) {
    	stockRepository.delete(stock);
    }
    
    public void deleteById(Long id) {
    	stockRepository.deleteById(id);
    }
    
    public Stock save(Stock stock) {
        if (stock == null) {
            LOGGER.log(Level.SEVERE,
                "Stock is null. Are you sure you have connected your form to the application?");
            return null;
        }
        
        return stockRepository.save(stock);
    } 
}
