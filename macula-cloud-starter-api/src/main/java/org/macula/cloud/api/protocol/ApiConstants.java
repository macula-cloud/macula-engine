package org.macula.cloud.api.protocol;

/**
 * <p>
 * <b>CloudConstants</b> is define constants.
 * </p>
 */
public class ApiConstants {

	/** 数据绑定错误码 */
	public final static String MACULA_CORE_VALID_CODE = "bind";

	/** 数据转换异常 */
	public final static String MACULA_CORE_CONVERT_CODE = "convert";

	/** 参数不合法错误码 */
	public final static String MACULA_CORE_ARGS_CODE = "argument";

	/** 服务层默认异常码 */
	public final static String MACULA_CORE_SERVICE_CODE = "service";

	/** HIBERNATE DATA ACCESS ERROR CODE */
	public final static String MACULA_DATA_HIBERNATE_ERROR_CODE = "macula.data.hibernate";

	/** JPA DATA ACCESS ERROR CODE */
	public final static String MACULA_DATA_JPA_ERROR_CODE = "macula.data.jpa";

	/** UserSession 在SerlvetRequest中的属性名 */
	public final static String USER_SESSION_IN_REQUEST = "__USER_SESSION_IN_REQUEST__";

	/** 应用版本 */
	public static final String APPLICATION_VERSION = "2.0.0";
}
