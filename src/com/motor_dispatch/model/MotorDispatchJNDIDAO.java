package com.motor_dispatch.model;

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

import com.motor_disp_list.model.MotorDispListVO;
import com.rent_ord.model.RentOrdVO;

public class MotorDispatchJNDIDAO implements MotorDispatchDAO_interface {
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdVO.getLocno());
			pstmt.setTimestamp(2, mdVO.getFilldate());
			pstmt.setTimestamp(3, mdVO.getCloseddate());
			pstmt.setString(4, mdVO.getProg());

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
	public void update(MotorDispatchVO mdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mdVO.getLocno());
			pstmt.setTimestamp(2, mdVO.getFilldate());
			pstmt.setTimestamp(3, mdVO.getCloseddate());
			pstmt.setString(4, mdVO.getProg());
			pstmt.setString(5, mdVO.getMdno());

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
	public void delete(String mdno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mdno);
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
	public MotorDispatchVO findByPrimaryKey(String mdno) {

		MotorDispatchVO mdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, mdno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				list.add(mdVO); // Store the row in the list
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
	public Set<MotorDispatchVO> getMotorDispatchsByLoc(String locno) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_LOC);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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

	@Override
	public Set<MotorDispatchVO> getMotorDispatchsByProg(String prog) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_PROG);
			pstmt.setString(1, prog);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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

	@Override
	public RentOrdVO checkDispatchableMotors(String motno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertByHib(MotorDispatchVO mdVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByHib(MotorDispatchVO mdVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByHib(String mdno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MotorDispatchVO findByPkByHib(String mdno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MotorDispatchVO> getAllByHib() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<MotorDispListVO> getMdListByMdnoByHib(String mdno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MotorDispatchVO> getByLocnoByHib(String locno) {
		// TODO Auto-generated method stub
		return null;
	}

}
