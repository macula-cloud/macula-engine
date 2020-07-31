package org.macula.cloud.cainiao;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;

import org.macula.cloud.cainiao.configure.CainiaoConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CainiaoLinkService {

	private CainiaoConfig config;
	private RestTemplate restTemplate;

	public CainiaoLinkService(CainiaoConfig config) {
		this.config = config;
	}

	@PostConstruct
	public void initialRestTemplate() {
		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
		httpMessageConverters.stream().forEach(httpMessageConverter -> {
			if (httpMessageConverter instanceof StringHttpMessageConverter) {
				StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) httpMessageConverter;
				messageConverter.setDefaultCharset(Charset.forName("UTF-8"));
			}
		});
	}

	/**
	 * 查询四级地址
	 */
	public DivisionResponse getChinaDivision(DivisionsRequest request) {
		String messageType = "CNDZK_CHINA_DIVISION";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, DivisionResponse.class);
	}

	/**
	 * 四级地址新版本查询
	 */
	public DivisionVersionListResponse getDivisionVersionList(DivisionVersionListRequest request) {
		String messageType = "CNDZK_DIVISION_VERSION_LIST";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, DivisionVersionListResponse.class);
	}

	/**
	 * 地址质量分类
	 */
	public AddressClassifyResponse getAddressClassify(AddressClassifyRequest request) {
		String messageType = "CNDZK_ADDRESS_CLASSIFY";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, AddressClassifyResponse.class);
	}

	/**
	 * 四级地址升级履历查询
	 */
	public VersionChangeListResponse getVersionChangeList(VersionChangeListRequest request) {
		String messageType = "CNDZK_VERSION_CHANGE_LIST";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, VersionChangeListResponse.class);
	}

	/**
	 * 查询下级四级地址
	 */
	public SubDivisionsResponse getChinaSubDivisions(SubDivisionsRequest request) {
		String messageType = "CNDZK_CHINA_SUB_DIVISIONS";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, SubDivisionsResponse.class);
	}

	/**
	 * 四级地址纠正
	 */
	public DivisionParseResponse getDivisionParse(DivisionParseRequest request) {
		String messageType = "CNDZK_DIVISION_PARSE";
		String logisticsInterface = JSONObject.toJSONString(request);
		String response = queryLinkApi(messageType, logisticsInterface);
		return JSONObject.parseObject(response, DivisionParseResponse.class);
	}

	public String queryLinkApi(String messageType, String logisticsInterface) {
		if (log.isInfoEnabled()) {
			log.info("Request -> {} , {}", messageType, logisticsInterface);
		}
		ResponseEntity<String> response = restTemplate.exchange(config.createRequestEntity(messageType, logisticsInterface), String.class);
		HttpStatus status = response.getStatusCode();
		if (status == HttpStatus.OK || status == HttpStatus.CREATED || status == HttpStatus.ACCEPTED || status == HttpStatus.INTERNAL_SERVER_ERROR) {
			String body = response.getBody();
			if (log.isInfoEnabled()) {
				log.info("Response -> {}", response);
			}
			return body;
		} else {
			throw new RuntimeException(String.format("error.openapi.response.status: %s", status));
		}
	}

}
