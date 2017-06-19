package com.emt_dispatch.model;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class EmtDispatchDAO implements EmtDispatchDAO_interface{
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
		private static final String INSERT_STMT = 
			"INSERT INTO EMT_DISPATCH (EDNO, LOCNO, DEMANDDATE, CLOSEDDATE, PROG) VALUES ('ED'||lpad(to_char(edno_seq.NEXTVAL),6,'0'), ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM EMT_DISPATCH order by EDNO";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM EMT_DISPATCH where EDNO = ?";
		private static final String DELETE = 
			"DELETE FROM EMT_DISPATCH where EDNO = ?";
		private static final String UPDATE = 
			"UPDATE EMT_DISPATCH set LOCNO=?, DEMANDDATE=?, CLOSEDDATE=?, PROG=? where EDNO = ?";

		@Override
		public void insert(EmtDispatchVO emtDispatchVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, emtDispatchVO.getLocno());
				pstmt.setTimestamp(2, emtDispatchVO.getDemanddate());
				pstmt.setTimestamp(3, emtDispatchVO.getCloseddate());
				pstmt.setString(4, emtDispatchVO.getProg());
				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public void update(EmtDispatchVO emtDispatchVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, emtDispatchVO.getLocno());
				pstmt.setTimestamp(2, emtDispatchVO.getDemanddate());
				pstmt.setTimestamp(3, emtDispatchVO.getCloseddate());
				pstmt.setString(4, emtDispatchVO.getProg());
				pstmt.setString(5, emtDispatchVO.getEdno());
				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public void delete(String edno) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, edno);

				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public EmtDispatchVO findByPrimaryKey(String edno) {

			EmtDispatchVO emtDispatchVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, edno);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// emtDispatchVO 也稱為 Domain objects
					emtDispatchVO = new EmtDispatchVO();
					emtDispatchVO.setEdno(rs.getString("edno"));
					emtDispatchVO.setLocno(rs.getString("locno"));
					emtDispatchVO.setDemanddate(rs.getTimestamp("demanddate"));
					emtDispatchVO.setCloseddate(rs.getTimestamp("closeddate"));
					emtDispatchVO.setProg(rs.getString("prog"));
				}

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
			return emtDispatchVO;
		}

		@Override
		public List<EmtDispatchVO> getAll() {
			List<EmtDispatchVO> list = new ArrayList<EmtDispatchVO>();
			EmtDispatchVO emtDispatchVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// emtDispatchVO 也稱為 Domain objects
					emtDispatchVO = new EmtDispatchVO();
					emtDispatchVO.setEdno(rs.getString("edno"));
					emtDispatchVO.setLocno(rs.getString("locno"));
					emtDispatchVO.setDemanddate(rs.getTimestamp("demanddate"));
					emtDispatchVO.setCloseddate(rs.getTimestamp("closeddate"));
					emtDispatchVO.setProg(rs.getString("prog"));
					list.add(emtDispatchVO); // Store the row in the list
				}

				
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
			return list;
		}
		
		
		public static void main(String[] args){

			EmtDispatchJDBCDAO dao = new EmtDispatchJDBCDAO();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			
			// 新增
//			EmtDispatchVO emtDispatchVO1 = new EmtDispatchVO();
//			emtDispatchVO1.setLocno("L000002");
//			emtDispatchVO1.setDemanddate(Timestamp.valueOf("2017-01-01 11:11:11"));
//			emtDispatchVO1.setCloseddate(Timestamp.valueOf("2017-02-02 20:20:20"));
//			emtDispatchVO1.setProg("dispatched");
//			dao.insert(emtDispatchVO1);

			// 修改
//			EmtDispatchVO emtDispatchVO2 = new EmtDispatchVO();
//			emtDispatchVO2.setLocno("L000002");
//			emtDispatchVO2.setDemanddate(Timestamp.valueOf("2017-03-03 11:33:33"));
//			emtDispatchVO2.setCloseddate(Timestamp.valueOf("2017-02-02 20:20:20"));
//			emtDispatchVO2.setProg("closed");
//			emtDispatchVO2.setEdno("ED000021");
//			dao.update(emtDispatchVO2);

			// 刪除
//			dao.delete("ED000021");
			

			// 查詢
			EmtDispatchVO emtDispatchVO3 = dao.findByPrimaryKey("ED000003");
			System.out.print(emtDispatchVO3.getEdno() + ",");
			System.out.print(emtDispatchVO3.getLocno() + ",");
			System.out.print(sdf.format(emtDispatchVO3.getDemanddate()) + ",");
			System.out.print(sdf.format(emtDispatchVO3.getCloseddate()) + ",");
			System.out.print(emtDispatchVO3.getProg());
			System.out.println("---------------------");

			// 查詢
//			List<EmtDispatchVO> list = dao.getAll();
//			for (EmtDispatchVO aEmtDispatch : list) {
//				System.out.print(aEmtDispatch.getEdno() + ",");
//				System.out.print(aEmtDispatch.getLocno() + ",");
//				System.out.print(sdf.format(aEmtDispatch.getDemanddate()) + ",");
//				System.out.print(sdf.format(aEmtDispatch.getCloseddate()) + ",");
//				System.out.print(aEmtDispatch.getProg());
//				System.out.println();
//			}
		}
	}