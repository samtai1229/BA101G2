package com.auth_cate.model;

import java.util.List;

import com.auth_cate.model.AuthCateVO;

	public interface AuthCateDAO_interface {
	public void insert(AuthCateVO authcatevo);
	public void update(AuthCateVO authcatevo);
	public void delete(String authno);
	public AuthCateVO findByPrimaryKey(String authno);
	public List<AuthCateVO> getAll();
	

}
