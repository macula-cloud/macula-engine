package org.macula.engine.assistant.protocol;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.macula.engine.assistant.query.ConversionUtils;

/**
 * <p> <b>CommonCondition</b> 是查询条件 </p>
 */
public class SearchCondition {

	/** 要查询的条件字段(或属性)名称 */
	private String name;

	/** 字段数据类型：Boolean, Integer, Long, Double, String, Timestamp, Date */
	private DataType dataType;

	/** 是否需要产生SQL片段 */
	private boolean queryPartCollect = true;
	/**
	 * 比较符：StartWith, EndWith, Contains, NotContains, Equals, GreaterThan, GreaterOrEqual LessThan, LessOrEqual,
	 * NotEqual, BeforeThan, AfterThan, Between, Is, In
	 **/
	private CriteriaType criteriaType;

	/** 条件值 */
	private Object value;

	/** 另一个条件值 */
	private Object anotherValue;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dataType
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the criteriaType
	 */
	public CriteriaType getCriteriaType() {
		return criteriaType;
	}

	/**
	 * @param criteriaType
	 *            the criteriaType to set
	 */
	public void setCriteriaType(CriteriaType criteriaType) {
		this.criteriaType = criteriaType;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the anotherValue
	 */
	public Object getAnotherValue() {
		return anotherValue;
	}

	/**
	 * @param anotherValue
	 *            the anotherValue to set
	 */
	public void setAnotherValue(Object anotherValue) {
		this.anotherValue = anotherValue;
	}

	public void collect(CriteriaVisitor collector) {
		// 对IN要特别处理
		Object targetValue = null;
		Object targetAnotherValue = null;
		if (getValue() instanceof String) {
			if (getCriteriaType() == CriteriaType.In) {
				// 对于输入的字符串，按逗号或tab或换行分割
				String originValue = ConversionUtils.convertQuietly(getValue(), String.class);
				String[] splitValues = originValue.split("\t|\r|\n|,");
				if (splitValues != null && splitValues.length > 0) {
					List<Object> inValues = new ArrayList<Object>();
					for (String in : splitValues) {
						if (!StringUtils.isBlank(in)) {
							inValues.add(ConversionUtils.convertQuietly(in, getDataType().getTypeClass()));
						}
					}
					if (!inValues.isEmpty()) {
						targetValue = inValues;
					}
				}
			} else {
				targetValue = ConversionUtils.convertQuietly(getValue(), getDataType().getTypeClass());
				targetAnotherValue = ConversionUtils.convertQuietly(getAnotherValue(), getDataType().getTypeClass());
			}
		} else {
			targetValue = getValue();
		}
		targetValue = changeTargetValue(targetValue);
		if (targetValue instanceof String) {
			if (StringUtils.isBlank((String) targetValue)) {
				targetValue = null;
			}
		}

		if (validate(targetValue)) {
			if (isQueryPartCollect()) {
				getCriteriaType().collect(collector, getName(), targetValue, targetAnotherValue);
			} else {
				collector.addQueryParam(getName(), targetValue);
			}
		}
	}

	protected boolean validate(Object targetValue) {
		if (!getCriteriaType().support(getDataType().getTypeClass())) {
			return false;
		}
		if (targetValue == null && !getCriteriaType().allowNull()) {
			return false;
		}
		return true;
	}

	protected Object changeTargetValue(Object targetValue) {
		return targetValue;
	}

	/**
	 * @return the queryPartCollect
	 */
	public boolean isQueryPartCollect() {
		return queryPartCollect;
	}

	/**
	 * @param queryPartCollect
	 *            the queryPartCollect to set
	 */
	public void setQueryPartCollect(boolean queryPartCollect) {
		this.queryPartCollect = queryPartCollect;
	}

}
