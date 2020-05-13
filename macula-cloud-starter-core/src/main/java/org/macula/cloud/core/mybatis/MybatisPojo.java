package org.macula.cloud.core.mybatis;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.macula.cloud.core.validator.group.UpdateGroup;

public abstract class MybatisPojo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull(message = "id不能为空", groups = { UpdateGroup.class })
	private Long id;

	// bigint(20) COMMENT '创建人'
	private Long createBy;

	// datetime COMMENT '创建时间'
	private Date createTime;

	// bigint(20) COMMENT '更新人'
	private Long updateBy;

	// datetime COMMENT '更新时间'
	private Date updateTime;

	public MybatisPojo() {
	}

	public MybatisPojo(Long id) {
		this.id = id;
	}

	public MybatisPojo(Long createBy, Date createTime, Long updateBy, Date updateTime) {
		this.createBy = createBy;
		this.createTime = createTime;
		this.updateBy = updateBy;
		this.updateTime = updateTime;
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
}
