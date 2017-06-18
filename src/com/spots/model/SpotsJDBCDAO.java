package com.spots.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SpotsJDBCDAO implements SpotsDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO SPOTS"
			+ " (SPNO, SPNAME, LOCNO, SPLONG, SPLAT"+")"+" VALUES ('S'||LPAD(TO_CHAR(spno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?"
			+ ")";

	private static final String UPDATE = "UPDATE SPOTS set SPNAME=?,"
			+ " LOCNO=?, SPLONG=?, SPLAT=? where SPNO = ?";

	private static final String DELETE = "DELETE FROM SPOTS where SPNO = ?";

	private static final String GET_ONE = 
		 "SELECT SPNO, SPNAME, LOCNO, SPLONG, SPLAT FROM Spots where spno = ?";
	private static final String GET_ALL = 
			"SELECT SPNO, SPNAME, LOCNO, SPLONG, SPLAT FROM SPOTS";
	
	
	public static void main(String[] args) {
         SpotsJDBCDAO dao = new SpotsJDBCDAO();
         
        
        //新增 
//        for(int i=1; i<=10;i++)
//        {
//        	 SpotsVO spVO1 = new SpotsVO();
//    		  
//     		 spVO1.setSpname("景點"+i);
//     		 int k=0;
//     		 if(i>=6)
//     		 {
//     			 k=(int)Math.random()*6+1;
//     			 spVO1.setLocno("L00000"+k);
//     		 }
//     		 else
//     		 {
//     			spVO1.setLocno("L00000"+i);
//     		 }
//     		
//     		spVO1.setSplat(new Float("23.56"));
//     		spVO1.setSplong(new Float("121.6"));
//     		;
//     		dao.insert(spVO1);
//     		 
//        }
//        System.out.println("insert ok");
         
         
         
         
        //刪除
// 		  dao.delete("S000013"); 
// 		  System.out.println("delete ok");
         
         
         //改
         
//         SpotsVO spVO3 = new SpotsVO();
//         spVO3.setLocNo("L000001");
//         spVO3.setSpotsName("我家");
//         spVO3.setSpotsLong(new Float("125.32"));
//         spVO3.setSpotsLat(new Float("33.33"));
//         spVO3.setSpotsNo("S000010");
// 		
//         dao.update(spVO3);
         
         //查1個
//         SpotsVO find = dao.findByPrimaryKey("S000005");
//         System.out.print(find.getSpotsNo() +",");
// 		 System.out.print(find.getSpotsName() +",");
// 		 System.out.print(find.getSpotsLat()+",");
// 		 System.out.println(find.getSpotsLong());
// 		
         
 		  //查全部
 		 List<SpotsVO> spots = dao.getAll();
 		 for(SpotsVO spot : spots)
 		 {
 			 System.out.print(spot.getSpno() +",");
 	 		 System.out.print(spot.getSpname() +",");
 	 		 System.out.print(spot.getSplat()+",");
 	 		 System.out.println(spot.getSplong());
 		 }
 		 
 		 

         
	}
	
	
	@Override
	public void insert(SpotsVO spotVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, spotVO.getSpname());
			pstmt.setString(2, spotVO.getLocno());
			pstmt.setFloat(4, spotVO.getSplat());
			pstmt.setFloat(3, spotVO.getSplong());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, spotVO.getSpname());
			pstmt.setString(2, spotVO.getLocno());
			pstmt.setFloat(4, spotVO.getSplat());
			pstmt.setFloat(3, spotVO.getSplong());
			pstmt.setString(5, spotVO.getSpno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, spno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
				// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
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
