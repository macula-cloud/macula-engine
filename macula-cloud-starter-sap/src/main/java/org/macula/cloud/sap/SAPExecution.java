package org.macula.cloud.sap;

import org.hibersap.session.Session;
import org.hibersap.session.SessionManager;
import org.hibersap.session.Transaction;
import org.macula.cloud.log.annotation.ServiceInvokeProxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SAPExecution {

	private SessionManager sessionManager;

	@ServiceInvokeProxy(key = "args[0]", source = "args[1].getClass().getSimpleName()", target = "'SAP'", targetMethod = "args[1].getName()", alarm = true)
	public <T extends ExecuteBapi> T execute(Object key, T bapi) {
		return execute(key, bapi, true);
	}

	@ServiceInvokeProxy(key = "args[0]", source = "args[1].getClass().getSimpleName()", target = "'SAP'", targetMethod = "args[1].getName()", alarm = true)
	public <T extends ExecuteBapi> T execute(Object key, T bapi, boolean autoCommit) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionManager.openSession();
			transaction = session.beginTransaction();
			session.execute(bapi);
			if (bapi.hasSuccess()) {
				if (autoCommit) {
					transaction.commit();
				}
			} else {
				transaction.rollback();
			}
			return bapi;
		} catch (RuntimeException ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw ex;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String getReturnMessage(Object value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			return value != null ? value.toString() : "";
		}
	}
}
