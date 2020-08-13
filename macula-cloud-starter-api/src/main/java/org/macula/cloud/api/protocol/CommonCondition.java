package org.macula.cloud.api.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * <b>CommonCondition</b> 通用条件
 * </p>
 */
@Getter
@Setter
public class CommonCondition {

	/** 要查询的条件字段(或属性)名称 */
	private String name;

	/** 字段数据类型：Boolean, Integer, Long, Double, String, Timestamp, Date */
	private DataType dataType;

	/** 
	 * 比较符：StartWith, EndWith, Contains, NotContains, Equals, GreaterThan, GreaterOrEqual
	 * LessThan, LessOrEqual, NotEqual, BeforeThan, AfterThan, Between, Is, In
	 **/
	private CriteriaType criteriaType;

	/** 条件值 */
	private Object value;

	/** 另一个条件值 */
	private Object anotherValue;

	/** 是否需要产生SQL片段 */
	private boolean queryPartCollect = true;

}
