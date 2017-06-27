package com.motor_dispatch.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MotorDispatchJDBCDAO implements MotorDispatchDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

	private static final String INSERT_STMT = "INSERT INTO MOTOR_DISPATCH" 
			+ " (mdno, locno, filldate, closeddate, prog"
			+ ") VALUES ('MD'||LPAD(TO_CHAR(mdno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?)";

	private static final String UPDATE = "UPDATE MOTOR_DISPATCH set locno=?,"
			+ " filldate=?, closeddate=?, prog=? where mdno = ?";

	private static final String DELETE = "DELETE FROM MOTOR_DISPATCH where mdno = ?";

	private static final String GET_ONE = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where mdno = ?";

	private static final String GET_ALL = "SELECT mdno, locno, filldate," 
			+ "  closeddate, prog FROM MOTOR_DISPATCH";

	private static final String GET_BY_LOC = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where locno = ?";

	private static final String GET_BY_PROG = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where prog = ?";

	@Override
	public void insert(MotorDispatchVO mdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			// mdno, locno, filldate, closeddate, prog

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdVO.getLocno());
			pstmt.setTimestamp(2, mdVO.getFilldate());
			pstmt.setTimestamp(3, mdVO.getCloseddate());
			pstmt.setString(4, mdVO.getProg());

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
	public void update(MotorDispatchVO mdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mdVO.getLocno());
			pstmt.setTimestamp(2, mdVO.getFilldate());
			pstmt.setTimestamp(3, mdVO.getCloseddate());
			pstmt.setString(4, mdVO.getProg());
			pstmt.setString(5, mdVO.getMdno());

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
	public void delete(String mdno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mdno);
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
	public MotorDispatchVO findByPrimaryKey(String mdno) {

		MotorDispatchVO mdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, mdno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
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
		return mdVO;
	}

	private void setAttirbute(MotorDispatchVO mdVO, ResultSet rs) {
		try {

			mdVO.setMdno(rs.getString("mdno"));
			mdVO.setLocno(rs.getString("locno"));
			mdVO.setFilldate(rs.getTimestamp("filldate"));
			mdVO.setCloseddate(rs.getTimestamp("closeddate"));
			mdVO.setProg(rs.getString("prog"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MotorDispatchVO> getAll() {
		List<MotorDispatchVO> list = new ArrayList<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				list.add(mdVO); // Store the row in the list
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
	public Set<MotorDispatchVO> getMotorDispatchsByLoc(String locno) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_LOC);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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
	public Set<MotorDispatchVO> getMotorDispatchsByProg(String prog) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_PROG);
			pstmt.setString(1, prog);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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

		MotorDispatchJDBCDAO dao = new MotorDispatchJDBCDAO();
		// 新增

		// MotorDispatchVO mdVO1 = new MotorDispatchVO();
		// mdVO1.setLocno("locno2");
		// mdVO1.setFilldate(java.sql.Timestamp.valueOf("2017-01-01 10:10:10"));
		// mdVO1.setCloseddate(java.sql.Timestamp.valueOf("2017-02-01
		// 10:10:10"));
		// mdVO1.setProg("closed");
		// dao.insert(mdVO1);
		// System.out.println("insert ok");
		//
		// MotorDispatchVO mdVO2 = new MotorDispatchVO();
		// mdVO2.setLocno("locno2");
		// mdVO2.setFilldate(java.sql.Timestamp.valueOf("2017-01-01 10:10:10"));
		// mdVO2.setCloseddate(java.sql.Timestamp.valueOf("2017-02-01
		// 10:10:10"));
		// mdVO2.setProg("rejected");
		// mdVO2.setMdno("MD000004");
		// dao.update(mdVO2);
		// System.out.println("update ok");

		// dao.delete("MD000005");
		// System.out.println("delete ok");

		// MotorDispatchVO mdVO1 = dao.findByPrimaryKey("MD000006");
		// System.out.println(mdVO1.getMdno() +",");
		// System.out.println(mdVO1.getLocno() +",");
		// System.out.println(mdVO1.getFilldate() +",");
		// System.out.println(mdVO1.getCloseddate() +",");
		// System.out.println(mdVO1.getProg() +",");
		// System.out.println("query ok");

		 List<MotorDispatchVO> list = dao.getAll();
		 System.out.println("=======================================");
		 for (MotorDispatchVO aMD : list) {
		 printMethod(aMD);
		 }
		//
		//
		// Set<MotorDispatchVO> set1 = dao.getMotorDispatchsByLoc("locno2");
		// System.out.println("=======================================");
		// for (MotorDispatchVO aMD : set1) {
		// printMethod(aMD);
		// }
		//
//		Set<MotorDispatchVO> set2 = dao.getMotorDispatchsByProg("rejected");
//		System.out.println("=======================================");
//		for (MotorDispatchVO aMD : set2) {
//			printMethod(aMD);
//		}

	}

	private static void printMethod(MotorDispatchVO aMD) {

		System.out.println("" + aMD.getMdno() + ",");
		System.out.println("" + aMD.getLocno() + ",");
		System.out.println("" + aMD.getFilldate() + ",");
		System.out.println("" + aMD.getCloseddate() + ",");
		System.out.println("" + aMD.getProg() + ",");
		System.out.println();

	}

}
