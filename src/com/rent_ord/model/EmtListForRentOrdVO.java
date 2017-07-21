package com.rent_ord.model;

public class EmtListForRentOrdVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private RentOrdVO roVO;
	private EquipmentForRentOrdVO emtVO;
	
	public EmtListForRentOrdVO() {
		super();
	}
	
	public RentOrdVO getRoVO() {
		return roVO;
	}

	public void setRoVO(RentOrdVO roVO) {
		this.roVO = roVO;
	}

	public EquipmentForRentOrdVO getEmtVO() {
		return emtVO;
	}

	public void setEmtVO(EquipmentForRentOrdVO emtVO) {
		this.emtVO = emtVO;
	}

}
