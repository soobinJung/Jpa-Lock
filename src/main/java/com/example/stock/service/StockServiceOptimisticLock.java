package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 장점
 *    - 높은 동시성: 낙관적 락킹은 데이터를 락으로 보호하지 않기 때문에 여러 트랜잭션이 동시에 데이터에 접근할 수 있어 시스템의 동시성을 높일 수 있습니다.
 *    - 데드락 없음: 낙관적 락킹은 락을 사용하지 않으므로 데드락이 발생할 가능성이 없습니다.
 *    - 자원의 효율적 사용: 락을 유지하는 데 필요한 자원이 없기 때문에 시스템 자원을 보다 효율적으로 사용할 수 있습니다.
 *    - 성능 향상: 락으로 인한 대기 시간이 없으므로 시스템의 전체적인 성능이 향상될 수 있습니다.
 * 단점
 *    - 충돌 감지와 복구 필요: 낙관적 락킹은 충돌을 예방하는 대신 충돌이 발생했을 때 이를 감지하고 적절하게 처리하는 메커니즘이 필요합니다.
 *    - 충돌 발생 시 재시도 필요: 충돌이 감지되면, 충돌이 발생한 트랜잭션을 롤백하고 재시도해야 할 수 있습니다. 이는 사용자 경험에 부정적인 영향을 줄 수 있습니다.
 *    - 복잡한 로직: 낙관적 락킹을 구현하려면 버전 관리 로직이 필요하고, 충돌 발생 시 이를 처리하는 추가적인 로직이 필요합니다.
 *    - 충돌 해결 전략 필요: 데이터베이스에 따라 충돌이 발생했을 때 이를 어떻게 해결할 것인지에 대한 전략이 필요합니다.
 */
@Service
public class StockServiceOptimisticLock {

    private final StockRepository stockRepository;

    public StockServiceOptimisticLock(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity){
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
