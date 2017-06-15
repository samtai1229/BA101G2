package com.sec_ord.model;

import java.util.List;


public interface SecOrdDAO_interface {

	   public void insert(SecOrdVO secordVO);
       public void update(SecOrdVO secordVO);
       public void delete(String sono);
       public SecOrdVO findByPrimaryKey(String sono);
       public List<SecOrdVO> getAll();
       //萬用複合查詢(傳入參數型態Map)(回傳 List)
//     public List<EmpVO> getAll(Map<String, String[]> map); 
}
