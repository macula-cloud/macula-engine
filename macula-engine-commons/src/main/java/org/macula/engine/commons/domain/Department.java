package org.macula.engine.commons.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Department extends CompanyAsset {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "SIMPLE_NAME", nullable = false, length = 50)
	@NotNull
	private String simpleName;
	@Column(name = "NICK_NAME", length = 255)
	private String nickname;
	@Column(name = "ORDERED", nullable = false)
	private int ordered;
	@Column(name = "LEADER_ACCOUNT", length = 255)
	private String leaderAccount;
	@Column(name = "ORG_LEVEL", nullable = false)
	private int level;
	@Column(name = "ORG_TYPE", nullable = false, length = 2)
	@NotNull
	private String type;
	@Column(name = "ENABLED", nullable = false)
	private boolean enabled;
	@Column(name = "FOUND_DATE", nullable = false)
	@NotNull
	private Date foundDate;

	public Department clone(Department entity) {
		super.clone(entity);
		entity.setDescription(getDescription());
		entity.setSimpleName(getSimpleName());
		entity.setNickname(getNickname());
		entity.setOrdered(getOrdered());
		entity.setLeaderAccount(getLeaderAccount());
		entity.setLevel(getLevel());
		entity.setType(getType());
		entity.setEnabled(isEnabled());
		entity.setFoundDate(getFoundDate());
		return entity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getOrdered() {
		return ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	public String getLeaderAccount() {
		return leaderAccount;
	}

	public void setLeaderAccount(String leaderAccount) {
		this.leaderAccount = leaderAccount;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

}
