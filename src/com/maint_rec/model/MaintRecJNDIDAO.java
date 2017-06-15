package com.maint_rec.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MaintRecJNDIDAO implements MaintRecDAO_interface {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mrVO.getMotno());
			pstmt.setTimestamp(2, mrVO.getStartdate());
			pstmt.setTimestamp(3, mrVO.getEnddate());
			pstmt.setString(4, mrVO.getCont());

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
	public void update(MaintRecVO mrVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mrVO.getMotno());
			pstmt.setTimestamp(2, mrVO.getStartdate());
			pstmt.setTimestamp(3, mrVO.getEnddate());
			pstmt.setString(4, mrVO.getCont());
			pstmt.setString(5, mrVO.getMaintno());

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
	public void delete(String maintno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, maintno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, maintno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
			}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
				list.add(mrVO); // Store the row in the list
			}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MOTOR);
			pstmt.setString(1, motno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mrVO = new MaintRecVO();
				setAttirbute(mrVO, rs); // 拉出來寫成一個方法
				set.add(mrVO); // Store the row in the vector
			}

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

}
