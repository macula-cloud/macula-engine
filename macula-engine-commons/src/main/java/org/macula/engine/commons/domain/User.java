package org.macula.engine.commons.domain;

import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.commons.utils.SystemUtils;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class User extends AbstractAuditable<Long> {

	/** 用户名 */
	@Column(name = "USER_NAME", length = 50, nullable = false)
	private String username;

	/** 密码 */
	@Column(name = "PASSWORD", length = 250, nullable = true)
	@JsonIgnore
	@XmlTransient
	private String password;

	/** 用户头像 */
	@Column(name = "AVATAR")
	private String avatar;

	/** 用户来源 */
	@Column(name = "USER_SOURCE", length = 20, nullable = false)
	private String source;

	/** 用户编号 */
	@Column(name = "USER_NO", length = 50, nullable = false)
	private String account;

	/** 用户姓名 */
	@Column(name = "REAL_NAME", length = 50, nullable = true)
	private String realname;

	/** 用户别名 */
	@Column(name = "NICK_NAME", length = 150, nullable = true)
	private String nickname;

	/** 用户所属公司 */
	@Column(name = "COMPANY_CODE", length = 50, nullable = true)
	private String companyCode;

	/** 用户所属组织 */
	@Column(name = "ORG_CODE", length = 50, nullable = true)
	private String orgCode;

	/** 用户兼职组织 */
	@Column(name = "L2_ORG_CODE", length = 50, nullable = true)
	private String l2OrgCode;

	/** 生效日期 */
	@Column(name = "EFFECTIVE_DATE", nullable = false)
	private Date effectiveDate = SystemUtils.getCurrentTime();

	/** 失效日期 */
	@Column(name = "INACTIVE_DATE", nullable = true)
	private Date inactiveDate;

	/** 是否有效 */
	@Column(name = "IS_ENABLED", length = 1, nullable = false)
	private Boolean enabled;

	/** 是否有效 */
	@Column(name = "IS_LOCKED", length = 1, nullable = false)
	private Boolean locked;

	/** 上级用户帐号 */
	@Column(name = "LEADER_ACCOUNT", length = 250, nullable = true)
	private String leaderAccount;

	/**
	 * 用户区域 由语言与地区字串拼接而成，中间用下划线连接。 语言字串遵循 ISO-639 标准，地区字串遵循 ISO-3166 标准。
	 */
	@Column(name = "LOCALE", length = 20, nullable = false)
	private String locale = Locale.getDefault().toString();

	/** 用户时区 采用TimeZone中 getAvailableIDs 方法获取的时区ID */
	@Column(name = "TIMEZONE", length = 50, nullable = false)
	private String timezone = "GM+08";

	/** 用户主题 */
	@Column(name = "THEME", length = 20, nullable = false)
	private String theme = "default";

	/** 性别'F'：女；'M'：男 */
	@Column(name = "SEX", length = 1, nullable = false)
	private String gender;

	/** 移动电话 */
	@Column(name = "MOBILE", length = 50, nullable = true)
	private String mobile;

	/** 商务电话 */
	@Column(name = "OFFICE_TEL", length = 50, nullable = true)
	private String officeTel;

	/** 商务传真 */
	@Column(name = "OFFICE_FAX", length = 50, nullable = true)
	private String officeTax;

	/** 电子邮箱 */
	@Column(name = "EMAIL", length = 50, nullable = true)
	private String email;

	/** 电子邮箱2 */
	@Column(name = "EMAIL2", length = 50, nullable = true)
	private String email2;

	/** 电子邮箱3 */
	@Column(name = "EMAIL3", length = 50, nullable = true)
	private String email3;

	@Column(name = "OFFICE_TITLE", length = 50, nullable = true)
	private String officeTitle;

	@Column(name = "OFFICE_POST", length = 50, nullable = true)
	private String officePost;

	/** 国家、地区 */
	@Column(name = "REGION", length = 250, nullable = true)
	private String region;

	/** 省 */
	@Column(name = "PROVINCE", length = 250, nullable = true)
	private String province;

	/** 市 */
	@Column(name = "CITY", length = 250, nullable = true)
	private String city;

	/** 区 */
	@Column(name = "DISTRICT", length = 250, nullable = true)
	private String district;

	/** 镇 */
	@Column(name = "TOWN", length = 250, nullable = true)
	private String town;

	/** 办公地点 */
	@Column(name = "ADDR", length = 250, nullable = true)
	private String address;

	/** 扩展字段 */
	@Column(name = "FIELD1", length = 250, nullable = true)
	private String field1;

	@Column(name = "FIELD2", length = 250, nullable = true)
	private String field2;

	@Column(name = "FIELD3", length = 250, nullable = true)
	private String field3;

	public User clone(User user) {
		super.clone(user);
		this.setAccount(user.getAccount());
		this.setAddress(user.getAddress());
		this.setAvatar(user.getAvatar());
		this.setCity(user.getCity());
		this.setDistrict(user.getDistrict());
		this.setEffectiveDate(user.getEffectiveDate());
		this.setEmail(user.getEmail());
		this.setEmail2(user.getEmail2());
		this.setEmail3(user.getEmail3());
		this.setEnabled(user.getEnabled());
		this.setLocked(user.getLocked());
		this.setField1(user.getField1());
		this.setField2(user.getField2());
		this.setField3(user.getField3());
		this.setGender(user.getGender());
		this.setId(user.getId());
		this.setInactiveDate(user.getInactiveDate());
		this.setL2OrgCode(user.getL2OrgCode());
		this.setLeaderAccount(user.getLeaderAccount());
		this.setLocale(user.getLocale());
		this.setMobile(user.getMobile());
		this.setNickname(user.getNickname());
		this.setOfficePost(user.getOfficePost());
		this.setOfficeTax(user.getOfficeTax());
		this.setOfficeTel(user.getOfficeTel());
		this.setOfficeTitle(user.getOfficeTitle());
		this.setOrgCode(user.getOrgCode());
		this.setPassword(user.getPassword());
		this.setProvince(user.getProvince());
		this.setRealname(user.getRealname());
		this.setRegion(user.getRegion());
		this.setSource(user.getSource());
		this.setTheme(user.getTheme());
		this.setTimezone(user.getTimezone());
		this.setTown(user.getTown());
		this.setUsername(user.getUsername());
		return this;
	}

}
