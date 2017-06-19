package com.emt_cate.model;

import java.util.*;
import java.io.IOException;
import java.sql.*;

public class EmtCateJDBCDAO implements EmtCateDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EMT_CATE (ECNO, TYPE, PIC, PRICE) VALUES ('EC'||lpad(to_char(ecno_seq.NEXTVAL),6,'0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMT_CATE order by ECNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EMT_CATE where ECNO = ?";
	private static final String DELETE = "DELETE FROM EMT_CATE where ECNO = ?";
	private static final String UPDATE = "UPDATE EMT_CATE set type=?, pic=?, price=? where ECNO = ?";

	@Override
	public void insert(EmtCateVO emtCateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtCateVO.getType());
			pstmt.setBytes(2, emtCateVO.getPic());
			pstmt.setInt(3, emtCateVO.getPrice());

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
	public void update(EmtCateVO emtCateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emtCateVO.getType());
			pstmt.setBytes(2, emtCateVO.getPic());
			pstmt.setInt(3, emtCateVO.getPrice());
			pstmt.setString(4, emtCateVO.getEcno());

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
	public void delete(String ecno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ecno);

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
	public EmtCateVO findByPrimaryKey(String ecno) {

		EmtCateVO emtCateVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ecno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtCateVO 也稱為 Domain objects
				emtCateVO = new EmtCateVO();
				emtCateVO.setEcno(rs.getString("ecno"));
				emtCateVO.setType(rs.getString("type"));
				emtCateVO.setPic(rs.getBytes("pic"));
				emtCateVO.setPrice(rs.getInt("price"));
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
		return emtCateVO;
	}

	@Override
	public List<EmtCateVO> getAll() {
		List<EmtCateVO> list = new ArrayList<EmtCateVO>();
		EmtCateVO emtCateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtCateVO 也稱為 Domain objects
				emtCateVO = new EmtCateVO();
				emtCateVO.setEcno(rs.getString("ecno"));
				emtCateVO.setType(rs.getString("type"));
				emtCateVO.setPrice(rs.getInt("price"));
				list.add(emtCateVO); // Store the row in the list
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

		EmtCateJDBCDAO dao = new EmtCateJDBCDAO();

		// 新增
		EmtCateVO emtCateVO1 = new EmtCateVO();
		emtCateVO1.setType("安全帽");
		emtCateVO1.setPic(PicReadAndWriter.getPictureByteArray("WebContent/backend/images/Helmet.jpg"));
		emtCateVO1.setPrice(50);
		dao.insert(emtCateVO1);

		// 修改
		// EmtCateVO emtCateVO2 = new EmtCateVO();
		// emtCateVO2.setEcno("EC000005");
		// emtCateVO2.setType("安全帽");
		// emtCateVO2.setPic(getPictureByteArray("backend/images/helmet.jpg"));
		// emtCateVO2.setPrice(50);
		// dao.update(emtCateVO2);

		// 刪除
		// dao.delete("EC000022");
		// dao.delete("EC000023");
		// dao.delete("EC000024");
		// dao.delete("EC000025");
		// dao.delete("EC000026");

		// 查詢
		// EmtCateVO emtCateVO3 = dao.findByPrimaryKey("EC000021");
		// System.out.print(emtCateVO3.getEcno() + ",");
		// System.out.print(emtCateVO3.getType() + ",");
		// System.out.print(emtCateVO3.getPrice());
		// System.out.println("---------------------");
		// PicReadAndWriter.readPicture(emtCateVO3.getPic(), "helmet.jpg");

		// 查詢
		List<EmtCateVO> list = dao.getAll();
		for (EmtCateVO aEmtCate : list) {
			System.out.print(aEmtCate.getEcno() + ",");
			System.out.print(aEmtCate.getType() + ",");
			System.out.print(aEmtCate.getPrice());
			System.out.println();
		}
	}
}