package com.emt_disp_list.model;

import java.util.List;

public class EmtDispListService {

	private EmtDispListDAO_interface dao;

	public EmtDispListService() {
		dao = new EmtDispListDAO();
	}

	public EmtDispListVO addEmtDispList(String edno, String emtno) {

		EmtDispListVO emtDispListVO = new EmtDispListVO();

		emtDispListVO.setEdno(edno);
		emtDispListVO.setEmtno(emtno);
		dao.insert(emtDispListVO);

		return emtDispListVO;
	}

	public EmtDispListVO updateEmtDispList(String edno, String emtno) {

		EmtDispListVO emtDispListVO = new EmtDispListVO();

		emtDispListVO.setEdno(edno);
		emtDispListVO.setEmtno(emtno);
		
		dao.update(emtDispListVO);

		return emtDispListVO;
	}

	public void deleteEmtDispList(String edno, String emtno) {
		dao.delete(edno, emtno);
	}

	public EmtDispListVO getOneEmtDispList(String edno, String emtno) {
		return dao.findByPrimaryKey(edno, emtno);
	}

	public List<EmtDispListVO> getAll() {
		return dao.getAll();
	}
}