package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface RentOrdDAO_interface {
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
}
