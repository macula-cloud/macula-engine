package org.macula.engine.assistant.protocol;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p> <b>DataType</b> 是数据类型. </p>
 */
public enum DataType {

	Boolean(Boolean.class), Integer(Integer.class), Long(Long.class), Double(Double.class), String(String.class), Timestamp(Timestamp.class),
	Date(Date.class), Word(DataType.class);

	private final Class<?> value;
	private final List<CriteriaType> criteriaTypes;

	private DataType(Class<?> clazz) {
		this.value = clazz;
		this.criteriaTypes = new ArrayList<CriteriaType>();
		for (CriteriaType type : CriteriaType.values()) {
			if (type.support(value)) {
				criteriaTypes.add(type);
			}
		}
	}

	public Class<?> getTypeClass() {
		return this.value;
	}

	public List<CriteriaType> getUsableCriteriaTypes() {
		return criteriaTypes;
	}

	public static DataType forType(Class<?> clz) {
		for (DataType dataType : DataType.values()) {
			if (dataType.getTypeClass().isAssignableFrom(clz)) {
				return dataType;
			}
		}
		return null;
	}
}
