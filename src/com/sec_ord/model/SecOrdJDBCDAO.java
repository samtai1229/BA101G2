package com.sec_ord.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SecOrdJDBCDAO implements SecOrdDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO SEC_ORD (sono,memno,motno,sodate,status) VALUES ('S'||lpad(to_char(sono_seq.NEXTVAL),6,'0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD order by sono";
	private static final String GET_ONE_STMT_SONO = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where sono = ?";
	private static final String GET_ONE_STMT_MEMNO = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where memno = ?";
	private static final String DELETE = "DELETE FROM SEC_ORD where sono = ?";
	private static final String UPDATE = "UPDATE SEC_ORD set memno=?, motno=?, sodate=?, status=? where sono = ?";

	private static final String GET_ALL_STMT_ByStatus = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where status= ? order by sodate desc";
	public static void main(String[] args) {
		SecOrdJDBCDAO dao = new SecOrdJDBCDAO();
		// 新增
//		SecOrdVO vo1 = new SecOrdVO();
//		vo1.setMemNo("MEM000001");
//		vo1.setMotorNo("M000005");
//	
//		vo1.setSecondOrderDate(new Timestamp(System.currentTimeMillis()));
//		vo1.setSecondStatus("paid");
//		dao.insert(vo1);
//		
		//修改
//		SecOrdVO vo2 = new SecOrdVO();
//		vo2.setSecondNo("SO000004");
//		vo2.setMemNo("MEM000001");
//		vo2.setMotorNo("M000005");
//		vo2.setSecondOrderDate(new Timestamp(System.currentTimeMillis()));
//		vo2.setSecondStatus("unpaid");
//		dao.update(vo2);
		
		//刪除
//        dao.delete("SO000010");		
		
		// 查一個
		
//		SecOrdVO one = dao.findByPrimaryKey("SO000008");
//		System.out.print(one.getMemNo()+",");

		
		
		
		//查全部

		List<SecOrdVO> orders = dao.getAll();
		for(SecOrdVO order : orders)
		{
			System.out.print(order.getSono()+",");
			System.out.print(order.getMemno()+",");
			System.out.print(order.getMotorno()+",");
			System.out.print(order.getBuildtime()+",");
			System.out.println(order.getStatus());
		}
		


	}

	@Override
	public void insert(SecOrdVO secordVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, secordVO.getMemno());
			pstmt.setTimestamp(3, secordVO.getBuildtime());
			pstmt.setString(2, secordVO.getMotorno());
			pstmt.setString(4, secordVO.getStatus());

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
	public void update(SecOrdVO secordVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, secordVO.getMemno());
			pstmt.setTimestamp(3, secordVO.getBuildtime());
			pstmt.setString(2, secordVO.getMotorno());
			pstmt.setString(4, secordVO.getStatus());
			pstmt.setString(5, secordVO.getSono());
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
	public void delete(String sono) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, sono);

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
	public SecOrdVO findBySono(String sono) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SecOrdVO  obj = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT_SONO);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1,sono);
			pstmt.executeUpdate();
			
			rs = pstmt.getResultSet();
			while(rs.next())
			{
				 obj = new SecOrdVO();
				//sodate,status
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
			    obj.setBuildtime(rs.getTimestamp("sodate"));
			    obj.setStatus(rs.getString("status"));

			}

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

		
		return obj;
	}

	@Override
	public List<SecOrdVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SecOrdVO> list = new ArrayList();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.executeUpdate();
			
			rs = pstmt.getResultSet();
			while(rs.next())
			{
				SecOrdVO  obj  = new SecOrdVO();
				//sodate,status
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
			    obj.setBuildtime(rs.getTimestamp("sodate"));
			    obj.setStatus(rs.getString("status"));
			    list.add(obj);
               
			}

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

		
		return list;
	}

	@Override
	public SecOrdVO findByMemno(String memno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SecOrdVO obj = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT_MEMNO);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, memno);
			pstmt.executeUpdate();

			rs = pstmt.getResultSet();
			while (rs.next()) {
				obj = new SecOrdVO();
				// sodate,status
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
				obj.setBuildtime(rs.getTimestamp("sodate"));
				obj.setStatus(rs.getString("status"));

			}

			// Handle any driver errors
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}  finally {
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

		return obj;
	}

	@Override
	public List<SecOrdVO> getAll(String status) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SecOrdVO> list = new ArrayList();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT_ByStatus);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.executeUpdate();
			rs = pstmt.getResultSet();
			while(rs.next())
			{
				SecOrdVO  obj  = new SecOrdVO();
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
			    obj.setBuildtime(rs.getTimestamp("sodate"));
			    obj.setStatus(rs.getString("status"));
			    list.add(obj);
               
			}

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

		
		return list;

	}

}
