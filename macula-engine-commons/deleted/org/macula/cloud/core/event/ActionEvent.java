package org.macula.cloud.core.event;

public class ActionEvent extends MaculaEvent<Long> {

	private static final long serialVersionUID = Versions.serialVersion;

	private final CRUD crud;

	public ActionEvent(Long id, CRUD crud) {
		super(id);
		this.crud = crud;
	}

	public CRUD getCrud() {
		return crud;
	}

}
