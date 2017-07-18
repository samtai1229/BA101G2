package com.spots.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
	private static final String INSERT_STMT = "INSERT INTO SPOTS"
			+ " (SPNO, SPNAME, LOCNO, SPLONG, SPLAT"+")"+" VALUES ('SP'||LPAD(TO_CHAR(spno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?"
			+ ")";

	private static final String UPDATE = "UPDATE SPOTS set SPNAME=?,"
			+ " LOCNO=?, SPLONG=?, SPLAT=? where SPNO = ?";

	private static final String DELETE = "DELETE FROM SPOTS where SPNO = ?";

	private static final String GET_ONE = 
		 "SELECT SPNO, SPNAME, LOCNO, SPLONG, SPLAT FROM Spots where spno = ?";
	private static final String GET_ALL = 
			"SELECT SPNO, SPNAME, LOCNO, SPLONG, SPLAT FROM SPOTS";
	
	
	@Override
	public void insert(SpotsVO spotVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
        
		try {
             con = ds.getConnection();			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, spotVO.getSpname());
			pstmt.setString(2, spotVO.getLocno());
			pstmt.setFloat(4, spotVO.getSplat());
			pstmt.setFloat(3, spotVO.getSplong());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(SpotsVO spotVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spotVO.getSpname());
			pstmt.setString(2, spotVO.getLocno());
			pstmt.setFloat(4, spotVO.getSplat());
			pstmt.setFloat(3, spotVO.getSplong());
			pstmt.setString(5, spotVO.getSpno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String spno) {


		Connection con = null;
		PreparedStatement pstmt = null;

		try {

		con = ds.getConnection();
			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, spno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		public SpotsVO findByPrimaryKey(String spno) {

			SpotsVO spVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE);

				pstmt.setString(1, spno);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// 也稱為 Domain objects
					spVO = new SpotsVO();
					spVO.setSpno(rs.getString("spno"));
					spVO.setLocno(rs.getString("locno"));
					spVO.setSplat(rs.getFloat("splat"));
					spVO.setSplong(rs.getFloat("splong"));
					spVO.setSpname(rs.getString("spname"));

				}
				

				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
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
			return spVO;
	}

	@Override
	public List<SpotsVO> getAll() {
		List<SpotsVO> list = new ArrayList<SpotsVO>();
		SpotsVO spVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				spVO = new SpotsVO();
				spVO.setSpno(rs.getString("spno"));
				spVO.setLocno(rs.getString("locno"));
				spVO.setSplat(rs.getFloat("splat"));
				spVO.setSplong(rs.getFloat("splong"));
				spVO.setSpname(rs.getString("spname"));
				list.add(spVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
