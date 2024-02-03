package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 장점:
 *    - 전역 범위: 명명된 락은 서버 전체에 걸쳐 영향을 미치므로, 다른 세션과 심지어 다른 트랜잭션 간에도 작업을 동기화하는 데 사용할 수 있습니다. 이는 전역적으로 직렬화해야 하는 작업에 특히 유용합니다.
 *    - 유연성: 배치 작업이 독점적으로 실행되도록 하거나 특정 중요 작업이 다른 작업과 겹치지 않도록 하는 등 다양한 목적으로 사용할 수 있습니다.
 *    - 수동 제어: 명명된 락은 언제 락을 획득하고 해제할지 애플리케이션 로직이 결정하기 때문에 높은 수준의 제어를 제공합니다.
 *    - 구현 용이성: 다른 분산 락 시스템에 비해 설정이 덜 복잡하면서 명명된 락을 사용하는 것이 종종 더 쉽습니다.
 *    - 낮은 오버헤드: 데이터베이스 수준에서 관리되기 때문에 애플리케이션 수준의 락보다 오버헤드가 낮을 수 있습니다.
 *
 *  단점:
 *    -수동 관리: 올바르게 락을 획득하고 해제하는 책임은 애플리케이션 로직에 있으며, 제대로 관리되지 않으면 데드락이나 락 해제를 잊는 등의 오류를 일으킬 수 있습니다.
 *    -데드락의 가능성: 명명된 락의 부적절한 사용은 데드락으로 이어질 수 있습니다. 이는 두 개 이상의 작업이 서로의 락을 무한히 기다리는 상황을 말합니다.
 *    -확장성 문제: 명명된 락은 적당한 동시성을 가진 애플리케이션에서 잘 작동할 수 있지만, 그 전역적인 특성 때문에 고부하 시스템에서는 잘 확장되지 않을 수 있습니다.
 *    -블로킹: 명명된 락은 블로킹 연산입니다. 한 세션이 이미 다른 세션에 의해 보유된 락을 획득하려고 시도하면, 락이 해제될 때까지 기다려야 하므로 지연이 발생할 수 있습니다.
 *    -데드락 탐지 부재: 내부 데이터베이스 락킹 메커니즘과 달리 명명된 락은 대개 데드락 탐지 기능이 없어, 락이 결코 해제되지 않는 상황이 발생할
 */
@Service
public class StockServiceNamedLock {

    private final StockRepository stockRepository;

    public StockServiceNamedLock(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity){
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
