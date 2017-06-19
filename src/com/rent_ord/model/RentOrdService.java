package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class RentOrdService {
	private RentOrdDAO_interface dao;

	public RentOrdService() {
		dao = new RentOrdDAO();
	}

	public RentOrdVO addRentOrd( String memno, String motno, String slocno, String rlocno,
		   Integer milstart, Timestamp startdate, Timestamp enddate,
		   String note) {

		RentOrdVO roVO = new RentOrdVO();
//		roVO.setRentno(rentno);
		roVO.setMemno(memno);
		roVO.setMotno(motno);
		roVO.setSlocno(slocno);
		roVO.setRlocno(rlocno);
		roVO.setMilstart(milstart);
//		roVO.setMilend(milend);
//		roVO.setFilldate(filldate);
		roVO.setStartdate(startdate);
		roVO.setEnddate(enddate);
//		roVO.setReturndate(returndate);
//		roVO.setFine(fine);
//		roVO.setTotal(total);
//		roVO.setRank(rank);
//		roVO.setStatus(status);
		roVO.setNote(note);
		dao.insert(roVO);

		return roVO;
	}

	public RentOrdVO updateRentOrd(String rentno,  String motno, String slocno, String rlocno,
			Integer milstart, Integer milend, Timestamp enddate, Timestamp startdate ,
			Timestamp returndate, Integer fine, Integer total, String rank, String status, String note) {

		RentOrdVO roVO = new RentOrdVO();
		roVO.setRentno(rentno);
		//roVO.setMemno(memno);
		roVO.setMotno(motno);
		roVO.setSlocno(slocno);
		roVO.setRlocno(rlocno);
		roVO.setMilstart(milstart);
		roVO.setMilend(milend);
		//roVO.setFilldate(filldate);
		roVO.setStartdate(startdate);
		roVO.setEnddate(enddate);
		roVO.setReturndate(returndate);
		roVO.setFine(fine);
		roVO.setTotal(total);
		roVO.setRank(rank);
		roVO.setStatus(status);
		roVO.setNote(note);
		dao.update(roVO);

		return roVO;
	}

	public void delete(String rentno) {
		dao.delete(rentno);
	}

	public RentOrdVO findByPK(String rentno) {
		return dao.findByPrimaryKey(rentno);
	}

	public List<RentOrdVO> getAll() {
		return dao.getAll();
	}

	public Set<RentOrdVO> getByStatus(String status) {
		return dao.getRentalOrdersByStatus(status);
	};

	public Set<RentOrdVO> getByRentLoc(String slocno) {
		return dao.getRentalOrdersByRentLoc(slocno);
	};

	public Set<RentOrdVO> getByReturnLoc(String rlocno) {
		return dao.getRentalOrdersByReturnLoc(rlocno);
	};

	public Set<RentOrdVO> getByStartDatePrioid(Timestamp start_time, Timestamp end_time) {
		return dao.getRentalOrdersByStartDatePrioid(start_time, end_time);
	};

	public Set<RentOrdVO> getByEndDatePrioid(Timestamp start_time, Timestamp end_time) {
		return dao.getRentalOrdersByEndDatePrioid(start_time, end_time);
	};
	
	public Set<RentOrdVO> getForLeaseView(String slocno){
		return dao.getRentalOrdersForLeaseView(slocno);
	};
	
	public Set<RentOrdVO> getForReturnView(String rlocno){
		return dao.getRentalOrdersForReturnView(rlocno);
	};
}
