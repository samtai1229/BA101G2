package com.auth_cate.model;

import java.util.List;

import com.adminis.model.AdminisVO;

public class AuthCateService {
	private AuthCateDAO_interface dao;
	
	public AuthCateService(){
		dao = new AuthCateDAO();
	}
	public AuthCateVO addAuth_cate(String descr){
		AuthCateVO	authcateVO = new AuthCateVO();
		authcateVO.setDescr(descr);
		dao.insert(authcateVO);
		
		return authcateVO;
	}
	public AuthCateVO updateAuth_cate(String authno,String descr){
		AuthCateVO	authcateVO = new AuthCateVO();
		authcateVO.setAuthno(authno);
		authcateVO.setDescr(descr);
		dao.update(authcateVO);
		
		return authcateVO;
	}
	public void deleteAuth_cate(String authno) {
		dao.delete(authno);
	}
	
	public List<AuthCateVO> getAll() {
		return dao.getAll();
	}

}
