package org.macula.engine.assistant.protocol;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

/**
 * Result Error  Information Feedback to  Client
*/
@Getter
@Setter
@ToString
@Schema(title = "响应错误详情", description = "为兼容Validation而增加的Validation错误信息实体")
public class ResultError implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	@Schema(title = "Exception完整信息", type = "string")
	private String detail;

	@Schema(title = "额外的错误信息，目前主要是Validation的Message")
	private String message;

	@Schema(title = "额外的错误代码，目前主要是Validation的Code")
	private String code;

	@Schema(title = "额外的错误字段，目前主要是Validation的Field")
	private String field;

	@Schema(title = "错误堆栈信息")
	private StackTraceElement[] stackTrace;

}