package com.emt_disp_list.model;
import java.util.List;

public interface EmtDispListDAO_interface {
	 public void insert(EmtDispListVO emtDispListVO);
     public void update(EmtDispListVO emtDispListVO);
     public void delete(String edno, String emtno);
     public EmtDispListVO findByPrimaryKey(String edno, String emtno);
     public List<EmtDispListVO> getAll();
     //萬用複合查詢(傳入參數型態Map)(回傳 List)
//   public List<EmpVO> getAll(Map<String, String[]> map); 
}