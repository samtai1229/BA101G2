package com.sec_ord.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class SecOrdVO implements Serializable {

	private String sono;
	private String memno;
	private String motorno;
	private Timestamp buildtime;
	private String status;

	public SecOrdVO(){}

	
	public String getSono() {
		return sono;
	}

	public void setSono(String sono) {
		this.sono = sono;
	}

	public String getMemno() {
		return memno;
	}

	public void setMemno(String memno) {
		this.memno = memno;
	}

	public String getMotorno() {
		return motorno;
	}

	public void setMotorno(String motorno) {
		this.motorno= motorno;
	}

	public Timestamp getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(Timestamp buildtime) {
		this.buildtime = buildtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}