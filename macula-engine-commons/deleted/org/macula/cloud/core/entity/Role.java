package org.macula.cloud.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Role implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private Long id;

	private Long createBy; // bigint(20) COMMENT '创建人',

	private Date createTime; // datetime COMMENT '创建时间',

	private Long updateBy; // bigint(20) COMMENT '更新人',

	private Date updateTime; // datetime COMMENT '更新时间',

	private String code; // varchar(32) NOT NULL COMMENT '角色代码',

	private String name;

	private Long parentId; // bigint(20) NOT NULL DEFAULT 0 COMMENT '上级角色',

	private Integer isExtends; // tinyint(1) DEFAULT 1 COMMENT '是否可继承，0不可继承，1可继承',

	private Long extendsFrom; // bigint(20) COMMENT '继承自',

	private Integer isAssignable; // tinyint(1) DEFAULT 1 COMMENT '是否可分配，0不可分配，0可分配',

	private Long isResignable; // bigint(20) COMMENT '是否可下放分配，0不可分配，0可分配',

	private Integer isGroup; // tinyint(1) DEFAULT 0 COMMENT '是否是分组',

	private Integer isExecutable; // tinyint(1) DEFAULT 0 COMMENT '是否可执行，0不可执行，1可执行',

	private Integer isRuleRole; // tinyint(1) DEFAULT 0 COMMENT '是否规则角色,0不是，1是',

	private String ruleExpresssion; // varchar(1000) COMMENT '规则表达式',

	private Long companyId; // 角色所属公司

	private String comments;

	private Integer dataAuthority;// 数据权限级别

	private List<Long> permissionIds;

	private List<Long> menuIds;

	private Long[] parentList;

	public Role() {
	}

	public Role(Long id, String code) {
		this.id = id;
		this.code = code;
	}

	public Role(Long id, String name, Long parentId) {
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public Role(Long id, Long createBy, Date createTime, Long updateBy, Date updateTime, String code, String name, Long parentId, Integer isExtends,
			Long extendsFrom, Integer isAssignable, Long isResignable, Integer isGroup, Integer isExecutable, Integer isRuleRole,
			String ruleExpresssion, Long companyId, String comments, Integer dataAuthority, List<Long> permissionIds, List<Long> menuIds,
			Long[] parentList, String parentListStr) {
		this.id = id;
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
		this.isExtends = isExtends;
		this.extendsFrom = extendsFrom;
		this.isAssignable = isAssignable;
		this.isResignable = isResignable;
		this.isGroup = isGroup;
		this.isExecutable = isExecutable;
		this.isRuleRole = isRuleRole;
		this.ruleExpresssion = ruleExpresssion;
		this.companyId = companyId;
		this.comments = comments;
		this.dataAuthority = dataAuthority;
		this.permissionIds = permissionIds;
		this.menuIds = menuIds;
		this.parentList = parentList;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getIsExtends() {
		return isExtends;
	}

	public void setIsExtends(Integer isExtends) {
		this.isExtends = isExtends;
	}

	public Long getExtendsFrom() {
		return extendsFrom;
	}

	public void setExtendsFrom(Long extendsFrom) {
		this.extendsFrom = extendsFrom;
	}

	public Integer getIsAssignable() {
		return isAssignable;
	}

	public void setIsAssignable(Integer isAssignable) {
		this.isAssignable = isAssignable;
	}

	public Long getIsResignable() {
		return isResignable;
	}

	public void setIsResignable(Long isResignable) {
		this.isResignable = isResignable;
	}

	public Integer getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Integer isGroup) {
		this.isGroup = isGroup;
	}

	public Integer getIsExecutable() {
		return isExecutable;
	}

	public void setIsExecutable(Integer isExecutable) {
		this.isExecutable = isExecutable;
	}

	public Integer getIsRuleRole() {
		return isRuleRole;
	}

	public void setIsRuleRole(Integer isRuleRole) {
		this.isRuleRole = isRuleRole;
	}

	public String getRuleExpresssion() {
		return ruleExpresssion;
	}

	public void setRuleExpresssion(String ruleExpresssion) {
		this.ruleExpresssion = ruleExpresssion;
	}

	public List<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getDataAuthority() {
		return dataAuthority;
	}

	public void setDataAuthority(Integer dataAuthority) {
		this.dataAuthority = dataAuthority;
	}

	public void setParentListStr(String parentListStr) {
	}

	public Long[] getParentList() {
		return this.parentList;
	}

	public void setParentList(Long[] parentList) {
		this.parentList = parentList;
	}
}
