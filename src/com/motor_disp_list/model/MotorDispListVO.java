package com.motor_disp_list.model;

import java.io.Serializable;

import com.motor.model.MotorVO;
import com.motor_dispatch.model.MotorDispatchVO;
import com.motor_model.model.MotorModelVO;

public class MotorDispListVO implements Serializable {
	private static final long serialVersionUID = -5137444861039543022L;
	
	private String mdno;
	private String motno;
	private String modtype;
	
	private MotorDispatchVO motorDispatchVO;
	private MotorVO motorVO;
	
	public MotorDispListVO() {
		super();
	}

	public MotorDispatchVO getMotorDispatchVO() {
		return motorDispatchVO;
	}

	public void setMotorDispatchVO(MotorDispatchVO motorDispatchVO) {
		this.motorDispatchVO = motorDispatchVO;
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

	public String getModtype() {
		return modtype;
	}

	public void setModtype(String modtype) {
		this.modtype = modtype;
	}

	public MotorVO getMotorVO() {
		return motorVO;
	}

	public void setMotorVO(MotorVO motorVO) {
		this.motorVO = motorVO;
	}
	
	

}
