package org.macula.cloud.cainiao;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DivisionParseResponse extends CainiaoResponse {

	private ParseDivisionResult parseDivisionResult;
	
}
