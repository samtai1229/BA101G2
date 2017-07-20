package com.motor.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.location.model.LocationVO;
import com.motor_disp_list.model.MotorDispListVO;
import com.motor_model.model.MotorModelVO;

public class MotorVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String motno;
	private String modtype;
	private String plateno;
	private String engno;
	private Timestamp manudate;
	private Integer mile;
	private String locno;
	private String status;
	private String note;
	
	//MotorModelVO:
	private String brand;
	private Integer displacement;
	private String name;
	private Integer renprice;
	private Integer saleprice;
	
	// 以下hibernate用
	private MotorModelVO motorModelVO;
	private LocationVO locationVO;
//	private Set<MotorDispListVO> motorDispLists = new HashSet<MotorDispListVO>();

	public MotorVO() {
		checkNull();
	}

	private void checkNull() {//若為空值則給預設值
		if (status == null) {
			status = "unleasable";
		}
		if (locno == null) {
			locno = "TPE00";
			return;
		}
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

	public String getPlateno() {
		return plateno;
	}

	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}

	public String getEngno() {
		return engno;
	}

	public void setEngno(String engno) {
		this.engno = engno;
	}

	public Timestamp getManudate() {
		return manudate;
	}

	public void setManudate(Timestamp manudate) {
		this.manudate = manudate;
	}

	public Integer getMile() {
		return mile;
	}

	public void setMile(Integer mile) {
		this.mile = mile;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public MotorModelVO getMotorModelVO() {
		return motorModelVO;
	}

	public void setMotorModelVO(MotorModelVO motorModelVO) {
		this.motorModelVO = motorModelVO;
	}

	public LocationVO getLocationVO() {
		return locationVO;
	}

	public void setLocationVO(LocationVO locationVO) {
		this.locationVO = locationVO;
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

//	public Set<MotorDispListVO> getMotorDispLists() {
//		return motorDispLists;
//	}
//
//	public void setMotorDispLists(Set<MotorDispListVO> motorDispLists) {
//		this.motorDispLists = motorDispLists;
//	}
}
