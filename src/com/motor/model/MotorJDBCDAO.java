package com.motor.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MotorJDBCDAO implements MotorDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

	private static final String INSERT_STMT = "INSERT INTO MOTOR (motno, modtype, plateno,"
			+ " engno, manudate, mile, locno, status, note"
			+ ") VALUES ('M'||LPAD(TO_CHAR(motno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE MOTOR set modtype=?, plateno=?,"
			+ " engno=?, manudate=?, mile=?, locno=?," + "status=?, note=? where motno = ?";

	private static final String DELETE = "DELETE FROM motor where motno = ?";
	private static final String GET_ALL = "SELECT motno, modtype, plateno,"
			+ " engno, manudate, mile, locno, status, note FROM motor";

	private static final String GET_ONE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where motno = ?";

	private static final String GET_BY_MOTOR_TYPE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where modtype = ?";

	private static final String GET_BY_LOC_NO = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where locno = ?";

	private static final String GET_BY_MANUDATE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor  where manudate" + " between ? and ? order by manudate";

	@Override
	public void insert(MotorVO motorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, motorVO.getModtype());
			pstmt.setString(2, motorVO.getPlateno());
			pstmt.setString(3, motorVO.getEngno());
			pstmt.setTimestamp(4, motorVO.getManudate());
			pstmt.setInt(5, motorVO.getMile());
			pstmt.setString(6, motorVO.getLocno());
			pstmt.setString(7, motorVO.getStatus());
			pstmt.setString(8, motorVO.getNote());

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
	public void update(MotorVO motorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, motorVO.getModtype());
			pstmt.setString(2, motorVO.getPlateno());
			pstmt.setString(3, motorVO.getEngno());
			pstmt.setTimestamp(4, motorVO.getManudate());
			pstmt.setInt(5, motorVO.getMile());
			pstmt.setString(6, motorVO.getLocno());
			pstmt.setString(7, motorVO.getStatus());
			pstmt.setString(8, motorVO.getNote());
			pstmt.setString(9, motorVO.getMotno());

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
	public void delete(String motno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, motno);
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
	public MotorVO findByPrimaryKey(String motno) {

		MotorVO motorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
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
		return motorVO;
	}

	@Override
	public List<MotorVO> getAll() {
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				list.add(motorVO); // Store the row in the list
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

	@Override
	public Set<MotorVO> getMotorsByModelType(String modtype) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MOTOR_TYPE);
			pstmt.setString(1, modtype);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<MotorVO> getMotorsByLocNo(String locno) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_LOC_NO);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
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
		return set;
	}

	@Override
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MANUDATE);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
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
		return set;
	}

	private void setAttirbute(MotorVO motorVO, ResultSet rs) {
		try {
			motorVO.setMotno(rs.getString("motno"));
			motorVO.setModtype(rs.getString("modtype"));
			motorVO.setPlateno(rs.getString("plateno"));
			motorVO.setEngno(rs.getString("engno"));
			motorVO.setManudate(rs.getTimestamp("manudate"));
			motorVO.setMile(rs.getInt("mile"));
			motorVO.setLocno(rs.getString("locno"));
			motorVO.setStatus(rs.getString("status"));
			motorVO.setNote(rs.getString("note"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		MotorJDBCDAO dao = new MotorJDBCDAO();
		// 新增

//		for(int i = 1; i<30 ; i++){
//		 MotorVO motorVO1 = new MotorVO();
//		 motorVO1.setModtype("modtype");
//		 motorVO1.setPlateno("plateno");
//		 motorVO1.setEngno("engno");
//		 motorVO1.setManudate(java.sql.Timestamp.valueOf("2016-01-01 10:10:10"));
//		 motorVO1.setMile(111);
//		 motorVO1.setLocno("locno");
//		 motorVO1.setStatus("status");
//		 motorVO1.setNote("note");
//		 dao.insert(motorVO1);
//		}
		// System.out.println("insert ok");

		/*
		 * MotorVO motorVO2 = new MotorVO(); motorVO2.setModtype("modtype2");
		 * motorVO2.setPlateno("plateno2"); motorVO2.setEngno("engno2");
		 * motorVO2.setManudate(java.sql.Timestamp.valueOf("2012-02-02 10:10:10"
		 * )); motorVO2.setMile(222); motorVO2.setLocno("locno2");
		 * motorVO2.setStatus("status2"); motorVO2.setNote("note2");
		 * motorVO2.setMotor_no("M000000003"); dao.update(motorVO2);
		 * System.out.println("update ok");
		 */

		/*
		 * dao.delete("M000000004"); System.out.println("delete ok");
		 */

		/**/
		// MotorVO motorVO3 = dao.findByPrimaryKey("M000000005");
		// System.out.println(motorVO3.getMotno() +",");
		// System.out.println(motorVO3.getModtype() +",");
		// System.out.println(motorVO3.getPlateno() +",");
		// System.out.println(motorVO3.getEngno() +",");
		// System.out.println(DateFormatter(motorVO3.getManudate()) +",");
		// System.out.println(motorVO3.getMile() +",");
		// System.out.println(motorVO3.getLocno() +",");
		// System.out.println(motorVO3.getStatus() +",");
		// System.out.println(motorVO3.getNote() +",");
		// System.out.println("query ok");

		// 轉成文字format要自己要的格式後輸出 DateFormatter

		 List<MotorVO> list = dao.getAll();
		 System.out.println("=======================================");
		 for (MotorVO aMotor : list) {
		 printMethod(aMotor);
		 }
		//
		//
		// Set<MotorVO> set1 = dao.getMotorsByModelType("3");
		// System.out.println("=======================================");
		// for (MotorVO aMotor : set1) {
		// printMethod(aMotor);
		// }
		//
		// Set<MotorVO> set2 = dao.getMotorsByLocNo("aa");
		// System.out.println("=======================================");
		// for (MotorVO aMotor : set2) {
		// printMethod(aMotor);
		// }
		//
//		Set<MotorVO> set3 = dao.getMotorsByManuDate(java.sql.Timestamp.valueOf("2017-01-01 00:00:01"),
//				java.sql.Timestamp.valueOf("2017-09-30 23:59:59"));
//		System.out.println("=======================================");
//		for (MotorVO aMotor : set3) {
//			printMethod(aMotor);
//		}

	}

	private static String DateFormatter(Timestamp manudate) {
		String showDate = new SimpleDateFormat("yyyy/MM/dd").format(manudate);
		return showDate;
	}

	private static void printMethod(MotorVO aMotor) {

		System.out.println("getMotno :" + aMotor.getMotno() + ",");
		System.out.println("getModtype :" + aMotor.getModtype() + ",");
		System.out.println("getPlateno :" + aMotor.getPlateno() + ",");
		System.out.println("getEngno :" + aMotor.getEngno() + ",");

		System.out.println("getManudate :" + DateFormatter(aMotor.getManudate()) + ",");

		System.out.println("getMile :" + aMotor.getMile() + ",");
		System.out.println("getLocno :" + aMotor.getLocno() + ",");
		System.out.println("getStatus :" + aMotor.getStatus() + ",");
		System.out.println("getNote :" + aMotor.getNote() + ",");
		System.out.println();

	}

}