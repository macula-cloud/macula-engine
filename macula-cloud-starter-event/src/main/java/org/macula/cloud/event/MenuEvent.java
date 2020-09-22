package org.macula.cloud.event;

import org.macula.cloud.core.event.CRUD;
import org.macula.cloud.core.event.MaculaEvent;

public class MenuEvent extends MaculaEvent<Long> {

	private static final long serialVersionUID = 1L;

	private final CRUD crud;

	public MenuEvent(Long id, CRUD crud) {
		super(id);
		this.crud = crud;
	}

	public CRUD getCrud() {
		return crud;
	}

}
