package org.macula.cloud.core.exception.translator;

import org.macula.cloud.core.exception.MaculaException;
import org.springframework.core.Ordered;

/**
 * <p>
 * <b>CoreExceptionTranslator</b> 是异常转化类.
 * </p>
 * 
 */
public interface MaculaExceptionTranslator extends Ordered {

	MaculaException translateExceptionIfPossible(Throwable ex);
}
