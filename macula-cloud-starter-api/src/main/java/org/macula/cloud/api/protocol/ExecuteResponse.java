package org.macula.cloud.api.protocol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * <b>ExecuteResponse</b> 单记录数据结构
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class ExecuteResponse<T> extends Response {
	/** 结果信息 */
	private T returnObject;

	public ExecuteResponse(T result) {
		this.returnObject = result;
	}
}
