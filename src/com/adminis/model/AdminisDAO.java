package com.adminis.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdminisDAO implements AdminisDAO_interface{
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
	public void insert(AdminisVO adminstratorVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, adminstratorVO.getLocno());
			pstmt.setString(2, adminstratorVO.getAuthno());
			pstmt.setString(3, adminstratorVO.getName());
			pstmt.setString(4, adminstratorVO.getAcc());
			pstmt.setString(5, adminstratorVO.getPw());
			
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
	public void update(AdminisVO adminstratorVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, adminstratorVO.getLocno());
			pstmt.setString(2, adminstratorVO.getAuthno());
			pstmt.setString(3, adminstratorVO.getName());
			pstmt.setString(4, adminstratorVO.getAcc());
			pstmt.setString(5, adminstratorVO.getPw());
			pstmt.setString(6, adminstratorVO.getAdmno());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			// Clean up JDBC resources
		} finally {
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
	public void delete(String admno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, admno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			// Clean up JDBC resources
		} finally {
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
	public AdminisVO findByPrimaryKey(String admno) {
		AdminisVO adminisVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, admno);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				adminisVO = new AdminisVO();
				adminisVO.setAdmno(rs.getString("admno"));
				adminisVO.setLocno(rs.getString("locno"));
				adminisVO.setAuthno(rs.getString("authno"));
				adminisVO.setName(rs.getString("name"));
				adminisVO.setAcc(rs.getString("acc"));
				adminisVO.setPw(rs.getString("pw"));
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return adminisVO;
	}
	@Override
	public List<AdminisVO> getAll() {
		List<AdminisVO> list = new ArrayList<AdminisVO>();
		AdminisVO adminstratorVO =null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				adminstratorVO = new AdminisVO();
				adminstratorVO.setAdmno(rs.getString("admno"));
				adminstratorVO.setLocno(rs.getString("locno"));
				adminstratorVO.setAuthno(rs.getString("authno"));
				adminstratorVO.setName(rs.getString("name"));
				adminstratorVO.setAcc(rs.getString("acc"));
				adminstratorVO.setPw(rs.getString("pw"));
				list.add(adminstratorVO);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return list;
		
	}
	
	
}
