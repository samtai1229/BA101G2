package com.equipment.model;
import java.sql.Timestamp;
import java.util.List;


public class EquipmentService {
	private EquipmentDAO_interface dao;

	public EquipmentService() {
		dao = new EquipmentDAO();
	}

	public EquipmentVO addEquipment(String ecno, Timestamp purchdate,String note) {

		EquipmentVO equipmentVO = new EquipmentVO();

		equipmentVO.setEcno(ecno);
		equipmentVO.setPurchdate(purchdate);
		equipmentVO.setNote(note);
		dao.insert(equipmentVO);

		return equipmentVO;
	}

	public EquipmentVO updateEquipment(String emtno, String ecno, String locno, Timestamp purchdate, String status, String note) {

		EquipmentVO equipmentVO = new EquipmentVO();

		equipmentVO.setEmtno(emtno);
		equipmentVO.setEcno(ecno);
		equipmentVO.setLocno(locno);
		equipmentVO.setPurchdate(purchdate);
		equipmentVO.setStatus(status);
		equipmentVO.setNote(note);
		
		dao.update(equipmentVO);

		return equipmentVO;
	}

	public void deleteEquipment(String emtno) {
		dao.delete(emtno);
	}

	public EquipmentVO getOneEquipment(String emtno) {
		return dao.findByPrimaryKey(emtno);
	}

	public List<EquipmentVO> getAll() {
		return dao.getAll();
	}
}