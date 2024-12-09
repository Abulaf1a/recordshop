package com.northcoders.recordshop.repository;

import com.northcoders.recordshop.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockManagerRepository extends CrudRepository<Stock, Long> {
}
