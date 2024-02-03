package com.example.stock.facade;

import com.example.stock.service.StockServiceOptimisticLock;
import org.springframework.stereotype.Component;

@Component
public class StockOptimisticLockFacade {

    private final StockServiceOptimisticLock stockServiceOptimisticLock;

    public StockOptimisticLockFacade(StockServiceOptimisticLock stockServiceOptimisticLock) {
        this.stockServiceOptimisticLock = stockServiceOptimisticLock;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true){
            try {
                stockServiceOptimisticLock.decrease(id, quantity);
                break;
            }catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
