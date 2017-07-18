package com.emt_list.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.*;

public class EmtListDAO implements EmtListDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO EMT_LIST (RENTNO, EMTNO) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMT_LIST order by RENTNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EMT_LIST where RENTNO = ?";
	private static final String DELETE = "DELETE FROM EMT_LIST where RENTNO = ? and EMTNO = ?";
	private static final String UPDATE = "UPDATE EMT_LIST set RENTNO = ?, EMTNO = ? where RENTNO = ? and EMTNO = ?";

	@Override
	public void insert(EmtListVO emtListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtListVO.getRentno());
			pstmt.setString(2, emtListVO.getEmtno());

			pstmt.executeUpdate();

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
	public void update(EmtListVO emtListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emtListVO.getRentno());
			pstmt.setString(2, emtListVO.getEmtno());
			pstmt.setString(3, emtListVO.getRentno());
			pstmt.setString(4, emtListVO.getEmtno());

			pstmt.executeUpdate();

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
	public void delete(String rentno, String emtno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rentno);
			pstmt.setString(2, emtno);

			pstmt.executeUpdate();

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
	public EmtListVO findByPrimaryKey(String rentno, String emtno) {

		EmtListVO emtListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rentno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtListVO 也稱為 Domain objects
				emtListVO = new EmtListVO();
				emtListVO.setRentno(rs.getString("rentno"));
				emtListVO.setEmtno(rs.getString("emtno"));
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
		return emtListVO;
	}

	@Override
	public List<EmtListVO> getAll() {
		List<EmtListVO> list = new ArrayList<EmtListVO>();
		EmtListVO emtListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtListVO 也稱為 Domain objects
				emtListVO = new EmtListVO();
				emtListVO.setRentno(rs.getString("rentno"));
				emtListVO.setEmtno(rs.getString("emtno"));
				list.add(emtListVO); // Store the row in the list
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

	public static void main(String[] args) throws IOException {

		EmtListJDBCDAO dao = new EmtListJDBCDAO();

		// 新增
		// EmtListVO emtListVO1 = new EmtListVO();
		// emtListVO1.setRentno("R000001");
		// emtListVO1.setEmtno("E000003");
		// dao.insert(emtListVO1);

		// 修改 (不能用)
		// EmtListVO emtListVO2 = new EmtListVO();
		// emtListVO2.setRentno("R000001");
		// emtListVO2.setEmtno("E000002");
		//
		// emtListVO2.setRentno("R000001");
		// emtListVO2.setEmtno("E000002");
		// dao.update(emtListVO2);

		// 刪除
		// dao.delete("R000001", "E000003");

		// 查詢
		EmtListVO emtListVO3 = dao.findByPrimaryKey("R000001", "E000002");
		System.out.print(emtListVO3.getRentno() + ",");
		System.out.print(emtListVO3.getEmtno() + ",");
		System.out.println("---------------------");

		// 查詢
		// List<EmtListVO> list = dao.getAll();
		// for (EmtListVO aEmtList : list) {
		// System.out.print(aEmtList.getRentno() + ",");
		// System.out.print(aEmtList.getEmtno());
		// System.out.println();
		// }
	}
}