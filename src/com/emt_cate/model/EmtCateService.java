package com.emt_cate.model;

import java.util.List;

public class EmtCateService {
	private EmtCateDAO_interface dao;

	public EmtCateService() {
		dao = new EmtCateDAO();
	}

	public EmtCateVO addEmtCate(String ecno, String type, byte[] pic, Integer price) {

		EmtCateVO emtCateVO = new EmtCateVO();

		emtCateVO.setEcno(ecno);
		emtCateVO.setType(type);
		emtCateVO.setPic(pic);
		emtCateVO.setPrice(price);
		
		dao.insert(emtCateVO);

		return emtCateVO;
	}

	public EmtCateVO updateEmtCate(String ecno, String type, byte[] pic, Integer price) {

		EmtCateVO emtCateVO = new EmtCateVO();

		emtCateVO.setEcno(ecno);
		emtCateVO.setType(type);
		emtCateVO.setPic(pic);
		emtCateVO.setPrice(price);
		
		dao.update(emtCateVO);

		return emtCateVO;
	}

	public void deleteEmtCate(String ecno) {
		dao.delete(ecno);
	}

	public EmtCateVO getOneEmtCate(String ecno) {
		return dao.findByPrimaryKey(ecno);
	}

	public List<EmtCateVO> getAll() {
		return dao.getAll();
	}
}