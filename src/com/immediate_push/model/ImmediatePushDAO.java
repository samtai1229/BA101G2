package com.immediate_push.model;

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



public class ImmediatePushDAO implements ImmediatePushDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO immediate_push(ipno,admno,ipcont,pushno) VALUES('IP'||lpad(to_char(ipno_seq.NEXTVAL),3,'0'), ?, ?, ?)";
	private static final String GET_ALL_STMT ="SELECT ipno,admno,ipcont,pushno FROM immediate_push order by ipno";
	private static final String GET_ONE_STMT ="SELECT ipno,admno,ipcont,pushno FROM immediate_push where ipno = ?";
	private static final String DELETE ="DELETE FROM immediate_push where ipno = ?";
	private static final String UPDATE ="UPDATE immediate_push set admno=?, ipcont=?, pushno=? where ipno = ?";
	@Override
	public void insert(ImmediatePushVO immediatepushvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			
			pstmt.setString(1, immediatepushvo.getAdmno());
			pstmt.setString(2, immediatepushvo.getIpcont());
			pstmt.setString(3, immediatepushvo.getPushno());
			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(ImmediatePushVO immediatepushvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, immediatepushvo.getAdmno());
			pstmt.setString(2, immediatepushvo.getIpcont());
			pstmt.setString(3, immediatepushvo.getPushno());
			pstmt.setString(4, immediatepushvo.getIpno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String ipno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ipno);
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public ImmediatePushVO findByPrimaryKey(String ipno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ImmediatePushVO immediatepushVO = null;


		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ipno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				immediatepushVO = new ImmediatePushVO();
				immediatepushVO.setIpno(rs.getString("ipno"));
				immediatepushVO.setAdmno(rs.getString("asmno"));
				immediatepushVO.setIpcont(rs.getString("ipcont"));
				immediatepushVO.setPushno(rs.getString("pushno"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return immediatepushVO;
	}
	@Override
	public List<ImmediatePushVO> getAll() {
		List<ImmediatePushVO> list = new ArrayList<ImmediatePushVO>();
		ImmediatePushVO immediatepushVO =null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				immediatepushVO = new ImmediatePushVO();
				immediatepushVO.setIpno(rs.getString("ipno"));
				immediatepushVO.setAdmno(rs.getString("asmno"));
				immediatepushVO.setIpcont(rs.getString("ipcont"));
				immediatepushVO.setPushno(rs.getString("pushno"));
				list.add(immediatepushVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return list;
	}
}