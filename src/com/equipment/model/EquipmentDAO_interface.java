package com.equipment.model;

import java.util.List;


public interface EquipmentDAO_interface {
	public void insert(EquipmentVO equipmentVO);
    public void update(EquipmentVO equipmentVO);
    public void delete(String emtno);
    public EquipmentVO findByPrimaryKey(String emtno);
    public List<EquipmentVO> getAll();
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 
}