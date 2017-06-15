package com.motor_dispatch.model;

import java.sql.Timestamp;

public class MotorDispatchVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String mdno;
	private String locno;
	private Timestamp filldate;
	private Timestamp closeddate;
	private String prog;

	public MotorDispatchVO() {

	}

	public String getMdno() {
		return mdno;
	}

	public void setMdno(String mdno) {
		this.mdno = mdno;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public Timestamp getFilldate() {
		return filldate;
	}

	public void setFilldate(Timestamp filldate) {
		this.filldate = filldate;
	}

	public Timestamp getCloseddate() {
		return closeddate;
	}

	public void setCloseddate(Timestamp closeddate) {
		this.closeddate = closeddate;
	}

	public String getProg() {
		return prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}

}
