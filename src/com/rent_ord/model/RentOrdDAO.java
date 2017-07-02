package com.rent_ord.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.equipment.model.EquipmentVO;

public class RentOrdDAO implements RentOrdDAO_interface {
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

//	private static final String INSERT_STMT = "INSERT INTO RENT_ORD"
//			+ " (rentno, memno, motno, slocno, rlocno, milstart, milend, filldate, "
//			+ "startdate, enddate, returndate, fine, total, rank, status, note"
//			+ ") VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//			+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	//合理版本
    final String INSERT_STMT = "INSERT INTO RENT_ORD"
	+ " (rentno, memno, motno, slocno, rlocno, startdate, enddate, total, status "
	+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
	+ "  ?, ?, ?,?)";

//	private static final String UPDATE = "UPDATE RENT_ORD set memno=?, motno=?,"
//			+ " slocno=?, rlocno=?, milstart=?, milend=?, filldate=?, startdate=?, enddate=?,"
//			+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";
	
    //合理版本: 去掉 filldate, memno
	private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
			+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
			+ " returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";

	private static final String DELETE = "DELETE FROM RENT_ORD where rentno = ?";

	private static final String selectFactor = "SELECT rentno, memno, motno, slocno, rlocno,"
			+ " milstart, milend, to_char(filldate,'yyyy-mm-dd hh:mm:ss') filldate, "
			+ " to_char(startdate,'yyyy-mm-dd hh:mm:ss') startdate, "
			+ " to_char(enddate,'yyyy-mm-dd hh:mm:ss') enddate, "
			+ " to_char(returndate,'yyyy-mm-dd hh:mm:ss') returndate, fine, total," 
			+ " rank, status, note ";	
	
	private static final String GET_ALL = selectFactor 
			+ " FROM RENT_ORD order by rentno desc";

	private static final String GET_ONE = selectFactor 
			+ " FROM RENT_ORD where rentno = ?";

	private static final String GET_BY_START_LOC_NO = selectFactor 
			+ " FROM RENT_ORD where slocno = ?  order by rentno desc";

	private static final String GET_BY_RETURN_LOC_NO = selectFactor 
			+ " FROM RENT_ORD where rlocno = ?  order by rentno desc";

	private static final String GET_BY_STATUS = selectFactor 
			+ " FROM RENT_ORD where status = ?";

	private static final String GET_BY_STARTTIME_PEROID = selectFactor 
			+ " FROM RENT_ORD  where startdate"
			+ " between ? and ? order by startdate";

	private static final String GET_BY_ENDTIME_PEROID = selectFactor 
			+ " From RENT_ORD  where enddate"
			+ " between ? and ? order by enddate";
	
	private static final String GET_BY_MOTNO = selectFactor 
			+ " From RENT_ORD  where motno = ?";
	
	private static final String GET_FOR_LEASE_VIEW = 
			selectFactor + " FROM RENT_ORD where slocno=? "
			+" and (status = 'unpaid' or status = 'unoccupied' or"
			+" status = 'noshow' or status = 'available' or status = 'canceled')"
			+" order by DECODE(status,'noshow',1,'available',2), status, startdate";
	
	private static final String GET_FOR_RETURN_VIEW = 
			selectFactor + " FROM RENT_ORD where rlocno=? "
			+" and (status = 'noreturn' or status = 'overtime')"
			+"order by DECODE(status,'overtime',1,'noreturn',2), enddate";
	
	
	
	private static final String GET_EMTNOs_BY_RENTNO_IN_EMT_LIST= 
			" SELECT emtno FROM EMT_LIST where rentno = ? ";
	
	private static final String GET_EMPVOs_BY_EMTNOs_IN_EQUIPMENT=
			"SELECT emtno, ecno, locno, to_char(purchdate,'yyyy-mm-dd hh:mm:ss') purchdate,"
			+" status, note from EQUIPMENT where emtno = ? ";

	//DIFFER_DATE_CALCULATOR
		private static final String DIFFER_DATE_CALCULATOR = 
			"select to_char(SYSDATE - TO_DATE(to_char(ENDDATE, 'yyyy/mm/dd'),'yyyy/mm/dd')) DIFFDAY "
			+"from rent_ord where rentno = ? ";
	
	//RENT_ORD from-status-changer: for available	
	private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_OCCUPIED = 
			"UPDATE EQUIPMENT set status='occupied' where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_OCCUPIED = 
			"UPDATE MOTOR set status='occupied' where motno = ? ";
	private static final String UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_NORETURN = 
			"UPDATE RENT_ORD set status='noreturn', note = ? where rentno = ? ";
	
	
//RENT_ORD from-status-changer: for noshow
	private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
			"UPDATE EQUIPMENT set status='unleasable' where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
			"UPDATE MOTOR set status='unleasable' where motno = ? ";
	private static final String UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_ABNORMALCLOSED = 
			"UPDATE RENT_ORD set status='abnormalclosed', note = ? where rentno = ? ";
	

	//RENT_ORD from-status-changer: for noreturn/overtime
	//1. 處理租賃單狀態(milend, returndate, fine, rank, 'status', note)
	//2. 車輛狀態(mile, 'status')
	//3. 裝備狀態('status')
	private static final String UPDATE_RENT_ORD_FROM_NORETURN_TO_CLOSED = 
			"UPDATE RENT_ORD set milend = ?, returndate = ?,"
			+" fine = ?, status='closed', rank = ?, note = ? where rentno = ? ";
	private static final String UPDATE_RENT_ORD_FROM_OVERTIME_TO_ABNORMALCLOSED = 
			"UPDATE RENT_ORD set milend = ?, returndate = ?,"
			+" fine = ?, status='abnormalclosed', rank = ?, note = ? where rentno = ? ";
	private static final String UPDATE_EMT_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
			"UPDATE EQUIPMENT set status='unleasable', locno = ? where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
			"UPDATE MOTOR set mile = ?, status='unleasable', locno = ? where motno = ? ";
	
	
	//依使用者輸入的時段來找符合的車輛並回傳車輛VO.
	private static final String  GET_MOTOR_VOs_BY_DATE_RANGE = 
			  "SELECT motno, startdate, enddate FROM RENT_ORD where ( startdate > ? and startdate <? )"
			  +" or  ( enddate > ? and enddate < ? ) order by startdate";
	
	private static final String GET_RENTNO_BY_MEMNO_AND_STARTDATE = 
			"SELECT rentno from RENT_ORD where memno =? and startdate = ?";
	

	
	
	@Override
	public String findRentnoByMemnoAndStartdate(String memno, Timestamp start_time) {
			String rentno ="";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RENTNO_BY_MEMNO_AND_STARTDATE);
			pstmt.setString(1, memno);
			pstmt.setTimestamp(2, start_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rentno = rs.getString("rentno");
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
		return rentno;
	}


	@Override
	public Set<RentOrdVO> getRentalOrdersBymotno(String motno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MOTNO);
			pstmt.setString(1, motno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法

				set.add(roVO); // Store the row in the vector
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
	public List<String> getMotnoInRentOrdByRentalPeriod(Timestamp start_time, Timestamp end_time) {

		List<String> list = new ArrayList<String>();
		String motno = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		System.out.println("start_time in RentOrdDAO : " + start_time);
		System.out.println("end_time in RentOrdDAO : " + end_time);
		
		resetDayMethod(start_time, false); //時分歸零
		resetDayMethod(end_time, true);
		
		System.out.println("start_time reset : " + start_time);
		System.out.println("end_time reset : " + end_time);


		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MOTOR_VOs_BY_DATE_RANGE);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			pstmt.setTimestamp(3, start_time);
			pstmt.setTimestamp(4, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motno = rs.getString("motno");
				rs.getTimestamp("startdate");
				rs.getTimestamp("enddate");
				System.out.print("motno = " + motno);
				System.out.print("  startdate = " + rs.getTimestamp("startdate"));
				System.out.println("  enddate = " + rs.getTimestamp("enddate"));
				
				list.add(motno); // Store the row in the vector
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


	private Timestamp resetDayMethod(Timestamp day, boolean isEndDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		cal.set(Calendar.MILLISECOND, 0);
		if(isEndDay){
			cal.add(Calendar.DATE, 2);//+2天
		}else{
			cal.add(Calendar.DAY_OF_MONTH, -2);//-2天
		}
        	

		day.setTime(cal.getTime().getTime());
		return day;
	}


	@Override
	public void updateRentOrdAfterOvertime(String rentno, Integer milend, Timestamp returndate, Integer fine,
			String rank, String note, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_RENT_ORD_FROM_OVERTIME_TO_ABNORMALCLOSED);

			pstmt.setInt(1, milend);
			pstmt.setTimestamp(2, returndate);
			pstmt.setInt(3, fine);
			pstmt.setString(4, rank);
			pstmt.setString(5, note);
			pstmt.setString(6, rentno);
			
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
	public void updateRentOrdAfterNoreturn(String rentno, Integer milend, Timestamp returndate, Integer fine, String rank,
			String note, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_RENT_ORD_FROM_NORETURN_TO_CLOSED);
			
			pstmt.setInt(1, milend);
			pstmt.setTimestamp(2, returndate);
			pstmt.setInt(3, fine);
			pstmt.setString(4, rank);
			pstmt.setString(5, note);
			pstmt.setString(6, rentno);
			
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


	//overload for noshow
	@Override
	public void updateRentOrdStatusAfterAvailable(String rentno, String note, String action) {
	
		
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_NORETURN);
			
			pstmt.setString(1, note);
			pstmt.setString(2, rentno);
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
	public void updateRentOrdStatusAfterNoshow(String rentno, String note, String action) {
	
		
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_ABNORMALCLOSED);
				
			pstmt.setString(1, note);
			pstmt.setString(2, rentno);
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
	public void updateMotorStatusAfterNoshow(String motno, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_UNLEASABLE);

			pstmt.setString(1, motno);
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
	public void updateMotorAfterReturn(String motno, Integer mile, String rlocno, String action) {

		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
	
			pstmt = con.prepareStatement(UPDATE_MOTOR_STATUS_FROM_OCCUPIED_TO_UNLEASABLE);

			pstmt.setInt(1, mile);
			pstmt.setString(2, rlocno);
			pstmt.setString(3, motno);
			
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
	public void updateMotorStatusAfterAvailable(String motno, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_OCCUPIED);

			pstmt.setString(1, motno);
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
	public void updateEmtsAfterReturn(String emtno, String rlocno, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_EMT_STATUS_FROM_OCCUPIED_TO_UNLEASABLE);
	
			pstmt.setString(1, rlocno);
			pstmt.setString(2, emtno);
			
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
	public void updateEmtsStatusAfterNoshow(String emtno, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_EMT_STATUS_FROM_RESERVE_TO_UNLEASABLE);

			pstmt.setString(1, emtno);
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
	public void updateEmtsStatusAfterAvailable(String emtno, String action) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_EMT_STATUS_FROM_RESERVE_TO_OCCUPIED);

			pstmt.setString(1, emtno);
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
	public Set<EquipmentVO> getEquipmentVOsByRentno(String rentno) {
		System.out.println("RentOrdDAO getEquipmentVOsByRentno in");
		System.out.println("rentno =" + rentno);
		Set<EquipmentVO> set = new LinkedHashSet<EquipmentVO>();

		Connection con = null;		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
				
			con = ds.getConnection();			
			pstmt = con.prepareStatement(GET_EMTNOs_BY_RENTNO_IN_EMT_LIST);
			
			pstmt.setString(1, rentno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String emtno = null;
				EquipmentVO emtVO = null;
				
				Connection con2 = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs2 = null;
				
				emtno = rs.getString("emtno");
				System.out.println("emtno = "+ emtno);
				
				try {
					con2 = ds.getConnection();		
					pstmt2 = con2.prepareStatement(GET_EMPVOs_BY_EMTNOs_IN_EQUIPMENT);				
					pstmt2.setString(1, emtno);
					rs2 = pstmt2.executeQuery();
					
					while(rs2.next()){
						System.out.println("rs2 in with emtno = " + emtno);					
						emtVO = new EquipmentVO();
						emtVO.setEmtno(rs2.getString("emtno"));
						emtVO.setEcno(rs2.getString("ecno"));
						emtVO.setLocno(rs2.getString("locno"));
						emtVO.setPurchdate(rs2.getTimestamp("purchdate"));
						emtVO.setStatus(rs2.getString("status"));
						emtVO.setNote(rs2.getString("note"));
						set.add(emtVO); // Store the row in the list
					}					
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					if (rs2 != null) {
						try {
							rs2.close();
						} catch (SQLException se) {
							se.printStackTrace(System.err);
						}
					}
					if (pstmt2 != null) {
						try {
							pstmt2.close();
						} catch (SQLException se) {
							se.printStackTrace(System.err);
						}
					}
					if (con2 != null) {
						try {
							con2.close();
						} catch (Exception e) {
							e.printStackTrace(System.err);
						}
					}				
				}
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
	public void insert(RentOrdVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			/*
			 * (沒有rentno) 下面計15個attribute memno, motno, slocno, rlocno,
			 * milstart, milend, filldate, "+
			 * "startdate, enddate, returndate, fine, total, rank, status, note"
			 * +
			 */
			
			//合理版本
//		    final String INSERT_STMT = "INSERT INTO RENT_ORD"
//			+ " (rentno, memno, motno, slocno, rlocno, startdate, enddate, total "
//			+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//			+ "  ?, ?, ?)";

			pstmt.setString(1, roVO.getMemno());
			pstmt.setString(2, roVO.getMotno());
			pstmt.setString(3, roVO.getSlocno());
			pstmt.setString(4, roVO.getRlocno());
			pstmt.setTimestamp(5, roVO.getStartdate());
			pstmt.setTimestamp(6, roVO.getEnddate());
			pstmt.setInt(7, roVO.getTotal());
			pstmt.setString(8, roVO.getStatus());
			
//          pstmt.setTimestamp(4, roVO.getFilldate());
//			pstmt.setInt(5, roVO.getMilstart());
//			pstmt.setInt(6, roVO.getMilend());
//			pstmt.setTimestamp(10, roVO.getReturndate());
//			pstmt.setInt(11, roVO.getFine());
//			pstmt.setString(13, roVO.getRank());
			
//			pstmt.setString(7, roVO.getNote());

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
	public void update(RentOrdVO roVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

//			private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
//			+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
//			+ "returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";

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
			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, rentno);
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
	public List<RentOrdVO> getAll() {
		List<RentOrdVO> list = new ArrayList<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, rentno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_STATUS);
			pstmt.setString(1, status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法

				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByRentLoc(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_START_LOC_NO);
			pstmt.setString(1, slocno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法

				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByReturnLoc(String rlocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_RETURN_LOC_NO);
			pstmt.setString(1, rlocno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByStartDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_STARTTIME_PEROID);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersByEndDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_ENDTIME_PEROID);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersForLeaseView(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOR_LEASE_VIEW);
			pstmt.setString(1, slocno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法
	
				set.add(roVO); // Store the row in the vector
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
	public Set<RentOrdVO> getRentalOrdersForReturnView(String rlocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		RentOrdVO roVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOR_RETURN_VIEW);
			pstmt.setString(1, rlocno);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				roVO = new RentOrdVO();
				setAllAttributeMethod(roVO, rs); // 拉出來寫成一個方法
	
				set.add(roVO); // Store the row in the vector
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
	public String differDateCalculator(String rentno) {
	
			String differDate = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
	
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(DIFFER_DATE_CALCULATOR);
	
				pstmt.setString(1, rentno);
	
				rs = pstmt.executeQuery();
	
				while (rs.next()) {
					// deptVO 也稱為 Domain objects
					Integer num = (int) Math.ceil(Double.parseDouble(rs.getString(1)));
					differDate = num.toString();
					System.out.println("rs.getString(1)"+ rs.getString(1));
					System.out.println("differDate : "+differDate);
	
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
			return differDate;
		}


	private void setAllAttributeMethod(RentOrdVO roVO, ResultSet rs) {

		try {
			roVO.setMotno(rs.getString("motno"));
			roVO.setRentno(rs.getString("rentno"));
			roVO.setMemno(rs.getString("memno"));
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
