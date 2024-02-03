package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import com.example.stock.service.StockServiceNamedLock;
import org.springframework.stereotype.Component;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockServiceNamedLock stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockServiceNamedLock stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true){
            try {
                lockRepository.getLock("stock_" + id);
                stockService.decrease(id, quantity);

                break;
            }catch (Exception e){
                Thread.sleep(50);
            } finally {
                lockRepository.releaseLock("stock_" + id);
            }
        }
    }
}
