package org.macula.cloud.core.exception.translator;

import java.sql.BatchUpdateException;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.PessimisticLockException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.DataException;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.exception.SQLGrammarException;
import org.macula.cloud.core.exception.HibernateDataAccessException;
import org.macula.cloud.core.exception.MaculaException;
import org.macula.cloud.core.utils.ExceptionUtils;

/**
 * <p>
 * <b>HibernateExceptionTranslator</b> 是Hibernate部分异常处理
 * </p>
 * 
 */
public class HibernateExceptionTranslator implements MaculaExceptionTranslator {

	@Override
	public int getOrder() {
		return 100;
	}

	@Override
	public MaculaException translateExceptionIfPossible(Throwable ex) {
		if (ExceptionUtils.getRecursionCauseException(ex, BatchUpdateException.class) != null) {
			return new HibernateDataAccessException("java.sql.BatchUpdateException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, JDBCConnectionException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.JDBCConnectionException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, SQLGrammarException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.SQLGrammarException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, ConstraintViolationException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.ConstraintViolationException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, LockAcquisitionException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.LockAcquisitionException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, PessimisticLockException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.PessimisticLockException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, QueryTimeoutException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.QueryTimeoutException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, GenericJDBCException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.GenericJDBCException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, DataException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.DataException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, HibernateException.class) != null) {
			return new HibernateDataAccessException("org.hibernate.exception.HibernateException", ex);
		}
		return null;
	}
}
