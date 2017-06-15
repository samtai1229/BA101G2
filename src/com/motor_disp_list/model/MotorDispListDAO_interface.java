package com.motor_disp_list.model;

public interface MotorDispListDAO_interface {

	public MotorDispListVO findByPrimaryKeyDispatchNo(String mdno);

	public MotorDispListVO findByPrimaryKeyMotorNo(String motno);

}
