package org.macula.engine.web.provider;

import java.util.List;

import io.swagger.v3.oas.models.servers.Server;

/**
 * <p> OpenApi Server Resover </p>
 * <p>
 * 单体和分布式架构，提供给OpenAPI展示的地址不同。
 */
public interface OpenApiServersResolver {
	/**
	 * 获取 Open Api 所需的 Server 地址。
	 *
	 * @return Open Api Servers 值
	 */
	List<Server> getServers();
}
