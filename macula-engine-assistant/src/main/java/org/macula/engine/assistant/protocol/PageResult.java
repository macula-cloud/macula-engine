package org.macula.engine.assistant.protocol;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.domain.Page;

/**
 * <p>
 * <b>PageResult</b> 是分页返回数据对象
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
public class PageResult<T> extends Result<List<T>> {

	private static final long serialVersionUID = 1L;

	/** 本次请求的记录数 */
	private int size;

	/** 当前页码，从零开始 */
	private int number;

	/** 总记录数 */
	private long totalElements;

	/** 总页数 */
	private int totalPages;

	/** 本页的总记录数 */
	private int numberOfElements;

	/** 是否首页 */
	private boolean firstPage;

	/** 是否最后页 */
	private boolean lastPage;

	/** 内容列表 */
	private List<T> content;

	public PageResult(Page<T> page) {
		setPage(page);
	}

	public void setPage(Page<T> page) {
		this.size = page.getSize();
		this.number = page.getNumber();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.numberOfElements = page.getNumberOfElements();
		this.firstPage = page.isFirst();
		this.lastPage = page.isLast();
		this.content = page.getContent();
	}

}
