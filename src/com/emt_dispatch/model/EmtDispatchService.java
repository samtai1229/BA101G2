package com.emt_dispatch.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.emt_disp_list.model.EmtDispListVO;


public class EmtDispatchService {
	private EmtDispatchDAO_interface dao;

	public EmtDispatchService() {
		dao = new EmtDispatchDAO();
	}

	public EmtDispatchVO addEmtDispatch(String locno) {

		EmtDispatchVO emtDispatchVO = new EmtDispatchVO();

		emtDispatchVO.setLocno(locno);
		dao.insert(emtDispatchVO);
		return emtDispatchVO;
	}

	public EmtDispatchVO updateEmtDispatch(String edno, String locno, Timestamp demanddate, Timestamp closeddate,
			String prog) {

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

	// 以下為Hibernate用
	public void insertByHib(EmtDispatchVO edVO) {
		dao.insertByHib(edVO);
	}

	public void updateByHib(EmtDispatchVO edVO) {
		dao.updateByHib(edVO);
	}

	public void deleteByHib(String edno) {
		dao.deleteByHib(edno);
	}

	public EmtDispatchVO findByPkByHib(String edno) {
		return dao.findByPkByHib(edno);
	}

	public List<EmtDispatchVO> getAllByHib() {
		return dao.getAllByHib();
	}

	Set<EmtDispListVO> getEdListByEdnoByHib(String edno) {
		return dao.getEdListByEdnoByHib(edno);
	}

	public List<EmtDispatchVO> getByLocnoByHib(String locno) {
		return dao.getByLocnoByHib(locno);
	}

	public void cancelByHib(String edno) {
		dao.cancelByHib(edno);
	}
}