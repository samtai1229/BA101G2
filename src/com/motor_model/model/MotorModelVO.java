package com.motor_model.model;



import java.util.*;

import com.motor.model.MotorVO;

public class MotorModelVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String modtype;
	private String brand;
	private Integer displacement;
	private String name;
	private Integer renprice;
	private Integer saleprice;
	private byte[] motpic;
	private String intro;
	//以下為hibernate用
	private Set<MotorVO> motors = new HashSet<MotorVO>();

	public MotorModelVO() {

	}

	public String getModtype() {
		return modtype;
	}

	public void setModtype(String modtype) {
		this.modtype = modtype;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRenprice() {
		return renprice;
	}

	public void setRenprice(Integer renprice) {
		this.renprice = renprice;
	}

	public Integer getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(Integer saleprice) {
		this.saleprice = saleprice;
	}

	public byte[] getMotpic() {
		return motpic;
	}

	public void setMotpic(byte[] motpic) {
		this.motpic = motpic;
	}
	
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Set<MotorVO> getMotors() {
		return motors;
	}

	public void setMotors(Set<MotorVO> motors) {
		this.motors = motors;
	}

}
