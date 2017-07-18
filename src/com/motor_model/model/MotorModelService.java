package com.motor_model.model;

import java.util.List;

public class MotorModelService {

	private MotorModelDAO_interface dao;

	public MotorModelService() {
		dao = new MotorModelDAO();
	}

	public MotorModelVO addMotorModel(String brand, Integer displacement, String name, Integer renprice,
			Integer saleprice, byte[] motpic, String intro) {

		MotorModelVO mmVO = new MotorModelVO();
		mmVO.setBrand(brand);
		mmVO.setDisplacement(displacement);
		mmVO.setName(name);
		mmVO.setRenprice(renprice);
		mmVO.setSaleprice(saleprice);
		mmVO.setMotpic(motpic);
		mmVO.setIntro(intro);

		dao.insert(mmVO);

		return mmVO;

	};


	public MotorModelVO updateMotorModel(String modtype, String brand, Integer displacement, String name,
			Integer renprice, Integer saleprice, byte[] motpic, String intro) {

		MotorModelVO mmVO = new MotorModelVO();
		mmVO.setModtype(modtype);
		mmVO.setBrand(brand);
		mmVO.setDisplacement(displacement);
		mmVO.setName(name);
		mmVO.setRenprice(renprice);
		mmVO.setSaleprice(saleprice);
		mmVO.setMotpic(motpic);
		mmVO.setIntro(intro);

		dao.update(mmVO);

		return mmVO;
	}

	public void deleteMotorModel(String modtype) {
		dao.delete(modtype);
	}

	public MotorModelVO findByPK(String modtype) {
		return dao.findByPrimaryKey(modtype);
	}

	public List<MotorModelVO> getAll() {
		return dao.getAll();
	}

	public List<MotorModelVO> fuzzyGetAll(String fuzzyValue) {
		return dao.fuzzyGetAll(fuzzyValue);
	}
	
	public List<MotorModelVO> fuzzySearchByHib(String fuzzyValue){
		return dao.fuzzySearchByHib(fuzzyValue);
	};
	
	public List<MotorModelVO> getAllByHib(){
		return dao.getAllByHib();
	};
}
