package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 장점
 *    - 데이터 일관성과 무결성 보장: 비관적 락은 데이터가 트랜잭션에 의해 락이 걸린 동안에는 다른 트랜잭션이 그 데이터를 변경할 수 없도록 함으로써 데이터의 일관성과 무결성을 유지합니다.
 *    - 충돌 회피: 비관적 락은 충돌이 발생할 것을 예상하고 미리 락을 걸어 충돌을 회피합니다. 이는 충돌 발생 후 처리하는 것보다 예방적으로 작동합니다.
 *    - 간단한 로직: 낙관적 락킹에 비해 애플리케이션 로직이 상대적으로 간단합니다. 트랜잭션은 락을 요청하고 획득할 때까지 기다리기만 하면 됩니다.
 *
 * 단점
 *    - 성능 저하: 락으로 인해 대기 시간이 발생하고, 이는 시스템의 성능 저하로 이어질 수 있습니다. 높은 동시성이 요구되는 시스템에서는 이러한 성능 저하가 더욱 심각해질 수 있습니다.
 *    - 데드락 위험: 여러 트랜잭션이 서로의 락을 기다리는 상황이 발생할 수 있으며, 이는 데드락(deadlock)으로 이어질 수 있습니다. 데드락은 시스템이 멈추는 심각한 문제를 야기할 수 있습니다.
 *    - 자원의 낭비: 비관적 락은 다른 트랜잭션이 해당 자원을 사용할 수 없도록 함으로써 자원 활용도가 떨어질 수 있습니다.
 *    - 락 유지 비용: 락을 유지하기 위해 추가적인 데이터베이스 자원이 필요하며, 이는 오버헤드를 증가시킵니다.
 */
@Service
public class StockServicePessimisticLock {

    private final StockRepository stockRepository;

    public StockServicePessimisticLock(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity){
       Stock stock = stockRepository.findByIdWithPessimisticWriteLock(id);
       stock.decrease(quantity);
       stockRepository.save(stock);
    }
}
