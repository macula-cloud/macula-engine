package org.macula.engine.assistant.protocol;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.http.HttpStatus;

/**
 * <p>Feedback 交互信息 </p>
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class Feedback {

	public static final Feedback OK = new Feedback(20000, "成功", HttpStatus.SC_OK);
	public static final Feedback NO_CONTENT = new Feedback(20400, "无内容", HttpStatus.SC_NO_CONTENT);
	public static final Feedback ERROR = new Feedback(50000, "服务器内部错误，无法完成请求", HttpStatus.SC_INTERNAL_SERVER_ERROR);

	/**
	 * 自定义错误代码
	 */
	private final int code;

	/**
	 * 用户友好的错误信息
	 */
	private final String message;

	/**
	 * 对应 Http 请求状态码
	 */
	private final int status;

	public Feedback(ResultCodes resultCodes, int status) {
		this(resultCodes.getCode(), resultCodes.getMessage(), status);
	}

}
