package org.macula.cloud.core.protocol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExecuteResponse<T> extends Response {

	private static final long serialVersionUID = 1L;

	/** 结果信息 */
	private T returnObject;

	public ExecuteResponse(T result) {
		this.returnObject = result;
	}

}
