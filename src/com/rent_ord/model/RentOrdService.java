package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.rent_ord.model.EquipmentVO;

public class RentOrdService {
	private RentOrdDAO_interface dao;

	public RentOrdService() {
		dao = new RentOrdDAO();
	}

	public RentOrdVO addRentOrd( String memno, String motno, String slocno, String rlocno,
		    Timestamp startdate, Timestamp enddate, Integer total, String status) {

//	    final String INSERT_STMT = "INSERT INTO RENT_ORD"
//		+ " (rentno, memno, motno, slocno, filldate, rlocno, startdate, enddate, total "
//		+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//		+ "  ?, ?, ?, ?)";		
		
		RentOrdVO roVO = new RentOrdVO();
//		roVO.setRentno(rentno);
		roVO.setMemno(memno);
		
		//roVO.setMotno(motno);
		MotorForRentOrdService mSvc = new MotorForRentOrdService();
		roVO.setMotorVO(mSvc.findByPK(motno));
		
		roVO.setSlocno(slocno);
		roVO.setRlocno(rlocno);
//		roVO.setMilstart(milstart);
//		roVO.setMilend(milend);
//		roVO.setFilldate(filldate);
		roVO.setStartdate(startdate);
		roVO.setEnddate(enddate);
//		roVO.setReturndate(returndate);
//		roVO.setFine(fine);
		roVO.setTotal(total);
//		roVO.setRank(rank);
		roVO.setStatus(status);
//		roVO.setNote(note);
		dao.insert(roVO);

		return roVO;
	}

	
	public RentOrdVO updateRentOrd(String rentno,  String motno, String slocno, String rlocno, 
			String rank, String status, String note, Timestamp startdate , Timestamp enddate,
			Timestamp returndate,Integer milstart, Integer milend, Integer fine, Integer total) {
		
		System.out.println("Service updateRentOrd in");

		RentOrdVO roVO = new RentOrdVO();
		roVO.setRentno(rentno);
		//roVO.setMemno(memno);

		//roVO.setMotno(motno);
		MotorForRentOrdService mSvc = new MotorForRentOrdService();
		roVO.setMotorVO(mSvc.findByPK(motno));
		
		roVO.setSlocno(slocno);
		roVO.setRlocno(rlocno);
		roVO.setMilstart(milstart);
		roVO.setMilend(milend);
		//roVO.setFilldate(filldate);
		roVO.setStartdate(startdate);
		roVO.setEnddate(enddate);
		roVO.setReturndate(returndate);
		
		System.out.println("returndate: (service)" + returndate);
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
	public Set<RentOrdVO> getBymotno(String motno){
		return dao.getRentalOrdersBymotno(motno);
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
	
	public Set<EquipmentVO> getEquipmentVOsByRentno(String rentno){
		return dao.getEquipmentVOsByRentno(rentno);
	}
	
	public void updateEmtsStatusAfterNoshow(String emtno, String action){
		dao.updateEmtsStatusAfterNoshow(emtno, action);
	};
	
	public void updateEmtsStatusAfterAvailable(String emtno, String action){
		dao.updateEmtsStatusAfterAvailable(emtno, action);
	};
	
	public void updateRentOrdStatusAfterNoshow(String rentno, String note, String action){
		dao.updateRentOrdStatusAfterNoshow(rentno, note, action);
	};
	
	public void updateRentOrdStatusAfterAvailable(String rentno, String note, String action){
		dao.updateRentOrdStatusAfterAvailable(rentno, note, action);
	};	
	
	public void updateMotorStatusAfterAvailable(String motno, String action){
		dao.updateMotorStatusAfterAvailable(motno, action);
	};
	
	public void updateMotorStatusAfterNoshow(String motno, String action){
		dao.updateMotorStatusAfterNoshow(motno, action);
	};	
	
	
	public void updateRentOrdAfterNoreturn(String rentno, Integer milend, Timestamp returndate, 
			Integer fine, String rank, String note, String action){
		dao.updateRentOrdAfterNoreturn(rentno, milend, returndate, fine, rank, note, action);
	};
	
	
	public void updateRentOrdAfterOvertime(String rentno, Integer milend, Timestamp returndate, 
			Integer fine, String rank, String note, String action){
		dao.updateRentOrdAfterOvertime(rentno, milend, returndate, fine, rank, note, action);
	};	
	
	public void updateMotorAfterReturn(String motno, Integer mile,  String rlocno, String action) {
		dao.updateMotorAfterReturn(motno, mile, rlocno, action);
	}
	
	public void updateEmtsAfterReturn(String emtno, String rlocno, String action) {
		dao.updateEmtsAfterReturn(emtno, rlocno, action);
	}
	
	public String differDateCalculator(String rentno){
		return dao.differDateCalculator(rentno);
	};
	
	public List<MotorVO> getMotnoInRentOrdByRentalPeriod(Timestamp start_time, Timestamp end_time){
		return dao.getMotnoInRentOrdByRentalPeriod(start_time, end_time);
	}
	
	public List<String> getRentnoByRentalPeriod(Timestamp start_time, Timestamp end_time){
		return dao.getRentnoByRentalPeriod(start_time, end_time);
	}
	
	public String getRentnoByMemnoAndStartdate(String memno, Timestamp start_time){
		return dao.getRentnoByMemnoAndStartdate(memno, start_time);
	}
	
	public List<EquipmentVO> getEmtnoByRentno(String rentno){
		return dao.getEmtnoByRentno(rentno);
	};
	
	public Set<RentOrdVO> getRoVOsByDatePrioidAndMotno(Timestamp start_time, Timestamp end_time, String motno){
		return dao.getRoVOsByDatePrioidAndMotno(start_time, end_time, motno);
	};
	
	public void updateStatusByRentno(String status, String rentno){
		dao.updateStatusByRentno(status, rentno);
	};
}
