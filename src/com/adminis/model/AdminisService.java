package com.adminis.model;

import java.util.List;

public class AdminisService {
	
	private AdminisDAO_interface dao;
	
	public AdminisService(){
		dao = new AdminisDAO();
	}
	public AdminisVO addAdminis(String admno,String locno,String authno,String name,String acc,String pw){
		AdminisVO	adminisVO = new AdminisVO();
		adminisVO.setAdmno(admno);
		adminisVO.setLocno(locno);
		adminisVO.setAuthno(authno);
		adminisVO.setName(name);
		adminisVO.setAcc(acc);
		adminisVO.setPw(pw);
		dao.insert(adminisVO);
		
		return adminisVO;
	}
	
	public AdminisVO updateAdminis(String admno,String locno,String authno,String name,String acc,String pw){
		AdminisVO	adminisVO = new AdminisVO();
		adminisVO.setAdmno(admno);
		adminisVO.setLocno(locno);
		adminisVO.setAuthno(authno);
		adminisVO.setName(name);
		adminisVO.setAcc(acc);
		adminisVO.setPw(pw);
		dao.insert(adminisVO);
		
		return adminisVO;
	}
	public void deleteEmp(String admno) {
		dao.delete(admno);
	}

	public AdminisVO getOneEmp(String admno) {
		return dao.findByPrimaryKey(admno);
	}

	public List<AdminisVO> getAll() {
		return dao.getAll();
	}

}
