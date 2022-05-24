package org.macula.engine.web.provider;

import java.util.Collections;
import java.util.List;

import io.swagger.v3.oas.models.servers.Server;
import org.macula.engine.web.context.WebApplicationContext;

/**
 * <p>Default OpenApi Servers Resolver Implement </p>
 */
public class DefaultOpenApiServersResolver implements OpenApiServersResolver {

	private final WebApplicationContext webApplicationContext;

	public DefaultOpenApiServersResolver(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	@Override
	public List<Server> getServers() {
		Server server = new Server();
		server.setUrl(webApplicationContext.getServiceContext().getAddress());
		return Collections.singletonList(server);
	}
}
