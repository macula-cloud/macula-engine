package org.macula.cloud.log.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "MC_SERVICE_INVOKE_LOG")
public class ServiceInvokeLog extends org.macula.cloud.core.domain.ServiceInvokeLog {

	private static final long serialVersionUID = 1L;

}
