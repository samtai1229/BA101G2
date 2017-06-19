package com.emt_disp_list.model;

import java.util.*;
import java.io.IOException;
import java.sql.*;

public class EmtDispListJDBCDAO implements EmtDispListDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EMT_DISP_LIST (EDNO, EMTNO) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMT_DISP_LIST order by EDNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EMT_DISP_LIST where EDNO = ?";
	private static final String DELETE = "DELETE FROM EMT_DISP_LIST where EDNO = ? and EMTNO = ?";
	private static final String UPDATE = "UPDATE EMT_DISP_LIST set EDNO = ?, EMTNO = ? where EDNO = ? and EMTNO = ?";

	@Override
	public void insert(EmtDispListVO emtDispListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtDispListVO.getEdno());
			pstmt.setString(2, emtDispListVO.getEmtno());

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
	public void update(EmtDispListVO emtDispListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emtDispListVO.getEdno());
			pstmt.setString(2, emtDispListVO.getEmtno());
			pstmt.setString(3, emtDispListVO.getEdno());
			pstmt.setString(4, emtDispListVO.getEmtno());

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
	public void delete(String edno, String emtno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, edno);
			pstmt.setString(2, emtno);

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
	public EmtDispListVO findByPrimaryKey(String edno, String emtno) {

		EmtDispListVO emtDispListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, edno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtDispListVO 也稱為 Domain objects
				emtDispListVO = new EmtDispListVO();
				emtDispListVO.setEdno(rs.getString("edno"));
				emtDispListVO.setEmtno(rs.getString("emtno"));
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
		return emtDispListVO;
	}

	@Override
	public List<EmtDispListVO> getAll() {
		List<EmtDispListVO> list = new ArrayList<EmtDispListVO>();
		EmtDispListVO emtDispListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtDispListVO 也稱為 Domain objects
				emtDispListVO = new EmtDispListVO();
				emtDispListVO.setEdno(rs.getString("edno"));
				emtDispListVO.setEmtno(rs.getString("emtno"));
				list.add(emtDispListVO); // Store the row in the list
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
		return list;
	}

	public static void main(String[] args) throws IOException {

		EmtDispListJDBCDAO dao = new EmtDispListJDBCDAO();

		// 新增
		// EmtDispListVO emtDispListVO1 = new EmtDispListVO();
		// emtDispListVO1.setEdno("ED000001");
		// emtDispListVO1.setEmtno("E000002");
		// dao.insert(emtDispListVO1);

		// 修改 (不能用)
//		EmtDispListVO emtDispListVO2 = new EmtDispListVO();
//		emtDispListVO2.setEdno("ED000001");
//		emtDispListVO2.setEmtno("E000001");
//
//		emtDispListVO2.setEdno("ED000001");
//		emtDispListVO2.setEmtno("E000001");
//		dao.update(emtDispListVO2);

		// 刪除
		// dao.delete("ED000001", "E000001");

		// 查詢
		// EmtDispListVO emtDispListVO3 = dao.findByPrimaryKey("ED000001",
		// "E000001");
		// System.out.print(emtDispListVO3.getEdno() + ",");
		// System.out.print(emtDispListVO3.getEmtno() + ",");
		// System.out.println("---------------------");

		// 查詢
		List<EmtDispListVO> list = dao.getAll();
		for (EmtDispListVO aEmtDispList : list) {
			System.out.print(aEmtDispList.getEdno() + ",");
			System.out.print(aEmtDispList.getEmtno());
			System.out.println();
		}
	}
}