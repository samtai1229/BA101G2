package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.rent_ord.model.MotorForRentOrdVO;

public interface MotorForRentOrdDAO_interface {

	public List<MotorForRentOrdVO> getAll();
	public List<String> getMotnosByModelType(String modtype);
	public List<MotorForRentOrdVO> getMotorsByRentalSide();
	public Set<MotorForRentOrdVO> getMotorsByModelType(String modtype);
	public Set<MotorForRentOrdVO> getMotorsByLocNo(String locno);
	public Set<MotorForRentOrdVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time);
	public List<String> getMotnosByAllRentalStatus();
	public MotorForRentOrdVO findByPrimaryKey(String motno);
	
}
