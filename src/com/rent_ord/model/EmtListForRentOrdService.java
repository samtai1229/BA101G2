package com.rent_ord.model;

import java.util.List;

public class EmtListForRentOrdService {

	private EmtListForRentOrdDAO_interface dao;

	public EmtListForRentOrdService() {
		dao = new EmtListForRentOrdDAO();
	}

	public EmtListVO addEmtList(String rentno, String emtno) {

		EmtListVO emtListVO = new EmtListVO();
		
		emtListVO.setEmtVO(new EquipmentForRentOrdService().getOneEquipment(emtno));
		emtListVO.setRoVO(new RentOrdService().findByPK(rentno));
		dao.insert(emtListVO);
		return emtListVO;
	}

	public void deleteEmtList(String rentno, String emtno) {
		dao.delete(rentno, emtno);
	}
	
    public List<EmtListVO> findByRentno(String rentno){
    	return dao.findByRentno(rentno);
    }
    
    public List<EmtListVO> findByEmtno(String emtno){
    	return dao.findByEmtno(emtno);
    }
	
	public List<EmtListVO> getAll() {
		return dao.getAll();
	}
}