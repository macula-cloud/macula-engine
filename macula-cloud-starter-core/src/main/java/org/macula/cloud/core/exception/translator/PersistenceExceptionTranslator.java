package org.macula.cloud.core.exception.translator;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

import org.macula.cloud.core.exception.JpaDataAccessException;
import org.macula.cloud.core.exception.MaculaException;
import org.macula.cloud.core.utils.ExceptionUtils;

/**
 * <p>
 * <b>PersistenceExceptionTranslator</b> is JPA的异常翻译
 * </p>
 */
public class PersistenceExceptionTranslator implements MaculaExceptionTranslator {

	@Override
	public int getOrder() {
		return 200;
	}

	@Override
	public MaculaException translateExceptionIfPossible(Throwable ex) {
		if (ExceptionUtils.getRecursionCauseException(ex, TransactionRequiredException.class) != null) {
			return new JpaDataAccessException("javax.persistence.TransactionRequiredException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, RollbackException.class) != null) {
			return new JpaDataAccessException("javax.persistence.RollbackException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, OptimisticLockException.class) != null) {
			return new JpaDataAccessException("javax.persistence.OptimisticLockException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, NoResultException.class) != null) {
			return new JpaDataAccessException("javax.persistence.NoResultException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, NonUniqueResultException.class) != null) {
			return new JpaDataAccessException("javax.persistence.NonUniqueResultException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, EntityNotFoundException.class) != null) {
			return new JpaDataAccessException("javax.persistence.EntityNotFoundException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, EntityExistsException.class) != null) {
			return new JpaDataAccessException("javax.persistence.EntityExistsException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, PersistenceException.class) != null) {
			return new JpaDataAccessException("javax.persistence.PersistenceException", ex);
		}
		return null;
	}

}
