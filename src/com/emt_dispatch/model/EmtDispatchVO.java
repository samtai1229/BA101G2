package com.emt_dispatch.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class EmtDispatchVO implements Serializable{
	private static final long serialVersionUID = -9000862051131910969L;
	private String edno;
	private String locno;
	private Timestamp demanddate;
	private Timestamp closeddate;
	private String prog;
	
	public EmtDispatchVO() {
		super();
	}

	public String getEdno() {
		return edno;
	}

	public void setEdno(String edno) {
		this.edno = edno;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public Timestamp getDemanddate() {
		return demanddate;
	}

	public void setDemanddate(Timestamp demanddate) {
		this.demanddate = demanddate;
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
