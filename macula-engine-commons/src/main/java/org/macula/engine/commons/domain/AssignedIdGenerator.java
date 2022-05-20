package org.macula.engine.commons.domain;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

public class AssignedIdGenerator extends IdentityGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor s, Object obj) throws HibernateException {
		Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
		return id != null ? id : super.generate(s, obj);
	}
}
