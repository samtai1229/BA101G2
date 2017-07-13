package com.rent_ord.model;

public class EmtListVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private RentOrdVO roVO;
	private EquipmentVO emtVO;
	
	public EmtListVO() {
		super();
	}
	
	public RentOrdVO getRoVO() {
		return roVO;
	}

	public void setRoVO(RentOrdVO roVO) {
		this.roVO = roVO;
	}

	public EquipmentVO getEmtVO() {
		return emtVO;
	}

	public void setEmtVO(EquipmentVO emtVO) {
		this.emtVO = emtVO;
	}

}
