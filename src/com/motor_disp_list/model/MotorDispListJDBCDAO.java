package com.motor_disp_list.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotorDispListJDBCDAO implements MotorDispListDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String GET_ONE_BY_MODO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where mdno = ?";

	private static final String GET_ONE_BY_MOTNO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where motno = ?";

	@Override
	public MotorDispListVO findByPrimaryKeyDispatchNo(String mdno) {

		MotorDispListVO mdlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_MODO);

			pstmt.setString(1, mdno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdlVO = new MotorDispListVO();
				setAttirbute(mdlVO, rs); // 拉出來寫成一個方法
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
		return mdlVO;
	}

	@Override
	public MotorDispListVO findByPrimaryKeyMotorNo(String motno) {

		MotorDispListVO mdlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_MOTNO);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdlVO = new MotorDispListVO();
				setAttirbute(mdlVO, rs); // 拉出來寫成一個方法
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
		return mdlVO;
	}

	private void setAttirbute(MotorDispListVO mdlVO, ResultSet rs) {
		try {
			mdlVO.setMdno(rs.getString("mdno"));
			mdlVO.setMotno(rs.getString("motno"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		MotorDispListJDBCDAO dao = new MotorDispListJDBCDAO();

		MotorDispListVO mdlVO1 = dao.findByPrimaryKeyDispatchNo("MD000002");
		System.out.println(mdlVO1.getMdno() + ",");
		System.out.println(mdlVO1.getMotno() + ",");
		System.out.println("query by mdno ok");

		MotorDispListVO mdlVO2 = dao.findByPrimaryKeyMotorNo("M000003");
		System.out.println(mdlVO2.getMdno() + ",");
		System.out.println(mdlVO2.getMotno() + ",");
		System.out.println("query by motno byok");

	}

}
