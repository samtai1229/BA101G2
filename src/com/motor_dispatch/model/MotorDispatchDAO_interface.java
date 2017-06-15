package com.motor_dispatch.model;

import java.util.List;
import java.util.Set;

public interface MotorDispatchDAO_interface {
	public void insert(MotorDispatchVO mdVO);

	public void update(MotorDispatchVO mdVO);

	public void delete(String mdno);

	public MotorDispatchVO findByPrimaryKey(String mdno);

	public List<MotorDispatchVO> getAll();

	public Set<MotorDispatchVO> getMotorDispatchsByLoc(String locno);

	public Set<MotorDispatchVO> getMotorDispatchsByProg(String prog);

}
