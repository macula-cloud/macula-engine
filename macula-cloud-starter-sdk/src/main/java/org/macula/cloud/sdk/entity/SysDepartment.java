package org.macula.cloud.sdk.entity;

import java.io.Serializable;
import java.util.Date;

public class SysDepartment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long createBy; // bigint(20) COMMENT '创建人',

	private Date createTime; // datetime COMMENT '创建时间',

	private Long updateBy; // bigint(20) COMMENT '更新人',

	private Date updateTime; // datetime COMMENT '更新时间',

	private String name; // varchar(256) NOT NULL COMMENT '部门名字'

	private String shortName; // varchar(32) NOT NULL COMMENT '简称'

	private String englishName; // varchar(128) DEFAULT NULL COMMENT '部门英文名'

	private String code; // varchar(32) NOT NULL COMMENT '编号（对应业务系统CODE）'

	private String businessUnit; // varchar(32) DEFAULT NULL COMMENT '业务单元'

	private String departmentLeader; // varchar(32) DEFAULT NULL COMMENT '部门主管'

	private String businessLicense; // varchar(64) DEFAULT NULL COMMENT '营业执照号'

	private String contact; // varchar(32) DEFAULT NULL COMMENT '营业执照号'

	private String mail; // varchar(64) DEFAULT NULL COMMENT '邮箱地址'

	private String source; // varchar(16) DEFAULT 'NEW' COMMENT '来源，如SRM或SNC，如果新增用NEW'

	private Integer isDeleted; // tinyint(1) DEFAULT '0' COMMENT '删除标志，0未删除，1已删除'

	private Long companyId; // bigint(20) DEFAULT NULL COMMENT '所属公司id'

	private Long parentId; // bigint(20) DEFAULT NULL COMMENT '上级部门id'

	private String businessId; // 同步过来的数据的主键

	private String comments; // varchar(64) COMMENT '备注',

	private Long[] parentList;

	public SysDepartment() {
	}

	public SysDepartment(String name, Long companyId, Integer isDeleted) {
		this.name = name;
		this.companyId = companyId;
		this.isDeleted = isDeleted;
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

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getDepartmentLeader() {
		return departmentLeader;
	}

	public void setDepartmentLeader(String departmentLeader) {
		this.departmentLeader = departmentLeader;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setParentListStr(String parentListStr) {
	}

	public Long[] getParentList() {
		return this.parentList;
	}

	public void setParentList(Long[] parentList) {
		this.parentList = parentList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
