package com.sec_ord.model;

import java.sql.Timestamp;
import java.util.List;

import com.sec_ord.model.SecOrdVO;

public class SecOrdService {

	SecOrdDAO_interface dao ;
	
	public SecOrdService()
	{
		dao = new SecOrdDAO();
	}
	
	public SecOrdVO addSecOrd(String sono,String memno, String motno, Timestamp sodate,String status)
	{
		SecOrdVO secordVO = new SecOrdVO();
		secordVO.setMemNo(memno);
		secordVO.setMotorNo(motno);
		secordVO.setSecondNo(sono);
		secordVO.setSecondOrderDate(sodate);
		secordVO.setSecondStatus(status);
		dao.insert(secordVO);
		return secordVO;
	}
	
	public void deleteSecOrd(String sono)
	{
		dao.delete(sono);
	}
	
	public SecOrdVO updateSecOrd(String memno,String motno,Timestamp sodate,String status,String sono )
	{

		SecOrdVO secordVO = new SecOrdVO();
		secordVO.setMemNo(memno);
		secordVO.setMotorNo(motno);
		secordVO.setSecondNo(sono);
		secordVO.setSecondOrderDate(sodate);
		secordVO.setSecondStatus(status);
		dao.update(secordVO);
		return secordVO;
	}
	
	public SecOrdVO getOneSecOrd(String sono) {
		return dao.findByPrimaryKey(sono);
	}

	public List<SecOrdVO> getAll() {
		return dao.getAll();
	}
	
	
	
	
	
	
}
