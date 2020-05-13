package org.macula.cloud.core.event;

import org.macula.cloud.core.domain.GatewayRoute;

public class GatewayRouteChangeEvent extends MaculaEvent<GatewayRoute> {

	private static final long serialVersionUID = 1L;

	public GatewayRouteChangeEvent(GatewayRoute source) {
		super(source);
	}

}
