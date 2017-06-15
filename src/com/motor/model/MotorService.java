package com.motor.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class MotorService {

	private MotorDAO_interface dao;

	public MotorService() {
		dao = new MotorDAO();
	}

	public MotorVO addMotor(String modtype, String plateno, String engno, java.sql.Timestamp manudate, Integer mile,
			String locno, String status, String note) {
		System.out.println("MotorService, addMotor in");

		MotorVO motorVO = new MotorVO();
		motorVO.setModtype(modtype);
		motorVO.setPlateno(plateno);
		motorVO.setEngno(engno);
		motorVO.setManudate(manudate);
		motorVO.setMile(mile);
		motorVO.setLocno(locno);
		motorVO.setStatus(status);
		motorVO.setNote(note);
		dao.insert(motorVO);

		return motorVO;

	}

	public MotorVO updateMotor(String motno, String modtype, String plateno, String engno, java.sql.Timestamp manudate,
			Integer mile, String locno, String status, String note) {

		MotorVO motorVO = new MotorVO();
		motorVO.setMotno(motno);
		motorVO.setModtype(modtype);
		motorVO.setPlateno(plateno);
		motorVO.setEngno(engno);
		motorVO.setManudate(manudate);
		motorVO.setMile(mile);
		motorVO.setLocno(locno);
		motorVO.setStatus(status);
		motorVO.setNote(note);
		dao.update(motorVO);

		return motorVO;

	}

	public void deleteMotor(String motno) {
		dao.delete(motno);
	}

	public MotorVO findByPK(String motno) {
		return dao.findByPrimaryKey(motno);
	}

	public List<MotorVO> getAll() {
		return dao.getAll();
	}

	public Set<MotorVO> getMotorsByModelType(String modtype) {
		return dao.getMotorsByModelType(modtype);
	}

	public Set<MotorVO> getMotorsByLocNo(String locno) {
		return dao.getMotorsByLocNo(locno);
	}

	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		return dao.getMotorsByManuDate(start_time, end_time);
	}

}
