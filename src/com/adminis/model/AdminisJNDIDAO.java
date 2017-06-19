package com.adminis.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdminisJNDIDAO implements AdminisDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO adminis(admno,locno,authno,name,acc,pw) VALUES(admno_seq.NEXTVAL,?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT ="SELECT admno,locno,authno,name,acc,pw FROM adminis order by admno";
	private static final String GET_ONE_STMT ="SELECT admno,locno,authno,name,acc,pw FROM adminis where admno = ?";
	private static final String DELETE ="DELETE FROM adminis where admno = ?";
	private static final String UPDATE ="UPDATE adminis set locno=?, authno=?, name=?, acc=?, pw=? where admno = ?";
	
	@Override
	public void insert(AdminisVO adminisvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, adminisvo.getLocno());
			pstmt.setString(2, adminisvo.getAuthno());
			pstmt.setString(3, adminisvo.getName());
			pstmt.setString(4, adminisvo.getAcc());
			pstmt.setString(5, adminisvo.getPw());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
		
	@Override
	public void update(AdminisVO adminisvo) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(String admno) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public AdminisVO findByPrimaryKey(String admno) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<AdminisVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
