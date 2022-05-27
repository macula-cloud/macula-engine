package org.macula.engine.web.configure;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.macula.engine.web.utils.JacksonUtils;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p>JacksonUtils Configuration </p>
 */
@Slf4j
@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class JacksonUtilsConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula] |- SDK [Engine Web Rest JacksonUtils] Auto Configure.");
	}

	@Bean
	public JacksonUtils jacksonUtils(ObjectProvider<ObjectMapper> objectMapper) {
		log.debug("[Macula] |- Bean [JacksonUtils] Auto Configure.");
		return new JacksonUtils(objectMapper.getIfAvailable());
	}

	@Primary
	@Bean
	public ObjectMapper jacksonObjectMapper(JacksonUtils jacksonUtils) {
		log.debug("[Macula] |- Bean [JacksonUtils Object Mapper] Auto Configure.");
		return JacksonUtils.getObjectMapper();
	}

	/**
	 * 转换器全局配置
	 *
	 * @return MappingJackson2HttpMessageConverter
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
		log.debug("[Macula] |- Bean [JacksonUtils Http Message Converter] Auto Configure.");
		return new MappingJackson2HttpMessageConverter(objectMapper);
	}

}
