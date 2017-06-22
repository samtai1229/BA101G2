package com.sec_ord.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class SecOrdDAO implements SecOrdDAO_interface {

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
	private static final String INSERT_STMT = "INSERT INTO SEC_ORD (sono,memno,motno,sodate,status) VALUES ('S'||lpad(to_char(sono_seq.NEXTVAL),6,'0'), ?, ?,sysdate,'unpaid')";
	private static final String GET_ALL_STMT = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD order by sodate desc";
	private static final String GET_ONE_STMT_SONO = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where sono = ?";
	private static final String GET_ONE_STMT_MEMNO = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where memno = ?";
	private static final String DELETE = "DELETE FROM SEC_ORD where sono = ?";
	private static final String UPDATE = "UPDATE SEC_ORD set memno=?, motno=?, sodate=?, status=? where sono = ?";
	private static final String GET_ALL_STMT_ByStatus = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where status= ? order by sodate desc";
	

	@Override
	public void insert(SecOrdVO secordVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, secordVO.getMemno());
			pstmt.setString(2, secordVO.getMotorno());
			pstmt.executeUpdate();

			// Handle any driver errors
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, sono);

			pstmt.executeUpdate();

			// Handle any driver errors
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
		SecOrdVO obj = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_SONO);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, sono);
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
	public List<SecOrdVO> getAll(String status) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SecOrdVO> list = new ArrayList<SecOrdVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_ByStatus);
			pstmt.setString(1, status);
			pstmt.executeUpdate();

			rs = pstmt.getResultSet();
			while (rs.next()) {
				SecOrdVO obj = new SecOrdVO();
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
				obj.setBuildtime(rs.getTimestamp("sodate"));
				obj.setStatus(rs.getString("status"));

				list.add(obj);

			}

			// Handle any driver errors
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
	public List<SecOrdVO> getAll() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SecOrdVO> list = new ArrayList<SecOrdVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.executeUpdate();

			rs = pstmt.getResultSet();
			while (rs.next()) {
				SecOrdVO obj = new SecOrdVO();
				// sodate,status
				obj.setSono(rs.getString("sono"));
				obj.setMemno(rs.getString("memno"));
				obj.setMotorno(rs.getString("motno"));
				obj.setBuildtime(rs.getTimestamp("sodate"));
				obj.setStatus(rs.getString("status"));

				list.add(obj);

			}

			// Handle any driver errors
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
			con = ds.getConnection();
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

}
