package org.macula.engine.assistant.provider;

import java.security.Principal;

/**
 * Get Current Session Username 
 */
public interface PrincipalAssistantProvider {

	Principal getPrincipal();
}
