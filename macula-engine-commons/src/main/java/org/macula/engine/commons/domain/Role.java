package org.macula.engine.commons.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
@Getter
@Setter
public class Role extends ApplicationAsset {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "IS_ASSIGNABLE", nullable = false)
	private boolean assignable;

	@Column(name = "IS_RESIGNABLE", nullable = false)
	private boolean resignable;

	@Column(name = "IS_RULE", nullable = false)
	private boolean ruleRole;

	@Column(name = "IS_OPPOSITE", nullable = false)
	private boolean opposite;

	@Column(name = "IS_EXECUTABLE", nullable = false)
	private boolean executable;

	@Column(name = "IS_INHERITABLE", nullable = false)
	private boolean inheritable;

	@Column(name = "INHERIT_SOURCE")
	private Long inheritSource;

	@Column(name = "RULE_EXPRESSION")
	private String ruleExpression;

	public Role clone(Role entity) {
		super.clone(entity);
		entity.setAssignable(isAssignable());
		entity.setResignable(isResignable());
		entity.setRuleRole(isRuleRole());
		entity.setOpposite(isOpposite());
		entity.setExecutable(isExecutable());
		entity.setInheritable(isInheritable());
		entity.setInheritSource(getInheritSource());
		entity.setRuleExpression(getRuleExpression());
		return entity;
	}

}
