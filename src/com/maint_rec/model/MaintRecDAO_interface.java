package com.maint_rec.model;

import java.util.List;
import java.util.Set;

public interface MaintRecDAO_interface {
	public void insert(MaintRecVO mrVO);

	public void update(MaintRecVO mrVO);

	public void delete(String maintno);

	public MaintRecVO findByPrimaryKey(String maintno);

	public List<MaintRecVO> getAll();

	public Set<MaintRecVO> getMaintRecByMotor(String motno);

}
