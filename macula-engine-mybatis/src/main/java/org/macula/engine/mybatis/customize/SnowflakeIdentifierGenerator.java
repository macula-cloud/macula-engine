package org.macula.engine.mybatis.customize;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * <p>Customize MyBatis Plus ID Generator use  SnowflakeI </p>
 */
public class SnowflakeIdentifierGenerator implements IdentifierGenerator {

	@Override
	public Number nextId(Object entity) {
		// 采用雪花算法获取id,时间回拨会存在重复,这里用随机数来减少重复的概率
		final Snowflake snowflake = IdUtil.getSnowflake(1, (int) (Math.random() * 20 + 1));
		return snowflake.nextId();
	}
}
