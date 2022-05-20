package org.macula.engine.web.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.macula.engine.web.jackson.XssStringJsonDeserializer;

@Slf4j
@AllArgsConstructor
public class JacksonUtils {
	private static ObjectMapper OBJECT_MAPPER;

	private ObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		if (ObjectUtils.isNotEmpty(this.objectMapper)) {
			OBJECT_MAPPER = this.objectMapper;
		} else {
			OBJECT_MAPPER = new ObjectMapper();
		}
		settings(OBJECT_MAPPER);
	}

	private void settings(ObjectMapper objectMapper) {
		// 设置为中国上海时区
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		// 空值不序列化
		//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 序列化时，日期的统一格式
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 排序key
		objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		// 忽略空bean转json错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 忽略在json字符串中存在，在java类中不存在字段，防止错误。
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 单引号处理
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		/**
		 * 序列换成json时,将所有的long变成string
		 * js中long过长精度丢失
		 */
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		simpleModule.addDeserializer(String.class, new XssStringJsonDeserializer());

		objectMapper.registerModule(simpleModule);
	}

	public static ObjectMapper getObjectMapper() {
		return OBJECT_MAPPER;
	}

	public static ObjectMapper registerModule(Module module) {
		return getObjectMapper().registerModules(module);
	}

	public static <T> String toJson(T domain) {
		try {
			return getObjectMapper().writeValueAsString(domain);
		} catch (JsonProcessingException e) {
			log.error("[Macula] |- JacksonUtils json processing error, when to json! {}", e.getMessage());
			return null;
		}
	}

	public static <T> T toObject(String content, Class<T> valueType) {
		try {
			return getObjectMapper().readValue(content, valueType);
		} catch (JsonProcessingException e) {
			log.error("[Macula] |- JacksonUtils json processing error, when to object with value type! {}", e.getMessage());
			return null;
		}
	}

	public static <T> T toObject(String content, TypeReference<T> typeReference) {
		try {
			return getObjectMapper().readValue(content, typeReference);
		} catch (JsonProcessingException e) {
			log.error("[Macula] |- JacksonUtils json processing error, when to object with type reference! {}", e.getMessage());
			return null;
		}
	}

	public static <T> T toObject(String content, JavaType javaType) {
		try {
			return getObjectMapper().readValue(content, javaType);
		} catch (JsonProcessingException e) {
			log.error("[Macula] |- JacksonUtils json processing error, when to object with java type! {}", e.getMessage());
			return null;
		}
	}

	public static <T> List<T> toList(String content, Class<T> clazz) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
		return toObject(content, javaType);
	}

	public static <T> List<T> toList(String content) {
		return toObject(content, new TypeReference<List<T>>() {
		});
	}

	public static <K, V> Map<K, V> toMap(String content, Class<K> keyClass, Class<V> valueClass) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
		return toObject(content, javaType);
	}

	public static <K, V> Map<K, V> toMap(String content) {
		return toObject(content, new TypeReference<Map<K, V>>() {
		});
	}

	public static <T> Set<T> toSet(String content, Class<T> clazz) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructCollectionLikeType(Set.class, clazz);
		return toObject(content, javaType);
	}

	public static <T> Set<T> toSet(String content) {
		return toObject(content, new TypeReference<Set<T>>() {
		});
	}

	public static <T> T[] toArray(String content, Class<T> clazz) {
		JavaType javaType = getObjectMapper().getTypeFactory().constructArrayType(clazz);
		return toObject(content, javaType);
	}

	public static <T> T[] toArray(String content) {
		return toObject(content, new TypeReference<T[]>() {
		});
	}

	public static JsonNode toNode(String content) {
		try {
			return getObjectMapper().readTree(content);
		} catch (JsonProcessingException e) {
			log.error("[Macula] |- JacksonUtils json processing error, when to node with string! {}", e.getMessage());
			return null;
		}
	}

	public static JsonNode toNode(JsonParser jsonParser) {
		try {
			return getObjectMapper().readTree(jsonParser);
		} catch (IOException e) {
			log.error("[Macula] |- JacksonUtils io error, when to node with json parser! {}", e.getMessage());
			return null;
		}
	}

	public static JsonParser createParser(String content) {
		try {
			return getObjectMapper().createParser(content);
		} catch (IOException e) {
			log.error("[Macula] |- JacksonUtils io error, when create parser! {}", e.getMessage());
			return null;
		}
	}

	public static <R> R loop(JsonNode jsonNode, Function<JsonNode, R> function) {
		if (jsonNode.isObject()) {
			Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();
			while (it.hasNext()) {
				Map.Entry<String, JsonNode> entry = it.next();
				loop(entry.getValue(), function);
			}
		}

		if (jsonNode.isArray()) {
			for (JsonNode node : jsonNode) {
				loop(node, function);
			}
		}

		if (jsonNode.isValueNode()) {
			return function.apply(jsonNode);
		} else {
			return null;
		}
	}
}
