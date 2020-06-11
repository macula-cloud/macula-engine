package org.macula.cloud.log.repository;

import org.macula.cloud.log.domain.ServiceInvokeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceInvokeLogRepository extends JpaRepository<ServiceInvokeLog, Long> {

}
