package org.macula.engine.web.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.macula.engine.commons.utils.XssUtils;

/**
 * <p> Xss Json 处理 </p>
 */
public class XssStringJsonDeserializer extends JsonDeserializer<String> {

	@Override
	public Class<String> handledType() {
		return String.class;
	}

	@Override
	public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String value = jsonParser.getValueAsString();
		if (StringUtils.isNotBlank(value)) {
			return XssUtils.cleaning(value);
		}

		return value;
	}
}
