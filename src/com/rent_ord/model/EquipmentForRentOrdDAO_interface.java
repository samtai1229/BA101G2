package com.rent_ord.model;

import java.util.List;

import com.equipment.model.EquipmentVO;


public interface EquipmentForRentOrdDAO_interface {

    public EquipmentVO findByPrimaryKey(String emtno);
    public List<EquipmentVO> getAll();
    public List<EquipmentVO> getLeasableEquipsByEcno(String ecno);


}