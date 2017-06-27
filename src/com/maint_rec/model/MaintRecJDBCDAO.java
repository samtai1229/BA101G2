package com.maint_rec.model;

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

public class MaintRecJDBCDAO implements MaintRecDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

	private static final String INSERT_STMT = "INSERT INTO MAINT_REC (maintno, motno, "
			+ " startdate, enddate, cont) VALUES " 
			+ "('MR'||LPAD(TO_CHAR(maintno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE MAINT_REC set motno=?, startdate=?,"
			+ " enddate=?, cont=? where maintno = ?";

	private static final String DELETE = "DELETE FROM MAINT_REC where maintno = ?";
	
	private static final String GET_ALL = "SELECT maintno, motno, " 
			+ " startdate, enddate, cont  FROM MAINT_REC";

	private static final String GET_ONE = "SELECT maintno, motno, "
			+ " startdate, enddate, cont  FROM MAINT_REC where maintno = ?";

	private static final String GET_BY_MOTOR = "SELECT maintno, motno, startdate,"
			+ " enddate, cont FROM MAINT_REC where motno = ?";

	@Override
	public void insert(MaintRecVO mrVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mrVO.getMotno());
			pstmt.setTimestamp(2, mrVO.getStartdate());
			pstmt.setTimestamp(3, mrVO.getEnddate());
			pstmt.setString(4, mrVO.getCont());

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
	public void update(MaintRecVO mrVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mrVO.getMotno());
			pstmt.setTimestamp(2, mrVO.getStartdate());
			pstmt.setTimestamp(3, mrVO.getEnddate());
			pstmt.setString(4, mrVO.getCont());
			pstmt.setString(5, mrVO.getMaintno());

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
	public void delete(String maintno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, maintno);
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
	public MaintRecVO findByPrimaryKey(String maintno) {

		MaintRecVO mrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, maintno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
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
		return mrVO;
	}

	private void setAttirbute(MaintRecVO mrVO, ResultSet rs) {
		try {

			mrVO.setMaintno(rs.getString("maintno"));
			mrVO.setMotno(rs.getString("motno"));
			mrVO.setStartdate(rs.getTimestamp("startdate"));
			mrVO.setEnddate(rs.getTimestamp("enddate"));
			mrVO.setCont(rs.getString("cont"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<MaintRecVO> getAll() {
		List<MaintRecVO> list = new ArrayList<MaintRecVO>();
		MaintRecVO mrVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
				list.add(mrVO); // Store the row in the list
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
	public Set<MaintRecVO> getMaintRecByMotor(String motno) {
		Set<MaintRecVO> set = new LinkedHashSet<MaintRecVO>();
		MaintRecVO mrVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_MOTOR);
			pstmt.setString(1, motno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
				set.add(mrVO); // Store the row in the vector
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

	public static void main(String[] args) {

		MaintRecJDBCDAO dao = new MaintRecJDBCDAO();
		// 新增

		// MaintRecVO mrVO1 = new MaintRecVO();
		// mrVO1.setMotno("monto");
		// mrVO1.setStartdate(java.sql.Timestamp.valueOf("2016-01-01
		// 10:10:10"));
		// mrVO1.setEnddate(java.sql.Timestamp.valueOf("2016-02-01 10:10:10"));
		// mrVO1.setCont("cont");
		// dao.insert(mrVO1);
		// System.out.println("insert ok");

		// MaintRecVO mrVO2 = new MaintRecVO();
		// mrVO2.setMotno("monto3");
		// mrVO2.setStartdate(java.sql.Timestamp.valueOf("2013-01-01
		// 10:10:10"));
		// mrVO2.setEnddate(java.sql.Timestamp.valueOf("2013-02-01 10:10:10"));
		// mrVO2.setCont("cont3");
		// mrVO2.setMaintno("MR000002");
		// dao.update(mrVO2);
		// System.out.println("update ok");

		// dao.delete("MR000006");
		// System.out.println("delete ok");

		// MaintRecVO mrVO3 = dao.findByPrimaryKey("MR000005");
		// System.out.println(mrVO3.getMaintno() +",");
		// System.out.println(mrVO3.getMotno() +",");
		// System.out.println(new
		// SimpleDateFormat("yyyy/MM/dd").format(mrVO3.getStartdate()) +",");
		// System.out.println(new
		// SimpleDateFormat("yyyy/MM/dd").format(mrVO3.getEnddate()) +",");
		// System.out.println(mrVO3.getCont() +",");
		// System.out.println("query ok");

		List<MaintRecVO> list = dao.getAll();
		System.out.println("=======================================");
		for (MaintRecVO aMR : list) {
			printMethod(aMR);
		}

	}

	private static void printMethod(MaintRecVO aMR) {

		System.out.println("getMaintno :" + aMR.getMaintno() + ",");
		System.out.println("getMotno :" + aMR.getMotno() + ",");
		System.out.println("getStartdate :" + DateFormatter(aMR.getStartdate()) + ",");
		System.out.println("getEnddate :" + DateFormatter(aMR.getEnddate()) + ",");
		System.out.println("getEnddate :" + aMR.getCont() + ",");

		System.out.println();

	}

	private static String DateFormatter(Timestamp manudate) {
		String showDate = new SimpleDateFormat("yyyy/MM/dd").format(manudate);
		return showDate;
	}

}
