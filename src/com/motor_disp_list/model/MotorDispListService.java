package com.motor_disp_list.model;

import java.util.List;

public class MotorDispListService {
	
	private MotorDispListDAO_interface dao;
	
	public MotorDispListService(){
		dao = new MotorDispListDAO();
	}
	
	public MotorDispListVO findByDispatchNo(String mdno) {
		return dao.findByPrimaryKeyDispatchNo(mdno);
	}

	public MotorDispListVO findByMotorNo(String motno) {
		return dao.findByPrimaryKeyMotorNo(motno);
	}
	
	//以下hibernate
	public void deleteByHib(String mdno){
		dao.deleteByHib(mdno);
	}
	
	public List<MotorDispListVO> getMotnosByMdnoByHib(String mdno){
		return dao.getMotnosByMdnoByHib(mdno);
	}

}
