package com.motor.model;

import java.sql.Timestamp;
import java.util.*;

public class MotorService {

	private MotorDAO_interface dao;

	public MotorService() {
		dao = new MotorDAO();
	}

	public MotorVO addMotor(String modtype, String plateno, String engno, java.sql.Timestamp manudate, Integer mile,
			String note) {
		System.out.println("MotorService, addMotor in");

		MotorVO motorVO = new MotorVO();
		motorVO.setModtype(modtype);
		motorVO.setPlateno(plateno);
		motorVO.setEngno(engno);
		motorVO.setManudate(manudate);
		motorVO.setMile(mile);
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

	public List<MotorVO> fuzzyGetAll(String fuzzyValue) {
		return dao.fuzzyGetAll(fuzzyValue);
	}

	public Set<MotorVO> getMotorsByModelType(String modtype) {
		return dao.getMotorsByModelType(modtype);
	}

	public List<MotorVO> getMotorsByModtypeAndLocno(String modtype, String locno) {
		return dao.getMotorsByModtypeAndLocno(modtype, locno);
	}

	public Set<MotorVO> getMotorsByLocNo(String locno) {
		return dao.getMotorsByLocNo(locno);
	}

	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		return dao.getMotorsByManuDate(start_time, end_time);
	}

	public HashSet<MotorVO> getModtypeByLocNo(String locno) {
		return dao.getModtypeByLocNo(locno);
	}
	
	public List<MotorVO> getMotorsByLocno(String locno){
		return dao.getMotorsByLocno(locno);
	}

	// 以下為Hibernate用
	public void insertByHib(MotorVO motorVO) {
		dao.insertByHib(motorVO);
	}

	public void updateByHib(MotorVO motorVO) {
		dao.updateByHib(motorVO);
	}

	public void deleteByHib(String motno) {
		dao.deleteByHib(motno);
	}

	public MotorVO findByPkByHib(String motno) {
		return dao.findByPkByHib(motno);
	}

	public List<MotorVO> getAllByHib() {
		return dao.getAllByHib();
	}
	
	public List<MotorVO> fuzzySearchByHib(String fuzzyValue){
		return dao.fuzzySearchByHib(fuzzyValue);
	}
	
	public List<MotorVO> getMotorsByModtypeByHib(String modtype){
		return dao.getMotorsByModtypeByHib(modtype);
	}
	
	public void updateStatusByHib(String motno, String status){
		dao.updateStatusByHib(motno, status);
	}

	public void updateLocnoByHib(String motno, String locno) {
		dao.updateLocnoByHib(motno, locno);
	}

}
