package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.equipment.model.EquipmentVO;
import com.motor.model.MotorVO;

public interface RentOrdDAO_interface {
	
	public String differDateCalculator(String rentno);
	
	public void insert(RentOrdVO rental_orderVO);

	public void update(RentOrdVO rental_orderVO);

	public void delete(String rentno);

	public RentOrdVO findByPrimaryKey(String rentno);

	public List<RentOrdVO> getAll();

	public Set<RentOrdVO> getRentalOrdersByStatus(String status);

	public Set<RentOrdVO> getRentalOrdersByRentLoc(String slocno);

	public Set<RentOrdVO> getRentalOrdersByReturnLoc(String rlocno);

	public Set<RentOrdVO> getRentalOrdersByStartDatePrioid(Timestamp start_time, Timestamp end_time);

	public Set<RentOrdVO> getRentalOrdersByEndDatePrioid(Timestamp start_time, Timestamp end_time);
	
	public Set<RentOrdVO> getRentalOrdersForLeaseView(String slocno);
	
	public Set<RentOrdVO> getRentalOrdersForReturnView(String rlocno);
	
	public Set<EquipmentVO> getEquipmentVOsByRentno(String rentno);
	
	public List<String> getMotnoInRentOrdByRentalPeriod(Timestamp start_time, Timestamp end_time);
	
	public void updateEmtsStatusAfterAvailable(String emtno, String action);
	
	public void updateEmtsStatusAfterNoshow(String emtno, String action);
	
	public void updateRentOrdStatusAfterAvailable(String rentno, String note, String action);
	
	public void updateRentOrdStatusAfterNoshow(String rentno, String note, String action);
	
	public void updateMotorStatusAfterAvailable(String motno, String action);
	
	public void updateMotorStatusAfterNoshow(String motno, String action);
	
	public void updateRentOrdAfterNoreturn(String rentno, Integer milend, Timestamp returndate, 
			Integer fine, String rank, String note, String action);
	
	public void updateRentOrdAfterOvertime(String rentno, Integer milend, Timestamp returndate, 
			Integer fine, String rank, String note, String action);
	
	public void updateMotorAfterReturn(String motno, Integer mile, String rlocno, String action);
	
	public void updateEmtsAfterReturn(String emtno, String rlocno, String action);

}
