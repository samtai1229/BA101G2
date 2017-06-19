package com.emt_dispatch.model;
import java.sql.Timestamp;
import java.util.List;

public class EmtDispatchService {
	private EmtDispatchDAO_interface dao;

	public EmtDispatchService() {
		dao = new EmtDispatchDAO();
	}

	public EmtDispatchVO addEmtDispatch(String edno, String locno, Timestamp demanddate, Timestamp closeddate,String prog) {

		EmtDispatchVO emtDispatchVO = new EmtDispatchVO();

		emtDispatchVO.setEdno(edno);
		emtDispatchVO.setLocno(locno);
		emtDispatchVO.setDemanddate(demanddate);
		emtDispatchVO.setCloseddate(closeddate);
		emtDispatchVO.setProg(prog);
		
		dao.insert(emtDispatchVO);

		return emtDispatchVO;
	}

	public EmtDispatchVO updateEmtDispatch(String edno, String locno, Timestamp demanddate, Timestamp closeddate,String prog) {

		EmtDispatchVO emtDispatchVO = new EmtDispatchVO();

		emtDispatchVO.setEdno(edno);
		emtDispatchVO.setLocno(locno);
		emtDispatchVO.setDemanddate(demanddate);
		emtDispatchVO.setCloseddate(closeddate);
		emtDispatchVO.setProg(prog);
		
		dao.update(emtDispatchVO);

		return emtDispatchVO;
	}

	public void deleteEmtDispatch(String edno) {
		dao.delete(edno);
	}

	public EmtDispatchVO getOneEmtDispatch(String edno) {
		return dao.findByPrimaryKey(edno);
	}

	public List<EmtDispatchVO> getAll() {
		return dao.getAll();
	}
}