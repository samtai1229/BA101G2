package com.rent_ord.model;

import java.util.List;

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
	
}
