package com.sec_ord.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class SecOrdVO implements Serializable {

	private String secordNO;
	private String memNO;
	private String motorNO;
	private Timestamp secordDate;
	private String secordStatus;

	public SecOrdVO(){}

	
	public String getSecondNo() {
		return secordNO;
	}

	public void setSecondNo(String second_no) {
		this.secordNO = second_no;
	}

	public String getMemNo() {
		return memNO;
	}

	public void setMemNo(String mem_no) {
		this.memNO = mem_no;
	}

	public String getMotorNo() {
		return motorNO;
	}

	public void setMotorNo(String motor_no) {
		this.motorNO= motor_no;
	}

	public Timestamp getSecondOrderDate() {
		return secordDate;
	}

	public void setSecondOrderDate(Timestamp second_order_date) {
		this.secordDate = second_order_date;
	}

	public String getSecondStatus() {
		return secordStatus;
	}

	public void setSecondStatus(String second_status) {
		this.secordStatus = second_status;
	}

}