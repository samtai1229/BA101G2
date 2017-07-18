package com.maint_rec.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class MaintRecService {
	private MaintRecDAO_interface dao;

	MaintRecService() {
		dao = new MaintRecDAO();
	}

	public MaintRecVO addMaintRec(String motno, Timestamp startdate, Timestamp enddate, String cont) {
		MaintRecVO mrVO = new MaintRecVO();
		mrVO.setMotno(motno);
		mrVO.setStartdate(startdate);
		mrVO.setEnddate(enddate);
		mrVO.setCont(cont);
		dao.insert(mrVO);

		return mrVO;

	}

	public MaintRecVO updateMaintRec(String maintno, String motno, Timestamp startdate, Timestamp enddate,
			String cont) {
		MaintRecVO mrVO = new MaintRecVO();
		mrVO.setMaintno(maintno);
		mrVO.setMotno(motno);
		mrVO.setStartdate(startdate);
		mrVO.setEnddate(enddate);
		mrVO.setCont(cont);
		dao.update(mrVO);

		return mrVO;

	}

	public void deleteMaintRec(String maintno) {
		dao.delete(maintno);
	}

	public MaintRecVO findByPK(String maintno) {
		return dao.findByPrimaryKey(maintno);
	}

	public List<MaintRecVO> getAll() {
		return dao.getAll();
	}

	public Set<MaintRecVO> getByMotor(String motno) {
		return dao.getMaintRecByMotor(motno);
	}

}
