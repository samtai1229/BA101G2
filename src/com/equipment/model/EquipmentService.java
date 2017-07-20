package com.equipment.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import com.motor.model.MotorVO;


public class EquipmentService {
	private EquipmentDAO_interface dao;

	public EquipmentService() {
		dao = new EquipmentDAO();
	}

	public EquipmentVO addEquipment(String ecno, Timestamp purchdate, String note) {

		EquipmentVO equipmentVO = new EquipmentVO();

		equipmentVO.setEcno(ecno);
		equipmentVO.setPurchdate(purchdate);
		equipmentVO.setNote(note);
		dao.insert(equipmentVO);

		return equipmentVO;
	}

	public EquipmentVO updateEquipment(String emtno, String ecno, String locno, Timestamp purchdate, String status,
			String note) {

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

	// 以下為Hibernate用
	public void insertByHib(EquipmentVO equipmentVO) {
		dao.insertByHib(equipmentVO);
	}

	public void updateByHib(EquipmentVO equipmentVO) {
		dao.updateByHib(equipmentVO);
	}

	public void deleteByHib(String emtno) {
		dao.deleteByHib(emtno);
	}

	public EquipmentVO findByPkByHib(String emtno) {
		return dao.findByPkByHib(emtno);
	}

	public List<EquipmentVO> getAllByHib() {
		return dao.getAllByHib();
	}

	public List<EquipmentVO> getEmtsByEcnoByHib(String ecno) {
		return dao.getEmtsByEcnoByHib(ecno);
	}

	public void updateStatusByHib(String emtno, String status) {
		dao.updateStatusByHib(emtno, status);
	}
	
	public List<String> getEcnoByLocnoByHib(String locno) {
		return dao.getEcnoByLocnoByHib(locno);
	}
	
	public List<EquipmentVO> getEmtsByEcnoAndLocnoByHib(String ecno, String locno){
		return dao.getEmtsByEcnoAndLocnoByHib(ecno, locno);
	}
}