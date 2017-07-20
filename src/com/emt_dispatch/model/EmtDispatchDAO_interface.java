package com.emt_dispatch.model;

import java.util.List;
import java.util.Set;

import com.emt_disp_list.model.EmtDispListVO;



public interface EmtDispatchDAO_interface {
	 public void insert(EmtDispatchVO emtDispatchVO);
     public void update(EmtDispatchVO emtDispatchVO);
     public void delete(String edno);
     public EmtDispatchVO findByPrimaryKey(String edno);
     public List<EmtDispatchVO> getAll();

   //以下為Hibernate用
 	public void insertByHib(EmtDispatchVO edVO);

 	public void updateByHib(EmtDispatchVO edVO);

 	public void deleteByHib(String edno);

 	public EmtDispatchVO findByPkByHib(String edno);

 	public List<EmtDispatchVO> getAllByHib();

 	Set<EmtDispListVO> getEdListByEdnoByHib(String edno);

 	public List<EmtDispatchVO> getByLocnoByHib(String locno);

 	public void cancelByHib(String edno);
}