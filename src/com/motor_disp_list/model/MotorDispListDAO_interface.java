package com.motor_disp_list.model;

import com.motor_model.model.MotorModelVO;

public interface MotorDispListDAO_interface {

	public MotorDispListVO findByPrimaryKeyDispatchNo(String mdno);

	public MotorDispListVO findByPrimaryKeyMotorNo(String motno);

	//以下為Hibernate用
	void insertByHib(MotorDispListVO mdListVO);

	void updateByHib(MotorDispListVO mdListVO);



}
