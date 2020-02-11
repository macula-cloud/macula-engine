package org.macula.cloud.sdk.event;

import org.macula.cloud.sdk.domain.GatewayRoute;

public class GatewayRouteChangeEvent extends MaculaEvent<GatewayRoute> {

	private static final long serialVersionUID = 1L;

	public GatewayRouteChangeEvent(GatewayRoute source) {
		super(source);
	}

}
