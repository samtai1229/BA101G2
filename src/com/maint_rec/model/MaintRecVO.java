package com.maint_rec.model;

import java.sql.Timestamp;

public class MaintRecVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String maintno;
	private String motno;
	private Timestamp startdate;
	private Timestamp enddate;
	private String cont;

	public MaintRecVO() {

	}

	public String getMaintno() {
		return maintno;
	}

	public void setMaintno(String maintno) {
		this.maintno = maintno;
	}

	public String getMotno() {
		return motno;
	}

	public void setMotno(String motno) {
		this.motno = motno;
	}

	public Timestamp getStartdate() {
		return startdate;
	}

	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}

	public Timestamp getEnddate() {
		return enddate;
	}

	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

}
