package com.motor_disp_list.model;

import com.motor_dispatch.model.MotorDispatchService;
import com.motor_dispatch.model.MotorDispatchVO;
import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;

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

}
