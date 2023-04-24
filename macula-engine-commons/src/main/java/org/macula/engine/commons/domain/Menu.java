package org.macula.engine.commons.domain;

import java.io.Serializable;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.ToString;
import org.macula.engine.assistant.constants.Versions;

@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
@ToString(callSuper = true)
public class Menu extends Resource {

	private static final long serialVersionUID = Versions.serialVersion;

	@Column(name = "OPEN_MODE", length = 10)
	private String openMode;

	@Column(name = "HELP_URI", length = 255)
	private String helpUri;

	@Column(name = "IS_BAR_ITEM", nullable = false)
	private boolean toolbarItem;

	public Menu clone(Menu menu) {
		super.clone(menu);
		menu.setOpenMode(getOpenMode());
		menu.setHelpUri(getHelpUri());
		menu.setToolbarItem(isToolbarItem());
		return menu;
	}

	@Override
	@Transient
	public Map<String, Serializable> getAttributes() {
		addAttribute("openMode", getOpenMode());
		addAttribute("helpUri", getHelpUri());
		addAttribute("boolbarItem", isToolbarItem());
		return super.getAttributes();
	}

	public String getOpenMode() {
		return openMode;
	}

	public boolean isToolbarItem() {
		return toolbarItem;
	}

	/**
	 * @param openMode the openMode to set
	 */
	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public void setToolbarItem(boolean toolbarItem) {
		this.toolbarItem = toolbarItem;
	}

	/**
	 * @return the helpUri
	 */
	public String getHelpUri() {
		return helpUri;
	}

	/**
	 * @param helpUri the helpUri to set
	 */
	public void setHelpUri(String helpUri) {
		this.helpUri = helpUri;
	}

}
