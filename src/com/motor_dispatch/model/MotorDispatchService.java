package com.motor_dispatch.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.motor_disp_list.model.MotorDispListVO;
import com.rent_ord.model.RentOrdVO;

public class MotorDispatchService {

	private MotorDispatchDAO_interface dao;

	public MotorDispatchService() {
		dao = new MotorDispatchDAO();
	}

	public MotorDispatchVO addMotorDispatch(String locno, Set<MotorDispListVO> motorDispLists) {
		MotorDispatchVO mdVO = new MotorDispatchVO();

		mdVO.setLocno(locno);
		mdVO.setMotorDispLists(motorDispLists);
		dao.insert(mdVO);

		return mdVO;
	}

	public MotorDispatchVO updateMotorDispatch(String mdno, String locno, Timestamp filldate, Timestamp closeddate,
			String prog) {
		
		MotorDispatchVO mdVO = new MotorDispatchVO();
		mdVO.setMdno(mdno);
		mdVO.setLocno(locno);
		mdVO.setFilldate(filldate);
		mdVO.setCloseddate(closeddate);
		mdVO.setProg(prog);
		dao.update(mdVO);

		return mdVO;
	}

	public void deleteMotorDispatch(String mdno) {
		dao.delete(mdno);
	}

	public MotorDispatchVO findByMdno(String mdno) {
		return dao.findByPrimaryKey(mdno);
	}

	public List<MotorDispatchVO> getAll() {
		return dao.getAll();
	}

	public Set<MotorDispatchVO> getByLoc(String locno) {
		return dao.getMotorDispatchsByLoc(locno);
	}

	public Set<MotorDispatchVO> getByProg(String prog) {
		return dao.getMotorDispatchsByProg(prog);
	}
	
	public RentOrdVO checkDispatchableMotors(String motno){
		return dao.checkDispatchableMotors(motno);
	}

	//以下為Hibernate用
	public void insertByHib(MotorDispatchVO mdVO){
		dao.insertByHib(mdVO);
	}
	
	public void updateByHib(MotorDispatchVO mdVO){
		dao.updateByHib(mdVO);
	}

	public void deleteByHib(String mdno){
		dao.deleteByHib(mdno);
	}

	public MotorDispatchVO findByPkByHib(String mdno){
		return dao.findByPkByHib(mdno);
	}

	public List<MotorDispatchVO> getAllByHib(){
		return dao.getAllByHib();
	}

	Set<MotorDispListVO> getMdListByMdnoByHib(String mdno){
		return dao.getMdListByMdnoByHib(mdno);
	}
	
	public List<MotorDispatchVO> getByLocnoByHib(String locno){
		return dao.getByLocnoByHib(locno);
	}
	
	public void cancelByHib(String mdno){
		dao.cancelByHib(mdno);
	}
	
	public List<RentOrdVO> checkUndispatchableMotors(){
		return dao.checkUndispatchableMotors();
	}
}
