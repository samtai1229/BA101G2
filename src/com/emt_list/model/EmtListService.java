package com.emt_list.model;

import java.util.List;

public class EmtListService {

	private EmtListDAO_interface dao;

	public EmtListService() {
		dao = new EmtListDAO();
	}

	public EmtListVO addEmtList(String rentno, String emtno) {

		EmtListVO emtListVO = new EmtListVO();

		emtListVO.setRentno(rentno);
		emtListVO.setEmtno(emtno);
		dao.insert(emtListVO);

		return emtListVO;
	}

	public EmtListVO updateEmtList(String rentno, String emtno) {

		EmtListVO emtListVO = new EmtListVO();

		emtListVO.setRentno(rentno);
		emtListVO.setEmtno(emtno);

		dao.update(emtListVO);

		return emtListVO;
	}

	public void deleteEmtList(String rentno, String emtno) {
		dao.delete(rentno, emtno);
	}

	public EmtListVO getOneEmtList(String rentno, String emtno) {
		return dao.findByPrimaryKey(rentno, emtno);
	}

	public List<EmtListVO> getAll() {
		return dao.getAll();
	}
}