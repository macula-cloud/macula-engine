package org.macula.engine.assistant.protocol;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpStatus;
import org.macula.engine.assistant.utils.StringUtils;

/**
 * <p>
 * <b>Response</b> 返回数据结构的基类
 * </p>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(title = "统一响应返回实体", description = "所有接口统一返回的实体定义", example = "Result.success(\"XXX\")")
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(title = "自定义响应编码")
	private int code = 0;

	@Schema(title = "响应返回信息")
	private String message;

	@Schema(title = "请求路径")
	private String path;

	@Schema(title = "响应返回数据")
	private T data;

	@Schema(title = "http状态码")
	private int status;

	@Schema(title = "响应时间戳", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp = new Date();

	@Schema(title = "执行错误信息")
	private ResultError error = new ResultError();

	public Result<T> code(int code) {
		this.code = code;
		return this;
	}

	public Result<T> message(String message) {
		this.message = message;
		return this;
	}

	public Result<T> data(T data) {
		this.data = data;
		return this;
	}

	public Result<T> path(String path) {
		this.path = path;
		return this;
	}

	public Result<T> type(ResultCodes resultCodes) {
		this.code = resultCodes.getCode();
		this.message = resultCodes.getMessage();
		return this;
	}

	public Result<T> status(int httpStatus) {
		this.status = httpStatus;
		return this;
	}

	public Result<T> stackTrace(StackTraceElement[] stackTrace) {
		this.error.setStackTrace(stackTrace);
		return this;
	}

	public Result<T> detail(String detail) {
		this.error.setDetail(detail);
		return this;
	}

	public Result<T> validation(String message, String code, String field) {
		this.error.setMessage(message);
		this.error.setCode(code);
		this.error.setField(field);
		return this;
	}

	private static <T> Result<T> create(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
		Result<T> result = new Result<>();
		if (StringUtils.isNotBlank(message)) {
			result.message(message);
		}

		if (StringUtils.isNotBlank(detail)) {
			result.detail(detail);
		}

		result.code(code);
		result.status(status);

		if (ObjectUtils.isNotEmpty(data)) {
			result.data(data);
		}

		if (ArrayUtils.isNotEmpty(stackTrace)) {
			result.stackTrace(stackTrace);
		}

		return result;
	}

	public static <T> Result<T> success(String message, int code, int status, T data) {
		return create(message, null, code, status, data, null);
	}

	public static <T> Result<T> success(String message, int code, T data) {
		return success(message, code, HttpStatus.SC_OK, data);
	}

	public static <T> Result<T> success(ResultCodes resultErrorCodes, T data) {
		return success(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
	}

	public static <T> Result<T> success(Feedback feedback, T data) {
		return success(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
	}

	public static <T> Result<T> success(String message, T data) {
		return success(message, Feedback.OK.getCode(), data);
	}

	public static <T> Result<T> success(String message) {
		return success(message, null);
	}

	public static <T> Result<T> success() {
		return success("操作成功！");
	}

	public static <T> Result<T> content(T data) {
		return success("操作成功！", data);
	}

	public static <T> Result<T> failure(String message, String detail, int code, int status, T data, StackTraceElement[] stackTrace) {
		return create(message, detail, code, status, data, stackTrace);
	}

	public static <T> Result<T> failure(String message, String detail, int code, int status, T data) {
		return failure(message, detail, code, status, data, null);
	}

	public static <T> Result<T> failure(String message, int code, int status, T data) {
		return failure(message, message, code, status, data);
	}

	public static <T> Result<T> failure(String message, String detail, int code, T data) {
		return failure(message, detail, code, HttpStatus.SC_INTERNAL_SERVER_ERROR, data);
	}

	public static <T> Result<T> failure(String message, int code, T data) {
		return failure(message, message, code, data);
	}

	public static <T> Result<T> failure(ResultCodes resultErrorCodes, T data) {
		return failure(resultErrorCodes.getMessage(), resultErrorCodes.getCode(), data);
	}

	public static <T> Result<T> failure(Feedback feedback, T data) {
		return failure(feedback.getMessage(), feedback.getCode(), feedback.getStatus(), data);
	}

	public static <T> Result<T> failure(String message, T data) {
		return failure(message, Feedback.ERROR.getCode(), data);
	}

	public static <T> Result<T> failure(String message) {
		return failure(message, null);
	}

	public static <T> Result<T> failure() {
		return failure("操作失败！");
	}

	public static <T> Result<T> empty(String message, int code, int status) {
		return create(message, null, code, status, null, null);
	}

	public static <T> Result<T> empty(String message, int code) {
		return empty(message, code, Feedback.NO_CONTENT.getStatus());
	}

	public static <T> Result<T> empty(Feedback feedback) {
		return empty(feedback.getMessage(), feedback.getCode(), feedback.getStatus());
	}

	public static <T> Result<T> empty(ResultCodes resultErrorCodes) {
		return empty(resultErrorCodes.getMessage(), resultErrorCodes.getCode());
	}

	public static <T> Result<T> empty(String message) {
		return empty(message, Feedback.NO_CONTENT.getCode());
	}

	public static <T> Result<T> empty() {
		return empty("未查询到相关内容！");
	}

	public Map<String, Object> toModel() {
		Map<String, Object> result = new HashMap<>(8);
		result.put("code", code);
		result.put("message", message);
		result.put("path", path);
		result.put("data", data);
		result.put("status", status);
		result.put("timestamp", timestamp);
		result.put("error", error);
		return result;
	}
}
