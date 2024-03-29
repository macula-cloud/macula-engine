package org.macula.cloud.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysUser implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	private Long id;

	private Long createBy; // bigint(20) COMMENT '创建人',

	private Date createTime; // datetime COMMENT '创建时间',

	private Long updateBy; // bigint(20) COMMENT '更新人',

	private Date updateTime; // datetime COMMENT '更新时间',

	private String userName; // VARCHAR(32) NOT NULL COMMENT '登录名',

	private String fullName; // VARCHAR(100) NOT NULL COMMENT '用户名',

	private String nickName; // VARCHAR(100) DEFAULT NULL COMMENT '用户昵称',

	private String password;

	private String sex; // TINYINT(1) DEFAULT 0 COMMENT '用户昵称',

	private String code; // VARCHAR(32) NOT NULL COMMENT '编号(业务系统过来的必须唯一)',

	private Date birthday; // DATETIME COMMENT '出生日期',

	private String grade; // VARCHAR(10) COMMENT '职级类别',

	private String position; // VARCHAR(32) COMMENT '岗位',

	private String phone; // VARCHAR(32) COMMENT '手机号',

	private String mail; // VARCHAR(32) COMMENT '邮箱地址',

	private String imageUrl; // VARCHAR(256) COMMENT '邮箱地址',

	private String source; // VARCHAR(16) NOT NULL DEFAULT 'NEW' COMMENT '来源，如SRM或SNC，如果新增用NEW',

	private Integer isEnabled; // TINYINT(1) DEFAULT 1 COMMENT '可用状态，0不可用，1可用',

	private Integer isDeleted; // TINYINT(1) DEFAULT 0 COMMENT '删除标志，0未删除，1已删除',

	private Long departmentId; // BIGINT(20) NOT NULL COMMENT '所属部门',

	private Long companyId;

	private String salt;

	private String comments; // varchar(64) COMMENT '备注',

	private Long[] departmentIds;

	private List<Long> roleIds;

	public SysUser() {
	}

	public SysUser(Long departmentId, Long companyId, Integer isDeleted, Integer isEnabled) {
		this.departmentId = departmentId;
		this.companyId = companyId;
		this.isDeleted = isDeleted;
		this.isEnabled = isEnabled;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public void setDepartmentIdStr(String departmentIdStr) {
	}

	public Long[] getDepartmentIds() {
		return this.departmentIds;

	}

	public void setDepartmentIds(Long[] departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
