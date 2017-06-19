package com.adminis.model;

import java.util.List;

public interface AdminisDAO_interface {
	public void insert(AdminisVO adminisvo);
	public void update(AdminisVO adminisvo);
	public void delete(String admno);
	public AdminisVO findByPrimaryKey(String admno);
	public List<AdminisVO> getAll();
}
