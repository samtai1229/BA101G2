package com.emt_disp_list.model;

import com.emt_dispatch.model.EmtDispatchVO;
import com.equipment.model.EquipmentVO;

public class EmtDispListVO implements java.io.Serializable{
	private static final long serialVersionUID = 5391361785448194239L;
	private String edno;
	private String emtno;
	
	//hibernate
	private EmtDispatchVO emtDispatchVO;
	private EquipmentVO equipmentVO;
	
	public EmtDispListVO() {
		super();
	}

	public String getEdno() {
		return edno;
	}

	public void setEdno(String edno) {
		this.edno = edno;
	}

	public String getEmtno() {
		return emtno;
	}

	public void setEmtno(String emtno) {
		this.emtno = emtno;
	}

	public EquipmentVO getEquipmentVO() {
		return equipmentVO;
	}

	public void setEquipmentVO(EquipmentVO equipmentVO) {
		this.equipmentVO = equipmentVO;
	}

	public EmtDispatchVO getEmtDispatchVO() {
		return emtDispatchVO;
	}

	public void setEmtDispatchVO(EmtDispatchVO emtDispatchVO) {
		this.emtDispatchVO = emtDispatchVO;
	}
	
	
}
