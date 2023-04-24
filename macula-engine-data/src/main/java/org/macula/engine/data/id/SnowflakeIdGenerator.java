package org.macula.engine.data.id;

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
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		Object id = session.getEntityPersister(null, object).getIdentifier(object, session);
		return id != null ? id : snowflake.nextId();
	}

}
