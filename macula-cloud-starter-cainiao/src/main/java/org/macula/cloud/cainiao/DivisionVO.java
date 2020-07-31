package org.macula.cloud.cainiao;

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
	private boolean isdeleted;
	private String version;
	private String parentId;
}
