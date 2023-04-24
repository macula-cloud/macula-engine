package org.macula.cloud.core.exception.translator;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TransactionRequiredException;

import org.macula.cloud.api.exception.MaculaException;
import org.macula.cloud.core.exception.JpaDataAccessException;
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
			return new JpaDataAccessException("jakarta.persistence.TransactionRequiredException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, RollbackException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.RollbackException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, OptimisticLockException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.OptimisticLockException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, NoResultException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.NoResultException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, NonUniqueResultException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.NonUniqueResultException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, EntityNotFoundException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.EntityNotFoundException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, EntityExistsException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.EntityExistsException", ex);
		}
		if (ExceptionUtils.getRecursionCauseException(ex, PersistenceException.class) != null) {
			return new JpaDataAccessException("jakarta.persistence.PersistenceException", ex);
		}
		return null;
	}

}
