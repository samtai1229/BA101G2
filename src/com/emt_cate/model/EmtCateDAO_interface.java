package com.emt_cate.model;

import java.util.List;
import java.util.Set;

import com.equipment.model.EquipmentVO;

public interface EmtCateDAO_interface {
	public void insert(EmtCateVO emtCateVO);

	public void update(EmtCateVO emtCateVO);

	public void delete(String ecno);

	public EmtCateVO findByPrimaryKey(String ecno);

	public List<EmtCateVO> getAll();

	public Set<EquipmentVO> getEmtsByEcno(String ecno);

	List<EmtCateVO> getAllByHib();

}
