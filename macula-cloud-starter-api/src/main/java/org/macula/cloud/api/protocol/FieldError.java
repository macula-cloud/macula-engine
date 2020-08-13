package org.macula.cloud.api.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * <b>FieldError</b> 字段错误数据结构
 * </p>
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldError {
	/** 元素名，与页面元素名一致 */
	private String element;

	/** 错误信息  */
	private String message;

}
