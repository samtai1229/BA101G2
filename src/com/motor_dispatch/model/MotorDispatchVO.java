package com.motor_dispatch.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import com.motor_disp_list.model.MotorDispListVO;

public class MotorDispatchVO implements Serializable {
	private static final long serialVersionUID = 6080077837756344013L;
	
	private String mdno;
	private String locno;
	private Timestamp filldate;
	private Timestamp closeddate;
	private String prog;
	private Set<MotorDispListVO> motorDispLists = new HashSet<MotorDispListVO>();
	
	public MotorDispatchVO() {
		checkNull();
	}
	
	private void checkNull() {//若為空值則給預設值
		if (filldate == null) {
			filldate = new Timestamp(System.currentTimeMillis());
		}
		if (prog == null) {
			prog = "request";
			return;
		}
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

	public Set<MotorDispListVO> getMotorDispLists() {
		return motorDispLists;
	}

	public void setMotorDispLists(Set<MotorDispListVO> motorDispListVO) {
		this.motorDispLists = motorDispListVO;
	} 
}
