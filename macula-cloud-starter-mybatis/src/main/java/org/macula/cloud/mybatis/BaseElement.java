package org.macula.cloud.mybatis;

import java.util.Date;

public class BaseElement extends MybatisPojo {

	private static final long serialVersionUID = 1L;

	private Long applicationId; // bigint(20) NOT NULL COMMENT '应用id',

	private String name; // varchar(64) NOT NULL COMMENT '菜单名',

	private String description; // varchar(255) COMMENT '菜单描述',

	private String httpMethod; // varchar(10) COMMENT 'HTTP请求方式',

	private Integer sortNo; // int(5) COMMENT '排序号',

	private String icon; // varchar(32) COMMENT '图标',

	private Long parentId; // bigint(20) NOT NULL DEFAULT 0 COMMENT '父级菜单，没上级默认0',

	private Date effectiveTime; // datetime COMMENT '有效开始时间',

	private Date inactiveTime; // datetime COMMENT '有效结束时间',

	private Integer isDeletable; // 是否可删除

	private String comments; // varchar(64) COMMENT '备注',

	public BaseElement() {
	}

	public BaseElement(Long createBy, Date createTime, Long updateBy, Date updateTime, Long applicationId, String name,
			String description, String httpMethod, Integer sortNo, String icon, Long parentId, Date effectiveTime,
			Date inactiveTime, String comments) {
		super(createBy, createTime, updateBy, updateTime);
		this.applicationId = applicationId;
		this.name = name;
		this.description = description;
		this.httpMethod = httpMethod;
		this.sortNo = sortNo;
		this.icon = icon;
		this.parentId = parentId;
		this.effectiveTime = effectiveTime;
		this.inactiveTime = inactiveTime;
		this.comments = comments;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getIsDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(Integer isDeletable) {
		this.isDeletable = isDeletable;
	}
}
