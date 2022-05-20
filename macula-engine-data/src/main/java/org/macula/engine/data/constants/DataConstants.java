package org.macula.engine.data.constants;

import org.macula.engine.assistant.constants.BaseConstants;

/**
 * <p>Description: 数据常量 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/1/19 18:10
 */
public interface DataConstants extends BaseConstants {

	String ITEM_SPRING_SQL_INIT_PLATFORM = "spring.sql.init.platform";

	String ANNOTATION_SQL_INIT_PLATFORM = ANNOTATION_PREFIX + ITEM_SPRING_SQL_INIT_PLATFORM + ANNOTATION_SUFFIX;
}
