package com.equipment.model;

import java.sql.Timestamp;

public class EquipmentVO {
	private String emtno;
	private String ecno;
	private String locno;
	private Timestamp purchdate;
	private String status;
	private String note;
	
	
	public EquipmentVO() {
		super();
	}

	public String getEmtno() {
		return emtno;
	}

	public void setEmtno(String emtno) {
		this.emtno = emtno;
	}

	public String getEcno() {
		return ecno;
	}

	public void setEcno(String ecno) {
		this.ecno = ecno;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public Timestamp getPurchdate() {
		return purchdate;
	}

	public void setPurchdate(Timestamp purchdate) {
		this.purchdate = purchdate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
