package com.motor_disp_list.model;


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

}
