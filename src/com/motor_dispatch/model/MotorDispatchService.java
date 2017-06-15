package com.motor_dispatch.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class MotorDispatchService {

	private MotorDispatchDAO_interface dao;

	public MotorDispatchService() {
		dao = new MotorDispatchDAO();
	}

	public MotorDispatchVO addMotorDispatch(String locno, Timestamp filldate, Timestamp closeddate, String prog) {
		MotorDispatchVO mdVO = new MotorDispatchVO();
		mdVO.setLocno(locno);
		mdVO.setFilldate(filldate);
		mdVO.setCloseddate(closeddate);
		mdVO.setProg(prog);
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

}
