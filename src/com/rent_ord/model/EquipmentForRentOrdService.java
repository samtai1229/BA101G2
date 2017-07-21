package com.rent_ord.model;
import java.util.List;

import com.rent_ord.model.EquipmentForRentOrdVO;


public class EquipmentForRentOrdService {
	private EquipmentForRentOrdDAO_interface dao;

	public EquipmentForRentOrdService() {
		dao = new EquipmentForRentOrdDAO();
	}

	public EquipmentForRentOrdVO getOneEquipment(String emtno) {
		return dao.findByPrimaryKey(emtno);
	}

	public List<EquipmentForRentOrdVO> getAll() {
		return dao.getAll();
	}
	
	public List<EquipmentForRentOrdVO> getEquipsByEcno(String ecno){
		return dao.getEquipsByEcno(ecno);
	}
	
}