package com.motor_model.model;

import java.util.List;

public interface MotorModelDAO_interface {
	public void insert(MotorModelVO mmVO);

	public void update(MotorModelVO mmVO);

	public void delete(String modtype);

	public MotorModelVO findByPrimaryKey(String modtype);

	public List<MotorModelVO> getAll();

}
