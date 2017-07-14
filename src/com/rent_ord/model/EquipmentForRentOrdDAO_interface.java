package com.rent_ord.model;

import java.util.List;

import com.rent_ord.model.EquipmentVO;


public interface EquipmentForRentOrdDAO_interface {

    public EquipmentVO findByPrimaryKey(String emtno);
    public List<EquipmentVO> getAll();
    public List<EquipmentVO> getEquipsByEcno(String ecno);

}