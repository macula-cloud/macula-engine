package org.macula.engine.web.provider;

import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.models.servers.Server;
import org.macula.engine.web.context.MaculaApplicationContext;

/**
 * <p>Default OpenApi Servers Resolver Implement </p>
 */
public class DefaultOpenApiServersResolver implements OpenApiServersResolver {

	private final MaculaApplicationContext maculaApplicationContext;

	public DefaultOpenApiServersResolver(MaculaApplicationContext maculaApplicationContext) {
		this.maculaApplicationContext = maculaApplicationContext;
	}

	@Override
	public List<Server> getServers() {
		Server server = new Server();
		server.setUrl(maculaApplicationContext.getServiceContext().getAddress());
		return Collections.singletonList(server);
	}
}
