package com.emt_list.model;

import java.util.List;


public interface EmtListDAO_interface {
	 public void insert(EmtListVO emtListVO);
     public void update(EmtListVO emtListVO);
     public void delete(String rentno, String emtno);
     public EmtListVO findByPrimaryKey(String rentno, String emtno);
     public List<EmtListVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}