package org.macula.cloud.core.entity;

import java.io.Serializable;
import java.util.Date;

public class SysCompany implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long createBy; // bigint(20) COMMENT '创建人',

	private Date createTime; // datetime COMMENT '创建时间',

	private Long updateBy; // bigint(20) COMMENT '更新人',

	private Date updateTime; // datetime COMMENT '更新时间',

	private String name; // varchar(256) NOT NULL COMMENT '公司名字',

	private String shortName; // varchar(32) NOT NULL COMMENT '简称',

	private String englishName; // varchar(128) DEFAULT NULL COMMENT '公司英文名',

	private String code; // varchar(32) NOT NULL COMMENT '编号，对应SAP Vendor Code',

	private String legalPerson; // varchar(32) DEFAULT NULL COMMENT '法人',

	private String businessLicense; // varchar(64) DEFAULT NULL COMMENT '营业执照号',

	private String contact; // varchar(32) DEFAULT NULL COMMENT '营业执照号',

	private String phone; // varchar(32) DEFAULT NULL COMMENT '手机号',

	private String mail; // varchar(32) DEFAULT NULL COMMENT '邮箱地址',

	private String address; // varchar(32) DEFAULT NULL COMMENT '联系地址',

	private String source; // varchar(16) NOT NULL DEFAULT 'NEW' COMMENT '来源，如SRM或SNC，如果新增用NEW',

	private Date effectiveTime; // datetime DEFAULT NULL COMMENT '有效开始时间',

	private Date inactiveTime; // datetime DEFAULT NULL COMMENT '有效结束时间',

	private Integer isEnabled; // tinyint(1) DEFAULT '1' COMMENT '可用状态，0不可用，1可用',

	private Integer isDeleted; // tinyint(1) DEFAULT '0' COMMENT '删除标志，0未删除，1已删除',

	private String comments; // varchar(64) COMMENT '备注',

	public SysCompany() {
	}

	public SysCompany(Integer isEnabled, Integer isDeleted) {
		this.isEnabled = isEnabled;
		this.isDeleted = isDeleted;
	}

	public SysCompany(String name) {
		this.name = name;
	}

	public SysCompany(Long id, Long createBy, Date createTime, Long updateBy, Date updateTime, String name,
			String shortName, String englishName, String code, String legalPerson, String businessLicense,
			String contact, String phone, String mail, String address, String source, Date effectiveTime,
			Date inactiveTime, Integer isEnabled, Integer isDeleted, String comments) {
		this.id = id;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.name = name;
		this.shortName = shortName;
		this.englishName = englishName;
		this.code = code;
		this.legalPerson = legalPerson;
		this.businessLicense = businessLicense;
		this.contact = contact;
		this.phone = phone;
		this.mail = mail;
		this.address = address;
		this.source = source;
		this.effectiveTime = effectiveTime;
		this.inactiveTime = inactiveTime;
		this.isEnabled = isEnabled;
		this.isDeleted = isDeleted;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public Date getInactiveTime() {
		return inactiveTime;
	}

	public void setInactiveTime(Date inactiveTime) {
		this.inactiveTime = inactiveTime;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
