package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockServiceSynchronized {

    private final StockRepository stockRepository;

    public StockServiceSynchronized(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /**
     * JAVA synchronized 를 이용하여 동시성 문제 해결
     *
     * 문제점
     * - 하나의 프로세스에서만 보장이 된다.
     * - 여러 서버에서는 보장이 되지 않는다.
     */
    public synchronized void decrease(Long id, Long quantity){
       Stock stock = stockRepository.findById(id).orElseThrow();
       stock.decrease(quantity);
       stockRepository.save(stock);
    }
}
