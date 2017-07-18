package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.rent_ord.model.MotorVO;

public interface MotorForRentOrdDAO_interface {

	public List<MotorVO> getAll();
	public List<String> getMotnosByModelType(String modtype);
	public List<MotorVO> getMotorsByRentalSide();
	public Set<MotorVO> getMotorsByModelType(String modtype);
	public Set<MotorVO> getMotorsByLocNo(String locno);
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time);
	public List<String> getMotnosByAllRentalStatus();
	public MotorVO findByPrimaryKey(String motno);
	
}
