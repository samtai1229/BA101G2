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
	
	public SecOrdVO addSecOrd(String memno, String motno, Timestamp sodate,String status)
	{
		SecOrdVO secordVO = new SecOrdVO();
		secordVO.setMemno(memno);
		secordVO.setMotorno(motno);
		secordVO.setBuildtime(sodate);
		secordVO.setStatus(status);
		dao.insert(secordVO);
		return secordVO;
	}
	public SecOrdVO addSecOrd(String memno, String motno)
	{
		SecOrdVO secordVO = new SecOrdVO();
		secordVO.setMemno(memno);
		secordVO.setMotorno(motno);
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
		secordVO.setMemno(memno);
		secordVO.setMotorno(motno);
		secordVO.setSono(sono);
		secordVO.setBuildtime(sodate);
		secordVO.setStatus(status);
		dao.update(secordVO);
		return secordVO;
	}
	
	public SecOrdVO getOneSecOrdBySono(String sono) {
		return dao.findBySono(sono);
	}
	public SecOrdVO getOneSecOrdByMemno(String memno) {
		return dao.findByMemno(memno);
	}

	public List<SecOrdVO> getAll() {
		return dao.getAll();
	}
	
	public List<SecOrdVO> getAll(String status) {
		return dao.getAll(status);
	}
	
	
	
	
}
