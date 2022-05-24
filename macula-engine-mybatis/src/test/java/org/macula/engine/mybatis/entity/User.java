package org.macula.engine.mybatis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import org.macula.engine.assistant.constants.Versions;
import org.macula.engine.mybatis.enums.GenderEnum;

/**
 * <p>
 * <b>User</b> 用户实体
 * </p>
 */
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = Versions.serialVersion;

	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String name;

	private Integer age;

	private String email;

	private GenderEnum gender;

	@Version
	private long version;

	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String lastUpdateBy;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime lastUpdateTime;
}
