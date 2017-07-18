package com.motor_dispatch.model;

import java.util.List;
import java.util.Set;

import com.motor.model.MotorVO;
import com.motor_disp_list.model.MotorDispListVO;
import com.rent_ord.model.RentOrdVO;

public interface MotorDispatchDAO_interface {
	public void insert(MotorDispatchVO mdVO);

	public void update(MotorDispatchVO mdVO);

	public void delete(String mdno);

	public MotorDispatchVO findByPrimaryKey(String mdno);

	public List<MotorDispatchVO> getAll();

	public Set<MotorDispatchVO> getMotorDispatchsByLoc(String locno);

	public Set<MotorDispatchVO> getMotorDispatchsByProg(String prog);

	public RentOrdVO checkDispatchableMotors(String motno);

	//以下為Hibernate用
	public void insertByHib(MotorDispatchVO mdVO);

	public void updateByHib(MotorDispatchVO mdVO);

	public void deleteByHib(String mdno);

	public MotorDispatchVO findByPkByHib(String mdno);

	public List<MotorDispatchVO> getAllByHib();

	Set<MotorDispListVO> getMdListByMdnoByHib(String mdno);

	public List<MotorDispatchVO> getByLocnoByHib(String locno);
}
