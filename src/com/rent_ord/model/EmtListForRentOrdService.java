package com.rent_ord.model;

import java.util.List;

public class EmtListForRentOrdService {

	private EmtListForRentOrdDAO_interface dao;

	public EmtListForRentOrdService() {
		dao = new EmtListForRentOrdDAO();
	}

	public EmtListForRentOrdVO addEmtList(String rentno, String emtno) {

		EmtListForRentOrdVO emtListVO = new EmtListForRentOrdVO();
		
		emtListVO.setEmtVO(new EquipmentForRentOrdService().getOneEquipment(emtno));
		emtListVO.setRoVO(new RentOrdService().findByPK(rentno));
		dao.insert(emtListVO);
		return emtListVO;
	}

	public void deleteEmtList(String rentno, String emtno) {
		dao.delete(rentno, emtno);
	}
	
    public List<EmtListForRentOrdVO> findByRentno(String rentno){
    	return dao.findByRentno(rentno);
    }
    
    public List<EmtListForRentOrdVO> findByEmtno(String emtno){
    	return dao.findByEmtno(emtno);
    }
	
	public List<EmtListForRentOrdVO> getAll() {
		return dao.getAll();
	}
}