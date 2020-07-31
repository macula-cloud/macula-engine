package org.macula.cloud.cainiao;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DivisionVO {
	private String divisionCode;
	private int divisionLevel;
	private String pinyin;
	private String divisionName;
	private String divisionTname;
	private String divisionId;
	private String divisionAbbName;
	@JsonProperty(value = "isdeleted")
	private boolean deleted;
	private String version;
	private String parentId;
}
