package com.rent_ord.model;

import java.util.List;

import com.motor.model.MotorVO;

public class MotorForRentOrdService {
	private MotorForRentOrdDAO_interface dao;

	public MotorForRentOrdService() {
		dao = new MotorForRentOrdDAO();
	};
	
	public List<String> getMotnosByAllRentalStatus(){
		return dao.getMotnosByAllRentalStatus();
	};
	
	public List<String> getMotnosByModelType(String modtype){
		return dao.getMotnosByModelType(modtype);
	}
	
	public List<MotorVO> getMotorsByRentalSide(){
		return dao.getMotorsByRentalSide();
	}
	
}
