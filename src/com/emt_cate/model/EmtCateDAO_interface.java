package com.emt_cate.model;

import java.util.List;

public interface EmtCateDAO_interface {
	 public void insert(EmtCateVO emtCateVO);
     public void update(EmtCateVO emtCateVO);
     public void delete(String ecno);
     public EmtCateVO findByPrimaryKey(String ecno);
     public List<EmtCateVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}
