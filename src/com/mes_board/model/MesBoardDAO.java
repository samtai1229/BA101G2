package com.mes_board.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class MesBoardDAO implements MesBoardDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO mes_board(mesno,memno,cont,pic,status) VALUES('MB'||lpad(to_char(mesno_seq.NEXTVAL),3,'0'),?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM mes_board order by mesno";
	private static final String GET_ONE_STMT = "SELECT * FROM mes_board where mesno = ?";
	private static final String DELETE = "DELETE FROM mes_board where mesno = ?";
	private static final String UPDATE = "UPDATE mes_board set memno=?, cont=?, pic=?, status=? where mesno = ?";
	@Override
	public void insert(MesBoardVO mesboardvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mesboardvo.getMemno());
			pstmt.setString(2, mesboardvo.getCont());
			pstmt.setBytes(3, mesboardvo.getPic());
			pstmt.setString(4, mesboardvo.getStatus());
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
	public void update(MesBoardVO mesboardvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			System.out.println("DAOOOOO");

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mesboardvo.getMemno());
			pstmt.setString(2, mesboardvo.getCont());
			pstmt.setBytes(3, mesboardvo.getPic());
			pstmt.setString(4, mesboardvo.getStatus());
			pstmt.setString(5, mesboardvo.getMesno());
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
	public void delete(String mesno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mesno);

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
	public MesBoardVO findByPrimaryKey(String mesno) {
		MesBoardVO mesboardVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mesno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mesboardVO=new MesBoardVO();
				mesboardVO.setMesno(rs.getString("mesno"));
				mesboardVO.setMemno(rs.getString("memno"));
				mesboardVO.setTimestamp(rs.getTimestamp("mesdate"));
				mesboardVO.setCont(rs.getString("cont"));
				mesboardVO.setPic(rs.getBytes("pic"));
				mesboardVO.setStatus(rs.getString("status"));
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
		return mesboardVO;
	}
	@Override
	public List<MesBoardVO> getAll() {
		List<MesBoardVO> list = new ArrayList<MesBoardVO>();
		MesBoardVO mesboardVO =null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				mesboardVO = new MesBoardVO();
				mesboardVO.setMesno(rs.getString("mesno"));
				mesboardVO.setMemno(rs.getString("memno"));
				mesboardVO.setTimestamp(rs.getTimestamp("mesdate"));
				mesboardVO.setCont(rs.getString("cont"));
				mesboardVO.setPic(rs.getBytes("pic"));
				mesboardVO.setStatus(rs.getString("status"));
				list.add(mesboardVO);
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