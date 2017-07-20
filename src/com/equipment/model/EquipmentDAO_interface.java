package com.equipment.model;

import java.util.List;




public interface EquipmentDAO_interface {
	public void insert(EquipmentVO equipmentVO);
    public void update(EquipmentVO equipmentVO);
    public void delete(String emtno);
    public EquipmentVO findByPrimaryKey(String emtno);
    public List<EquipmentVO> getAll();


  //以下為Hibernate用
  	public void insertByHib(EquipmentVO equipmentVO);

  	public void updateByHib(EquipmentVO equipmentVO);

  	public void deleteByHib(String emtno);

  	public EquipmentVO findByPkByHib(String emtno);

  	public List<EquipmentVO> getAllByHib();
  	
  	public List<EquipmentVO> getEmtsByEcnoByHib(String ecno);

  	public void updateStatusByHib(String emtno, String status);
  	
	public List<String> getEcnoByLocnoByHib(String locno);
	
	public List<EquipmentVO> getEmtsByEcnoAndLocnoByHib(String ecno, String locno);
}