package com.emt_disp_list.model;
import java.util.List;


public interface EmtDispListDAO_interface {
	 public void insert(EmtDispListVO emtDispListVO);
     public void update(EmtDispListVO emtDispListVO);
     public void delete(String edno, String emtno);
     public EmtDispListVO findByPrimaryKey(String edno, String emtno);
     public List<EmtDispListVO> getAll();


   //以下為Hibernate用
 	void insertByHib(EmtDispListVO edListVO);

 	void updateByHib(EmtDispListVO edListVO);

 	public void deleteByHib(String edno);
}