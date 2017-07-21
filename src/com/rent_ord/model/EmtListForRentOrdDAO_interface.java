package com.rent_ord.model;

import java.util.List;


public interface EmtListForRentOrdDAO_interface {
	 public void insert(EmtListForRentOrdVO emtListVO);
     public void delete(String rentno, String emtno);
     public List<EmtListForRentOrdVO> findByRentno(String rentno);
     public List<EmtListForRentOrdVO> findByEmtno(String emtno);
     public List<EmtListForRentOrdVO> getAll();
}