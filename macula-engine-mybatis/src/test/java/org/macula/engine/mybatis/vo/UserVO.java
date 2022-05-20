package org.macula.engine.mybatis.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.macula.engine.mybatis.enums.GenderEnum;

/**
 * <p>
 * <b>UserVO</b> User Value Object
 * </p>
 */
@Getter
@Setter
public class UserVO {
	private Long id;
	private String name;
	private String email;
	private GenderEnum gender;
	private long version;
	private String createBy;
	private LocalDateTime createTime;
	private String lastUpdateBy;
	private LocalDateTime lastUpdateTime;
}
