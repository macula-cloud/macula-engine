package org.macula.cloud.log.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LOG_SERVICE_INVOKE")
@Setter
@Getter
public class ServiceInvokeLog extends org.macula.cloud.core.domain.ServiceInvokeLog {

	private static final long serialVersionUID = 1L;

}
