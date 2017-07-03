package com.rent_ord.model;
import java.util.List;

import com.equipment.model.EquipmentVO;


public class EquipmentForRentOrdService {
	private EquipmentForRentOrdDAO_interface dao;

	public EquipmentForRentOrdService() {
		dao = new EquipmentForRentOrdDAO();
	}

	public EquipmentVO getOneEquipment(String emtno) {
		return dao.findByPrimaryKey(emtno);
	}

	public List<EquipmentVO> getAll() {
		return dao.getAll();
	}
	
	public List<EquipmentVO> getEquipsByEcno(String ecno){
		return dao.getEquipsByEcno(ecno);
	}
	
}