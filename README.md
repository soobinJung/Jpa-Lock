# JPA Lock
세 가지 락(Lock) 유형은 데이터베이스 관리에서 동시성 제어를 위해 사용됩니다. 이들은 데이터의 일관성과 무결성을 보장하기 위해 다중 트랜잭션 환경에서 데이터에 접근하는 방법을 제어합니다. MySQL을 비롯한 많은 데이터베이스 시스템에서 이러한 락킹 메커니즘을 지원합니다. 각각에 대해 설명하겠습니다.

## Lock 종류

#### 🔊 Pessimistic Lock (비관적 락)
비관적 락은 이름에서 알 수 있듯이 시스템이 데이터 충돌을 비관적으로 가정하고 접근하는 방식입니다. 이는 데이터가 한 트랜잭션에 의해 사용되고 있을 때, 다른 트랜잭션이 해당 데이터를 변경하거나 읽지 못하도록 강제로 막는 방식입니다. 비관적 락을 사용하면, 락을 획득한 트랜잭션만이 해당 데이터를 읽거나 변경할 수 있으며, 이는 일반적으로 데이터베이스의 행(row) 또는 테이블에 대한 'exclusive lock'을 설정하여 수행됩니다. 비관적 락은 동시성이 낮지만 데이터의 일관성을 매우 높게 유지할 수 있는 장점이 있습니다.

#### 🔊 Optimistic Lock (낙관적 락)
낙관적 락은 시스템이 데이터 충돌을 낙관적으로 가정하여 접근하는 방식입니다. 즉, 충돌이 발생하지 않을 것이라고 가정하고, 데이터를 읽거나 변경할 때 락을 사용하지 않습니다. 대신, 데이터를 업데이트할 때 'version'이라는 속성을 확인하여, 트랜잭션이 시작된 이후 데이터가 변경되었는지를 검사합니다. 만약 데이터가 이미 변경되었다면, 업데이트는 실패하고, 이는 일종의 충돌 감지 메커니즘으로 작용합니다. 낙관적 락은 동시성은 높지만, 데이터 충돌 시 복구 메커니즘이 필요합니다.

#### 🔊 Named Lock (명명된 락)
명명된 락은 MySQL에서 제공하는 특수한 유형의 락입니다. 이는 데이터베이스의 특정 객체나 작업에 대해 유일한 이름을 가진 락을 설정할 수 있게 해주며, 주로 글로벌한 작업이나 배치 처리 등에 사용됩니다. 명명된 락은 특정 이름을 가진 락을 획득하여 해당 락이 해제될 때까지 다른 트랜잭션이나 작업이 해당 자원에 접근하는 것을 방지합니다. 명명된 락은 데이터베이스 내에서 글로벌한 동기화를 필요로 할 때 유용하게 사용될 수 있습니다.
