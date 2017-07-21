package com.rent_ord.model;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import com.rent_ord.model.MotorForRentOrdVO;

public class MotorForRentOrdService {
	private MotorForRentOrdDAO_interface dao;

	public MotorForRentOrdService() {
		dao = new MotorForRentOrdDAO();
	};
	
	public List<String> getMotnosByAllRentalStatus(){
		return dao.getMotnosByAllRentalStatus();
	};
	
	public List<String> getMotnosByModelType(String modtype){
		return dao.getMotnosByModelType(modtype);
	}
	
	public List<MotorForRentOrdVO> getMotorsByRentalSide(){
		return dao.getMotorsByRentalSide();
	}
	
	public MotorForRentOrdVO findByPK(String motno){
		return dao.findByPrimaryKey(motno);
	}
	
	public List<MotorForRentOrdVO> getAll(){
		return dao.getAll();
	}
	public Set<MotorForRentOrdVO> getMotorsByModelType(String modtype){
		return dao.getMotorsByModelType(modtype);
	}
	public Set<MotorForRentOrdVO> getMotorsByLocNo(String locno){
		return dao.getMotorsByLocNo(locno);
	}
	public Set<MotorForRentOrdVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time){
		return dao.getMotorsByManuDate(start_time, end_time);
	}
	
}
