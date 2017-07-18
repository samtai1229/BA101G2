package com.emt_dispatch.model;

import java.util.List;


public interface EmtDispatchDAO_interface {
	 public void insert(EmtDispatchVO emtDispatchVO);
     public void update(EmtDispatchVO emtDispatchVO);
     public void delete(String edno);
     public EmtDispatchVO findByPrimaryKey(String edno);
     public List<EmtDispatchVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}