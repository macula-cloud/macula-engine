package org.macula.engine.commons.id;

import java.io.Serializable;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * Snow flake Id generator
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {

	private static final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Serializable id = session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
		return id != null ? id : snowflake.nextId();
	}

}
