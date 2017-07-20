package com.emt_dispatch.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.emt_disp_list.model.EmtDispListVO;


public class EmtDispatchVO implements Serializable{
	private static final long serialVersionUID = -9000862051131910969L;
	private String edno;
	private String locno;
	private Timestamp demanddate;
	private Timestamp closeddate;
	private String prog;
	private Set<EmtDispListVO> emtDispLists = new HashSet<EmtDispListVO>();
	
	public EmtDispatchVO() {
		checkNull();
	}
	private void checkNull() {//若為空值則給預設值
		if (demanddate == null) {
			demanddate = new Timestamp(System.currentTimeMillis());
		}
		if (prog == null) {
			prog = "request";
			return;
		}
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

	public Set<EmtDispListVO> getEmtDispLists() {
		return emtDispLists;
	}

	public void setEmtDispLists(Set<EmtDispListVO> emtDispLists) {
		this.emtDispLists = emtDispLists;
	}

}
