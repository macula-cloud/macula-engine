package org.macula.cloud.sdk.event;

public class ActionEvent extends MaculaEvent<Long> {
	
	private static final long serialVersionUID = 1L;

	private final CRUD crud;

	public ActionEvent(Long id, CRUD crud) {
		super(id);
		this.crud = crud;
	}

	public CRUD getCrud() {
		return crud;
	}

}
