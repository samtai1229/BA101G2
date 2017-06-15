package com.motor_disp_list.model;

public class MotorDispListVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String mdno;
	private String motno;

	public MotorDispListVO() {

	}

	public String getMdno() {
		return mdno;
	}

	public void setMdno(String mdno) {
		this.mdno = mdno;
	}

	public String getMotno() {
		return motno;
	}

	public void setMotno(String motno) {
		this.motno = motno;
	}

}
