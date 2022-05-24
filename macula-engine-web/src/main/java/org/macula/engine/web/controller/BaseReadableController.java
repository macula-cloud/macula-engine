package org.macula.engine.web.controller;

import java.io.Serializable;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.macula.engine.assistant.domain.Entity;
import org.macula.engine.assistant.protocol.Result;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>BaseReadableController </p>
 */
public abstract class BaseReadableController<E extends Entity, ID extends Serializable> implements ReadableController<E, ID> {

	@Operation(summary = "分页查询数据", description = "通过pageNumber和pageSize获取分页数据", responses = {
			@ApiResponse(description = "单位列表", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))) })
	@Parameters({
			@Parameter(name = "page", required = true, in = ParameterIn.PATH, description = "分页Bo对象", schema = @Schema(implementation = Pageable.class)) })
	@GetMapping
	public Result<Map<String, Object>> findByPage(@Validated Pageable page) {
		return ReadableController.super.findByPage(page.getPageNumber(), page.getPageSize());
	}
}
