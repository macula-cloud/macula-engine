package org.macula.cloud.core.domain;

import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class UserSocial extends AbstractPersistable<Long> {

	private String userId;

	private String clientId;
	private String appid;

	// 基础信息
	private String uuid;
	private String unionId;
	private String openId;
	private String nickname;
	private String gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatar;
	private String location;
	private String username;
	private String email;

	// 电话信息
	private String phoneNumber;
	private String purePhoneNumber;
	private String countryCode;

	// 其他信息
	private String company;
	private String blog;
	private String remark;

	// SessionKey
	private String sessionKey;

	public UserSocial clone(UserSocial us) {
		this.setUserId(us.getUserId());
		this.setClientId(us.getClientId());
		this.setAppid(us.getAppid());
		this.setUuid(us.getUuid());
		this.setUnionId(us.getUnionId());
		this.setOpenId(us.getOpenId());
		this.setNickname(us.getNickname());
		this.setGender(us.getGender());
		this.setLanguage(us.getLanguage());
		this.setCity(us.getCity());
		this.setProvince(us.getProvince());
		this.setCountry(us.getCountry());
		this.setAvatar(us.getAvatar());
		this.setLocation(us.getLocation());
		this.setUsername(us.getUsername());
		this.setEmail(us.getEmail());
		this.setPhoneNumber(us.getPhoneNumber());
		this.setPurePhoneNumber(us.getPurePhoneNumber());
		this.setCountryCode(us.getCountryCode());
		this.setCompany(us.getCompany());
		this.setBlog(us.getBlog());
		this.setRemark(us.getRemark());
		this.setSessionKey(us.getSessionKey());
		return this;
	}

}
