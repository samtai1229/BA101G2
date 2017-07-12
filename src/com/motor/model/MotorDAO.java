package com.motor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.motor_model.model.MotorModelVO;

public class MotorDAO implements MotorDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO MOTOR (motno, modtype, plateno,"
			+ " engno, manudate, mile, note, locno"
			+ ") VALUES ('M'||LPAD(TO_CHAR(MOTNO_SEQ.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, 'TPE00')";

	private static final String UPDATE = "UPDATE MOTOR set modtype=?, plateno=?,"
			+ " engno=?, manudate=?, mile=?, locno=?," + "status=?, note=? where motno = ?";

	private static final String DELETE = "DELETE FROM motor where motno = ?";
	private static final String GET_ALL = "SELECT motno, modtype, plateno,"
			+ " engno, manudate, mile, locno, status, note FROM motor order by motno";

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
	
	private static final String FUZZY_SEARCH = 
//		    "SELECT m.motno, m.modtype, d.name, d.displacement, d.renprice, m.plateno, m.engno, m.manudate, m.mile, m.locno, d.saleprice,m.status, m.note FROM MOTOR m join motor_model d ON m.modtype = d.modtype where m.MOTNO LIKE ? or m.MODTYPE LIKE ? or m.PLATENO LIKE ? or m.ENGNO LIKE ? or m.MANUDATE LIKE ? or m.MILE LIKE ? or m.LOCNO LIKE ? or m.STATUS LIKE ? or m.NOTE LIKE ? or d.NAME LIKE ? or d.DISPLACEMENT LIKE ? or d.RENPRICE LIKE ? or d.SALEPRICE LIKE ? ORDER BY m.MOTNO";
			"SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM MOTOR where MOTNO LIKE ? or MODTYPE LIKE ? or PLATENO LIKE ? or ENGNO LIKE ? or MANUDATE LIKE ? or MILE LIKE ? or LOCNO LIKE ? or STATUS LIKE ? or NOTE LIKE ?  ORDER BY MOTNO";

//	private static final String GET_ALL_STATUS="slelect distinct status from motor";
	
	private static final String GET_MODTYPE_BY_LOCNO = "SELECT distinct modtype FROM MOTOR where locno <> ?";
	
	private static final String GET_BY_MODTYP_AND_LOCNO = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where modtype = ? and locno <> ? and status in ('leasable','unleasable')";
	
	@Override
	public void insert(MotorVO motorVO) {
		System.out.println("MotorDAO insert in");
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, motorVO.getModtype());
			pstmt.setString(2, motorVO.getPlateno());
			pstmt.setString(3, motorVO.getEngno());
			pstmt.setTimestamp(4, motorVO.getManudate());
			pstmt.setInt(5, motorVO.getMile());
			pstmt.setString(6, motorVO.getNote());

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
	public void update(MotorVO motorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, motno);
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
	public MotorVO findByPrimaryKey(String motno) {

		MotorVO motorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				list.add(motorVO); // Store the row in the list
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
	public Set<MotorVO> getMotorsByModelType(String modtype) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MOTOR_TYPE);
			pstmt.setString(1, modtype);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
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
	public Set<MotorVO> getMotorsByLocNo(String locno) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_LOC_NO);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
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
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
//			String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getTimestamp("manudate"));			
//			motorVO.setManudate(Timestamp.valueOf(str));			
			motorVO.setManudate(rs.getTimestamp("manudate"));			
			motorVO.setMile(rs.getInt("mile"));
			motorVO.setLocno(rs.getString("locno"));
			motorVO.setStatus(rs.getString("status"));
			motorVO.setNote(rs.getString("note"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MotorVO> fuzzyGetAll(String fuzzyValue) {
		
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FUZZY_SEARCH);

			pstmt.setString(1, "%"+fuzzyValue+"%");
			pstmt.setString(2, "%"+fuzzyValue+"%");
			pstmt.setString(3, "%"+fuzzyValue+"%");
			pstmt.setString(4, "%"+fuzzyValue+"%");
			pstmt.setString(5, "%"+fuzzyValue+"%");
			pstmt.setString(6, "%"+fuzzyValue+"%");
			pstmt.setString(7, "%"+fuzzyValue+"%");
			pstmt.setString(8, "%"+fuzzyValue+"%");
			pstmt.setString(9, "%"+fuzzyValue+"%");
		
System.out.println("motorDAO: "+"'%" + fuzzyValue + "%'");	
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				motorVO = new MotorVO();

				motorVO.setMotno(rs.getString("motno"));
				motorVO.setModtype(rs.getString("modtype"));
				motorVO.setPlateno(rs.getString("plateno"));
				motorVO.setEngno(rs.getString("engno"));						
				motorVO.setManudate(rs.getTimestamp("manudate"));			
				motorVO.setMile(rs.getInt("mile"));
				motorVO.setLocno(rs.getString("locno"));
				motorVO.setStatus(rs.getString("status"));
				motorVO.setNote(rs.getString("note"));
				list.add(motorVO); 
				
System.out.println("motorLsitString: "+list.toString());
				
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
System.out.println("motorDAOlist: " + list);
		return list;
	}

	@Override
	public HashSet<MotorVO> getModtypeByLocNo(String locno) {
		HashSet<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MODTYPE_BY_LOCNO);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				motorVO.setModtype(rs.getString("modtype"));
				set.add(motorVO); // Store the row in the vector
			}

			// Handle any driver errors
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
	public List<MotorVO> getMotorsByModtypeAndLocno(String modtype, String locno) {
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MODTYP_AND_LOCNO);
			pstmt.setString(1, modtype);
			pstmt.setString(2, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				list.add(motorVO); // Store the row in the vector
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
	public List<MotorVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}
}
