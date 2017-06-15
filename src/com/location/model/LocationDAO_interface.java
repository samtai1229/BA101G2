package com.location.model;

import java.util.List;


public interface LocationDAO_interface {
	public void insert(LocationVO locationDAO);
    public void update(LocationVO locationDAO);
    public void delete(String locno);
    public LocationVO findByPrimaryKey(String locno);
    public List<LocationVO> getAll();
}
