package org.macula.cloud.log.repository;

import java.util.Optional;

import org.macula.cloud.log.domain.ServiceInvokeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInvokeLogRepository extends JpaRepository<ServiceInvokeLog, Long> {

	Optional<ServiceInvokeLog> findByTransactionId(String transactionId);

}
