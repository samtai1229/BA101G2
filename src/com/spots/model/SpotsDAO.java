package com.spots.model;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SpotsDAO implements SpotsDAO_interface{

	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
	
	
	
	
	@Override
	public void insert(SpotsVO spotVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SpotsVO spotVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String spno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpotsVO findByPrimaryKey(String spno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpotsVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
