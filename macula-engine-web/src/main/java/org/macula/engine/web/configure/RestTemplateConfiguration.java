package org.macula.engine.web.configure;

import java.io.IOException;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.macula.engine.web.properties.RestTemplateProperties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * <p> Rest Template Configuration </p>
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateConfiguration {

	@PostConstruct
	public void postConstruct() {
		log.debug("[Macula]  |- SDK [Engine Web Rest Template] Auto Configure.");
	}

	/**
	 * '@LoadBalanced'注解表示使用Ribbon实现客户端负载均衡
	 *
	 * @return RestTemplate
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {

		final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

		/**
		 * 默认的 RestTemplate 有个机制是请求状态码非200 就抛出异常，会中断接下来的操作。
		 * 如果不想中断对结果数据得解析，可以通过覆盖默认的 ResponseErrorHandler ，
		 * 对hasError修改下，让他一直返回true，即是不检查状态码及抛异常了
		 */
		ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return true;
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {

			}
		};

		restTemplate.setErrorHandler(responseErrorHandler);

		restTemplate.getMessageConverters().clear();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		log.trace("[Macula]  |- Bean [Rest Template] Auto Configure.");
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory(RestTemplateProperties restTemplateProperties) {
		OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
		//读取超时5秒,默认无限限制,单位：毫秒
		factory.setReadTimeout(restTemplateProperties.getReadTimeout());
		//连接超时10秒，默认无限制，单位：毫秒
		factory.setConnectTimeout(restTemplateProperties.getConnectTimeout());
		log.trace("[Macula]  |- Bean [Client Http Request Factory] Auto Configure.");
		return factory;
	}
}
