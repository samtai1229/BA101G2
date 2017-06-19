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
	private static final String INSERT_STMT = "INSERT INTO SEC_ORD (sono,memno,motno,sodate,status) VALUES ('S'||lpad(to_char(sono_seq.NEXTVAL),6,'0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD order by sono";
	private static final String GET_ONE_STMT = "SELECT sono,memno,motno,sodate,status FROM SEC_ORD where sono = ?";
	private static final String DELETE = "DELETE FROM SEC_ORD where sono = ?";
	private static final String UPDATE = "UPDATE SEC_ORD set memno=?, motno=?, sodate=?, status=? where sono = ?";

	public static void main(String[] args) {
		SecOrdJDBCDAO dao = new SecOrdJDBCDAO();
		// 新增
		// SecOrdVO vo1 = new SecOrdVO();
		// vo1.setMemNo("MEM000001");
		// vo1.setMotorNo("M000005");
		//
		// vo1.setSecondOrderDate(new Timestamp(System.currentTimeMillis()));
		// vo1.setSecondStatus("paid");
		// dao.insert(vo1);
		//
		// 修改
		// SecOrdVO vo2 = new SecOrdVO();
		// vo2.setSecondNo("SO000004");
		// vo2.setMemNo("MEM000001");
		// vo2.setMotorNo("M000005");
		// vo2.setSecondOrderDate(new Timestamp(System.currentTimeMillis()));
		// vo2.setSecondStatus("unpaid");
		// dao.update(vo2);

		// 刪除
		// dao.delete("SO000010");

		// 查一個

		// SecOrdVO one = dao.findByPrimaryKey("SO000008");
		// System.out.print(one.getMemNo()+",");

		// 查全部

		List<SecOrdVO> orders = dao.getAll();
		for (SecOrdVO order : orders) {
			System.out.print(order.getSecondNo() + ",");
			System.out.print(order.getMemNo() + ",");
			System.out.print(order.getMotorNo() + ",");
			System.out.print(order.getSecondOrderDate() + ",");
			System.out.println(order.getSecondStatus());
		}

	}

	@Override
	public void insert(SecOrdVO secordVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, secordVO.getMemNo());
			pstmt.setTimestamp(3, secordVO.getSecondOrderDate());
			pstmt.setString(2, secordVO.getMotorNo());
			pstmt.setString(4, secordVO.getSecondStatus());

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
			pstmt.setString(1, secordVO.getMemNo());
			pstmt.setTimestamp(3, secordVO.getSecondOrderDate());
			pstmt.setString(2, secordVO.getMotorNo());
			pstmt.setString(4, secordVO.getSecondStatus());
			pstmt.setString(5, secordVO.getSecondNo());
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
	public SecOrdVO findByPrimaryKey(String sono) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SecOrdVO obj = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			// sono,memno,motno,sodate,status
			// pstmt.setString(1, secordVO.getSecondNo());
			pstmt.setString(1, sono);
			pstmt.executeUpdate();

			rs = pstmt.getResultSet();
			while (rs.next()) {
				obj = new SecOrdVO();
				// sodate,status
				obj.setSecondNo(rs.getString("sono"));
				obj.setMemNo(rs.getString("memno"));
				obj.setMotorNo(rs.getString("motno"));
				obj.setSecondOrderDate(rs.getTimestamp("sodate"));
				obj.setSecondStatus(rs.getString("status"));

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
				obj.setSecondNo(rs.getString("sono"));
				obj.setMemNo(rs.getString("memno"));
				obj.setMotorNo(rs.getString("motno"));
				obj.setSecondOrderDate(rs.getTimestamp("sodate"));
				obj.setSecondStatus(rs.getString("status"));
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

}
