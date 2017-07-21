package com.rent_ord.model;

import java.util.List;

import com.rent_ord.model.EquipmentForRentOrdVO;


public interface EquipmentForRentOrdDAO_interface {
    public EquipmentForRentOrdVO findByPrimaryKey(String emtno);
    public List<EquipmentForRentOrdVO> getAll();
    public List<EquipmentForRentOrdVO> getEquipsByEcno(String ecno);
}