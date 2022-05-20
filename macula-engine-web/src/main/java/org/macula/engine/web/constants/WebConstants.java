package org.macula.engine.web.constants;

import org.macula.engine.assistant.constants.BaseConstants;

/**
 * <p>Web Package Constants</p>
 */
public interface WebConstants extends BaseConstants {

	String PROPERTY_PREFIX_ENDPOINT = PROPERTY_PREFIX_MACULA + ".endpoint";

	String PROPERTY_REST_TEMPLATE = PROPERTY_PREFIX_REST + ".template";
	String PROPERTY_REST_SCAN = PROPERTY_PREFIX_REST + ".scan";

	/* ----------Macula 详细配置属性路径 ---------- */

	String ITEM_SCAN_ENABLED = PROPERTY_REST_SCAN + PROPERTY_ENABLED;

}
