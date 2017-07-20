package com.equipment.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emt_cate.model.EmtCateVO;
import com.location.model.LocationVO;


public class EquipmentVO implements Serializable{
	private static final long serialVersionUID = -2050144125915023005L;
	
	private String emtno;
	private String ecno;
	private String locno;
	private Timestamp purchdate;
	private String status;
	private String note;

	// 以下hibernate用
	private EmtCateVO emtCateVO;
	private LocationVO locationVO;
	

	public EquipmentVO() {
		super();
	}

	public String getEmtno() {
		return emtno;
	}

	public void setEmtno(String emtno) {
		this.emtno = emtno;
	}

	public String getEcno() {
		return ecno;
	}

	public void setEcno(String ecno) {
		this.ecno = ecno;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String locno) {
		this.locno = locno;
	}

	public Timestamp getPurchdate() {
		return purchdate;
	}

	public void setPurchdate(Timestamp purchdate) {
		this.purchdate = purchdate;
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

	public EmtCateVO getEmtCateVO() {
		return emtCateVO;
	}

	public void setEmtCateVO(EmtCateVO emtCateVO) {
		this.emtCateVO = emtCateVO;
	}

	public LocationVO getLocationVO() {
		return locationVO;
	}

	public void setLocationVO(LocationVO locationVO) {
		this.locationVO = locationVO;
	}

}
