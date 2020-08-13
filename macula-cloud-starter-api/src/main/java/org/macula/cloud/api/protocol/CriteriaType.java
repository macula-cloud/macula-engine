package org.macula.cloud.api.protocol;

/**
 * <p>
 * <b>CriteriaType</b> 是 比较符
 * </p>
 */
public enum CriteriaType {
	// like '%x'
	StartWith,
	// like 'x%'
	EndWith,
	// like '%x%'
	Contains,
	// not like '%x%'
	NotContains,
	// = x
	Equals,
	// > x
	GreaterThan,
	// >= x
	GreaterOrEqual,
	// < x
	LessThan,
	// <= x
	LessOrEqual,
	// <> x
	NotEqual,
	// < x 早于
	BeforeThan,
	// > x 晚于
	AfterThan,
	// >= x1 and < x2
	Between, Is,
	// in ( x1, x2 )
	In;
}
