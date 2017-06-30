package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.motor.model.MotorVO;

public interface MotorForRentOrdDAO_interface {

	public List<MotorVO> getAll();
	public Set<MotorVO> getMotorsByModelType(String modtype);
	public Set<MotorVO> getMotorsByLocNo(String locno);
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time);
	public List<String> getMotnosByAllRentalStatus();
	public MotorVO findByPrimaryKey(String motno);
	
}
