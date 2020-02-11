package org.macula.cloud.sdk.protocol;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldError implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 元素名，与页面元素名一致 */
	private String element;

	/** 错误信息 */
	private String message;

}
