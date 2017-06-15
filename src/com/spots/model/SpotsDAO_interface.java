package com.spots.model;

import java.util.List;

public interface SpotsDAO_interface {
	public void insert(SpotsVO spotVO) ;
	public void update(SpotsVO spotVO);
	public void delete(String spno);
	public SpotsVO findByPrimaryKey(String spno);
	public List<SpotsVO> getAll() ;
}
