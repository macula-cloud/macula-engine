
package org.macula.cloud.sdk.principal;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import org.macula.cloud.sdk.utils.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class SubjectPrincipal implements Principal, Serializable {

	private static final long serialVersionUID = 1L;

	private final String account;

	private final String source;

	private final SubjectType type;
	
	private String guid;

	private String nickname;

	private String mobile;

	private String email;

	private String avatar;

	private Locale locale;

	private TimeZone timeZone;

	private long createdTime;

	private long lastUpdatedTime;

	private Set<String> tenantIds;

	private SubjectCredential credential;

	private Set<String> authorities = new HashSet<String>();

	private final Map<String, Serializable> properties = new HashMap<String, Serializable>();

	public SubjectPrincipal(String account) {
		this(account, SubjectType.USER);
	}

	public SubjectPrincipal(String account, SubjectType type) {
		this(account, "LOCAL", type);
	}

	public SubjectPrincipal(String account, String source) {
		this(account, source, SubjectType.USER);
	}

	public SubjectPrincipal(String account, String source, SubjectType type) {
		this.account = account;
		this.source = source;
		this.type = type;
		this.setLocale(Locale.getDefault());
		this.setTimeZone(TimeZone.getDefault());
		this.authorities = new HashSet<String>();
		this.createdTime = System.currentTimeMillis();
		this.lastUpdatedTime = System.currentTimeMillis();
	}

	@SuppressWarnings("unchecked")
	public SubjectPrincipal(Map<String, Object> map) {
		this.account = (String) map.get("username");
		this.source = (String) map.get("source");
		this.type = (SubjectType) map.get("type");
		this.locale = Locale.forLanguageTag((String) map.get("locale"));
		this.setTimeZone(TimeZone.getTimeZone((String) map.get("timeZone")));
		this.authorities.addAll((Set<String>) map.get("authorities"));
		this.createdTime = (long) map.get("createdTime");
		this.lastUpdatedTime = (long) map.get("lastUpdatedTime");

		this.nickname = (String) map.get("nickname");
		this.mobile = (String) map.get("mobile");
		this.email = (String) map.get("email");
		this.avatar = (String) map.get("avatar");
		this.credential = (SubjectCredential) map.get("credential");
		this.properties.putAll((Map<String, Serializable>) map.get("properties"));
		this.tenantIds = (Set<String>) map.get("tenantIds");
	}

	public Map<String, Object> asMap() {
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("guid", getGuid());
		map.put("username", getAccount());
		map.put("source", getSource());
		map.put("type", getType());
		map.put("locale", getLocale().toLanguageTag());
		map.put("timeZone", getTimeZone().getID());
		map.put("authorities", getAuthorities());
		map.put("createdTime", getCreatedTime());
		map.put("lastUpdatedTime", getLastUpdatedTime());

		map.put("nickname", getNickname());
		map.put("mobile", getMobile());
		map.put("email", getEmail());
		map.put("avatar", getAvatar());
		map.put("credential", getCredential());
		map.put("properties", getProperties());
		map.put("tenantIds", getTenantIds());
		return map;
	}

	@Override
	public String getName() {
		return getAccount();
	}

	/**
	 * 获取用户名
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * 获取显示用户名
	 */
	public String getNickname() {
		return this.nickname;
	}

	/**
	 * 获取用户设置的Locale
	 */
	public Locale getLocale() {
		return this.locale;
	}

	/**
	 * 获取用户时区
	 */
	public TimeZone getTimeZone() {
		return this.timeZone;
	}

	/**
	 * 获取角色列表
	 */
	public Set<String> getAuthorities() {
		return Collections.unmodifiableSet(this.authorities);
	}

	/**
	 * 增加角色
	 */
	public void addAuthority(String role) {
		this.authorities.add(role);
	}

	public long getCreatedTime() {
		return this.createdTime;
	}

	/**
	 * 最后更新时间
	 */
	public long getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public void addAuthorities(Set<String> authorities) {
		if (authorities != null) {
			this.authorities.addAll(authorities);
		}
	}

	public void setLastUpdatedTime(long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * Get UserProperties
	 * 
	 * @return
	 */
	public Map<String, Serializable> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	/**
	 * 增加额外属性
	 * 
	 * @param properties
	 */
	public void addProperties(Map<String, Serializable> properties) {
		for (Map.Entry<String, Serializable> entry : properties.entrySet()) {
			addProperty(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 增加额外属性
	 * 
	 * @param property
	 * @param value
	 */
	public void addProperty(String property, Serializable value) {
		this.properties.put(property, value);
	}

	/**
	 * 解析给定的属性值.
	 * 
	 * @param property 需要解析的属性
	 * @return 返回解析后的结果
	 */
	public Serializable getProperty(String property) {
		if (StringUtils.isBlank(property)) {
			return null;
		}
		if (hasProperty(property)) {
			return this.properties.get(property);
		}
		return null;
	}

	protected String getPropertyCacheKey(String property) {
		return getGuid() + ":" + property;
	}

	/**
	 * 是否已解析.
	 * 
	 * @param property 传入需要检测的属性
	 * @return 返回是否已解析
	 */
	public boolean hasProperty(String property) {
		return properties.containsKey(property);
	}

	/**
	 * 销毁
	 */
	public void destory() {
		this.properties.clear();
		this.authorities.clear();
	}

	public String getGuid() {
		if (guid == null) {
			guid = generateKey();
		}
		return guid;
	}

	private String generateKey() {
		try {
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("username", getAccount());
			values.put("source", getSource());
			values.put("type", getType());
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		} catch (NoSuchAlgorithmException nsae) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", nsae);
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", uee);
		}
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isOwnerOf(Object key) {
		return this.getTenantIds() != null && getTenantIds().contains(key);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public SubjectCredential getCredential() {
		return credential;
	}

	public void setCredential(SubjectCredential credential) {
		this.credential = credential;
	}

	public String getSource() {
		return source;
	}

	public SubjectType getType() {
		return type;
	}

	public Set<String> getTenantIds() {
		return tenantIds;
	}

	public void setTenantIds(Set<String> tenantIds) {
		this.tenantIds = tenantIds;
	}

}
