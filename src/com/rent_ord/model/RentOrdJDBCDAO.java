package com.rent_ord.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class RentOrdJDBCDAO implements RentOrdDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

//	private static final String INSERT_STMT = "INSERT INTO RENT_ORD" 
//			+ " (rentno, memno, motno, slocno, rlocno, milstart, milend, filldate, "
//			+ "startdate, enddate, returndate, fine, total, rank, status, note"
//			+ ") VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

//	private static final String UPDATE = "UPDATE RENT_ORD set memno=?, motno=?,"
//	+ " slocno=?, rlocno=?, milstart=?, milend=?, filldate=?, startdate=?, enddate=?,"
//	+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";
	
	
	//合理版本
    final String INSERT_STMT = "INSERT INTO RENT_ORD"
	+ " (rentno, memno, motno, slocno, rlocno, milstart,  "
	+ "startdate, enddate, note"
	+ ") VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'),  ?, ?, ?,"
	+ " ?, ?, ?, ?, ?)";

    //合理版本: 去掉 filldate, memno
	private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
			+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
			+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";

	private static final String DELETE = "DELETE FROM RENT_ORD where rentno = ?";

	private static final String selectFactor = "SELECT rentno, memno, motno, slocno, rlocno,"
			+ " milstart, milend, to_char(filldate,'yyyy-mm-dd hh:mm:ss') filldate, "
			+ "to_char(startdate,'yyyy-mm-dd hh:mm:ss') startdate, "
			+ "to_char(enddate,'yyyy-mm-dd hh:mm:ss') enddate, "
			+ "to_char(returndate,'yyyy-mm-dd hh:mm:ss') returndate, fine, total," 
			+ " rank, status, note ";

	private static final String GET_ALL = 
			selectFactor + " FROM RENT_ORD";

	private static final String GET_ONE = 
			selectFactor + " FROM RENT_ORD where rentno = ?";

	private static final String GET_BY_START_LOC_NO = 
			selectFactor + " FROM RENT_ORD where slocno = ?";

	private static final String GET_BY_RETURN_LOC_NO = 
			selectFactor + " FROM RENT_ORD where rlocno = ?";

	private static final String GET_BY_STATUS = 
			selectFactor + " FROM RENT_ORD where status = ?";

	private static final String GET_BY_STARTTIME_PEROID = 
			selectFactor + " FROM RENT_ORD  where startdate"
			+ " between ? and ? order by startdate";

	private static final String GET_BY_ENDTIME_PEROID = 
			selectFactor + " From RENT_ORD  where enddate"
			+ " between ? and ? order by enddate";

	private static final String GET_FOR_LEASE_VIEW = 
			selectFactor + " FROM RENT_ORD where slocno=? "
			+" and (status = 'unpaid' or status = 'unoccupied' or status = 'noshow')";
	
	private static final String GET_FOR_RETURN_VIEW = 
			selectFactor + " FROM RENT_ORD where rlocno=? "
			+" and (status = 'noreturn' or status = 'overtime')";
	

	@Override
	public void insert(RentOrdVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			/*
			 * (沒有rentno) 下面計15個attribute: memno, motno, slocno, rlocno,
			 * milstart, milend, filldate, "+
			 * "startdate, enddate, returndate, fine, total, rank, status, note"
			 * +
			 */

			pstmt.setString(1, roVO.getMemno());
			pstmt.setString(2, roVO.getMotno());
			pstmt.setString(3, roVO.getSlocno());
			pstmt.setString(4, roVO.getRlocno());
			pstmt.setInt(5, roVO.getMilstart());
//			pstmt.setInt(6, roVO.getMilend());
//			pstmt.setTimestamp(7, roVO.getFilldate());
			pstmt.setTimestamp(6, roVO.getStartdate());
			pstmt.setTimestamp(7, roVO.getEnddate());
//			pstmt.setTimestamp(10, roVO.getReturndate());
//			pstmt.setInt(11, roVO.getFine());
//			pstmt.setInt(12, roVO.getTotal());
//			pstmt.setString(13, roVO.getRank());
//			pstmt.setString(14, roVO.getStatus());
			pstmt.setString(8, roVO.getNote());

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
	public void update(RentOrdVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
//			private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
//					+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
//					+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";

			//pstmt.setString(1, roVO.getMemno());
			pstmt.setString(1, roVO.getMotno());
			pstmt.setString(2, roVO.getSlocno());
			pstmt.setString(3, roVO.getRlocno());
			pstmt.setInt(4, roVO.getMilstart());
			pstmt.setInt(5, roVO.getMilend());
			//pstmt.setTimestamp(7, roVO.getFilldate());
			pstmt.setTimestamp(6, roVO.getStartdate());
			pstmt.setTimestamp(7, roVO.getEnddate());
			pstmt.setTimestamp(8, roVO.getReturndate());
			pstmt.setInt(9, roVO.getFine());
			pstmt.setInt(10, roVO.getTotal());
			pstmt.setString(11, roVO.getRank());
			pstmt.setString(12, roVO.getStatus());
			pstmt.setString(13, roVO.getNote());
			pstmt.setString(14, roVO.getRentno());

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
	public void delete(String rentno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, rentno);
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
	public List<RentOrdVO> getAll() {
		List<RentOrdVO> list = new ArrayList<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			/*
			 * 計16個attribute rentno,memno, motno, slocno, rlocno, milstart,
			 * milend, filldate, "+
			 * "startdate, enddate, returndate, fine, total, rank, status, note"
			 * +
			 */

			while (rs.next()) {
				roVO = new RentOrdVO();
				roVO.setRentno(rs.getString("rentno"));
				roVO.setMemno(rs.getString("memno"));
				roVO.setMotno(rs.getString("motno"));
				roVO.setSlocno(rs.getString("slocno"));
				roVO.setRlocno(rs.getString("rlocno"));
				roVO.setMilstart(rs.getInt("milstart"));
				roVO.setMilend(rs.getInt("milend"));
				roVO.setFilldate(rs.getTimestamp("filldate"));
				roVO.setStartdate(rs.getTimestamp("startdate"));
				roVO.setEnddate(rs.getTimestamp("enddate"));
				roVO.setReturndate(rs.getTimestamp("returndate"));
				roVO.setFine(rs.getInt("fine"));
				roVO.setTotal(rs.getInt("total"));
				roVO.setRank(rs.getString("rank"));
				roVO.setStatus(rs.getString("status"));
				roVO.setNote(rs.getString("note"));
				list.add(roVO); // Store the row in the list
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
	public RentOrdVO findByPrimaryKey(String rentno) {

		RentOrdVO roVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, rentno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				roVO = new RentOrdVO();
				roVO.setRentno(rs.getString("rentno"));
				roVO.setMemno(rs.getString("memno"));
				roVO.setMotno(rs.getString("motno"));
				roVO.setSlocno(rs.getString("slocno"));
				roVO.setRlocno(rs.getString("rlocno"));
				roVO.setMilstart(rs.getInt("milstart"));
				roVO.setMilend(rs.getInt("milend"));
				roVO.setFilldate(rs.getTimestamp("filldate"));
				roVO.setStartdate(rs.getTimestamp("startdate"));
				roVO.setEnddate(rs.getTimestamp("enddate"));
				roVO.setReturndate(rs.getTimestamp("returndate"));
				roVO.setFine(rs.getInt("fine"));
				roVO.setTotal(rs.getInt("total"));
				roVO.setRank(rs.getString("rank"));
				roVO.setStatus(rs.getString("status"));
				roVO.setNote(rs.getString("note"));

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
		return roVO;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByStatus(String status) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法

				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByRentLoc(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_START_LOC_NO);
			pstmt.setString(1, slocno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法

				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByReturnLoc(String rlocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_RETURN_LOC_NO);
			pstmt.setString(1, rlocno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByStartDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_STARTTIME_PEROID);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByEndDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BY_ENDTIME_PEROID);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersForLeaseView(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_FOR_LEASE_VIEW);
			pstmt.setString(1, slocno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法
	
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersForReturnView(String rlocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_FOR_RETURN_VIEW);
			pstmt.setString(1, rlocno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				roVO = new RentOrdVO();
				setAttirbute(roVO, rs); // 拉出來寫成一個方法
	
				set.add(roVO); // Store the row in the vector
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

	private void setAttirbute(RentOrdVO roVO, ResultSet rs) {

		try {
			roVO.setMotno(rs.getString("motno"));
			roVO.setRentno(rs.getString("rentno"));
			roVO.setMemno(rs.getString("memno"));
			roVO.setSlocno(rs.getString("slocno"));
			roVO.setRlocno(rs.getString("rlocno"));
			roVO.setMilstart(rs.getInt("milstart"));
			roVO.setMilend(rs.getInt("milend"));
			
//			personVO.setUserDate(java.sql.Timestamp.valueOf
//					(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("USERDATE"))));
			
			
			roVO.setFilldate(rs.getTimestamp("filldate"));
			roVO.setStartdate(rs.getTimestamp("startdate"));
			roVO.setEnddate(rs.getTimestamp("enddate"));
			roVO.setReturndate(rs.getTimestamp("returndate"));
			roVO.setFine(rs.getInt("fine"));
			roVO.setTotal(rs.getInt("total"));
			roVO.setRank(rs.getString("rank"));
			roVO.setStatus(rs.getString("status"));
			roVO.setNote(rs.getString("note"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		RentOrdJDBCDAO dao = new RentOrdJDBCDAO();
		// 新增

		/*
		 * (沒有rentno) 下面計15個attribute memno, motno, slocno, rlocno, milstart,
		 * milend, filldate, "+
		 * "startdate, enddate, returndate, fine, total, rank, status, note"+
		 * 
		 * 
		 */

		
//        final String INSERT_STMTd = "INSERT INTO RENT_ORD"
//		+ " (rentno, memno, motno, slocno, rlocno, milstart, filldate, "
//		+ "startdate, enddate, note"
//		+ ") VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//		+ " ?, ?, ?, ?, ? )";
		
//		  RentOrdVO roVO1 = new RentOrdVO(); 
//		  roVO1.setMemno("memno");
//		  roVO1.setMotno("motno"); 
//		  roVO1.setSlocno("slocno");
//		  roVO1.setRlocno("rlocno"); 
//		  roVO1.setMilstart(111111);
//		  //roVO1.setMilend(222222); //結束才填
//		  //roVO1.setFilldate(java.sql.Timestamp.valueOf("2017-06-02 10:10:10"));
//		  roVO1.setStartdate(java.sql.Timestamp.valueOf("2017-06-05 10:10:10"));		  
//		  roVO1.setEnddate(java.sql.Timestamp.valueOf("2017-06-08 10:10:10"));
//		  //roVO1.setReturndate(java.sql.Timestamp.valueOf("2017-06-09 10:10:10")); 
//		  //roVO1.setFine(500); 
//		  //roVO1.setTotal(2000); 
//		  //roVO1.setRank("1");
//		  //roVO1.setStatus(unpaid);
//		  roVO1.setNote("test"); 
//		  dao.insert(roVO1);
//		  System.out.println("insert ok");
		  
		
//		  RentOrdVO roVO2 = new RentOrdVO(); 
//		  roVO2.setMemno("memno2");
//		  roVO2.setMotno("motno2"); 
//		  roVO2.setSlocno("slocno2");
//		  roVO2.setRlocno("rlocno2"); 
//		  roVO2.setMilstart(111111);
//		  roVO2.setMilend(222222);
//		  //roVO2.setFilldate(java.sql.Timestamp.valueOf("2017-06-03 10:10:10"));
//		  roVO2.setStartdate(java.sql.Timestamp.valueOf("2017-06-06 10:10:10"));
//		  roVO2.setEnddate(java.sql.Timestamp.valueOf("2017-06-09 10:10:10"));
//		  roVO2.setReturndate(java.sql.Timestamp.valueOf("2017-06-10 10:10:10")); 
//		  roVO2.setFine(5000); 
//		  roVO2.setTotal(20000); 
//		  roVO2.setRank("5");
//		  roVO2.setStatus("unoccupied"); 
//		  roVO2.setNote("test2");
//		  roVO2.setRentno("R000030"); 
//		  dao.update(roVO2);
//		  System.out.println("update ok");
		 

		/*
		 * dao.delete("R000014"); System.out.println("delete ok");
		 */

		// 查詢
		// RentOrdVO roVO3 = dao.findByPrimaryKey("R000015");
		// System.out.println(roVO3.getRentno() +",");
		// System.out.println(roVO3.getMemno() +",");
		// System.out.println(roVO3.getMotno() +",");
		// System.out.println(roVO3.getSlocno() +",");
		// System.out.println(roVO3.getRlocno() +",");
		// System.out.println(roVO3.getMilstart() +",");
		// System.out.println(roVO3.getMilend() +",");
		// System.out.println(roVO3.getFilldate() +",");
		// System.out.println(roVO3.getStartdate() +",");
		// System.out.println(roVO3.getEnddate() +",");
		// System.out.println(roVO3.getReturndate() +",");
		// System.out.println(roVO3.getFine() +",");
		// System.out.println(roVO3.getTotal() +",");
		// System.out.println(roVO3.getRank() +",");
		// System.out.println(roVO3.getStatus() +",");
		// System.out.println(roVO3.getNote() +",");
		// System.out.println("---------------------");

		/*
		 * 計16個attribute rentno,memno, motno, slocno, rlocno, milstart, milend,
		 * filldate, "+
		 * "startdate, enddate, returndate, fine, total, rank, status, note"+
		 */

//		List<RentOrdVO> list = dao.getAll();
//		for (RentOrdVO aRO : list) {
//			printMethod(aRO);
//		}

//		Set<RentOrdVO> set1 = dao.getRentalOrdersByRentLoc("TaiChung");
//		for (RentOrdVO aRO : set1) {
//			printMethod(aRO);
//		}
//
//		Set<RentOrdVO> set2 = dao.getRentalOrdersByReturnLoc("KaohsungR");
//		for (RentOrdVO aRO : set2) {
//			printMethod(aRO);
//		}
//
//		Set<RentOrdVO> set3 = dao.getRentalOrdersByStatus("noshow");
//		for (RentOrdVO aRO : set3) {
//			printMethod(aRO);
//		}
//		Set<RentOrdVO> set4 = dao.getRentalOrdersByStartDatePrioid(java.sql.Timestamp.valueOf("2017-06-01 00:00:01"),
//				java.sql.Timestamp.valueOf("2017-06-30 23:59:59"));
//		for (RentOrdVO aRO : set4) {
//			printMethod(aRO);
//		}
//
//		Set<RentOrdVO> set5 = dao.getRentalOrdersByEndDatePrioid(
//				java.sql.Timestamp.valueOf("2017-01-01 00:00:01"),
//				java.sql.Timestamp.valueOf("2017-09-30 23:59:59"));
//		for (RentOrdVO aRO : set5) {
//			printMethod(aRO);
//		}

//		Set<RentOrdVO> set6 = dao.getRentalOrdersForLeaseView("L000002");
//		for (RentOrdVO aRO : set6) {
//			System.out.println("===LeaseView===");
//			printMethod(aRO);
//		}
		
//		Set<RentOrdVO> set7 = dao.getRentalOrdersForReturnView("L000002");
//		for (RentOrdVO aRO : set7) {
//			System.out.println("===ReturnView===");
//			printMethod(aRO);
//		}		
		
	}

	private static void printMethod(RentOrdVO aRO) {
		System.out.println("getRentno:" + aRO.getRentno() + ",");
		System.out.println("getMemno:" + aRO.getMemno() + ",");
		System.out.println("getMotno:" + aRO.getMotno() + ",");
		System.out.println("getSlocno:" + aRO.getSlocno() + ",");
		System.out.println("getRlocno:" + aRO.getRlocno() + ",");		
		System.out.println("getMilstart:" + aRO.getMilstart() + ",");
		System.out.println("getMilend:" + aRO.getMilend() + ",");
		System.out.println("getFilldate:" + aRO.getFilldate() + ",");
		System.out.println("getStartdate:" + aRO.getStartdate() + ",");
		System.out.println("getEnddate:" + aRO.getEnddate() + ",");
		System.out.println("getReturndate:" + aRO.getReturndate() + ",");
		System.out.println("getFine:" + aRO.getFine() + ",");
		System.out.println("getTotal:" + aRO.getTotal() + ",");
		System.out.println("getRank:" + aRO.getRank() + ",");
		System.out.println("getStatus:" + aRO.getStatus() + ",");
		System.out.println("getNote:" + aRO.getNote() + ",");
		System.out.println();
		System.out.println();
	}
}
