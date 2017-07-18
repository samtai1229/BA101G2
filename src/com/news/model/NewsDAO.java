package com.news.model;

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

public class NewsDAO implements NewsDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO NEWS(newsno,admno,cont,pic,title,status) VALUES('N'||lpad(to_char(newsno_seq.NEXTVAL),3,'0'),?,?, ?, ?, ?)";
	private static final String GET_ALL_STMT ="SELECT * FROM news order by newsdate desc";
	private static final String GET_ONE_STMT ="SELECT * FROM news where newsno = ?";
	private static final String DELETE ="DELETE FROM news where newsno = ?";
	private static final String UPDATE ="UPDATE news set admno=?, cont=?, pic=?, title=?, status=? where newsno = ?";
	private static final String GET_ALLBYSTATUS = " SELECT * FROM news where status = 'normal' order by newsdate desc";

	@Override
	public void insert(NewsVO newsvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsvo.getAdmno());
			pstmt.setString(2, newsvo.getCont());
			pstmt.setBytes(3, newsvo.getPic());
			pstmt.setString(4, newsvo.getTitle());
			pstmt.setString(5, newsvo.getStatus());

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
	public void update(NewsVO Newsvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, Newsvo.getAdmno());
			pstmt.setString(2, Newsvo.getCont());
			pstmt.setBytes(3, Newsvo.getPic());
			pstmt.setString(4, Newsvo.getTitle());
			pstmt.setString(5, Newsvo.getStatus());
			pstmt.setString(6, Newsvo.getNewsno());


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
	public void delete(String newsno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, newsno);

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
	public NewsVO findByPrimaryKey(String newsno) {
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getString("newsno"));
				newsVO.setAdmno(rs.getString("admno"));
				newsVO.setNewsdate(rs.getTimestamp("newsdate"));
				newsVO.setCont(rs.getString("cont"));
				newsVO.setPic(rs.getBytes("pic"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setStatus(rs.getString("status"));
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NewsVO newsVO =new NewsVO();
				// empVO �]�٬� Domain objects
				newsVO.setNewsno(rs.getString("newsno"));
				newsVO.setAdmno(rs.getString("admno"));
				newsVO.setNewsdate(rs.getTimestamp("newsdate"));
				newsVO.setCont(rs.getString("cont"));
				newsVO.setPic(rs.getBytes("pic"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setStatus(rs.getString("status"));
				list.add(newsVO); // Store the row in the list
			}

			for(NewsVO aaa:list){
				System.out.println(aaa.getNewsno());
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

	@Override
	public List<NewsVO> getAllnormal() {
		List<NewsVO> listnormal = new ArrayList<NewsVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLBYSTATUS);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NewsVO newsVO =new NewsVO();
				// empVO �]�٬� Domain objects
				newsVO.setNewsno(rs.getString("newsno"));
				newsVO.setAdmno(rs.getString("admno"));
				newsVO.setNewsdate(rs.getTimestamp("newsdate"));
				newsVO.setCont(rs.getString("cont"));
				newsVO.setPic(rs.getBytes("pic"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setStatus(rs.getString("status"));
				listnormal.add(newsVO); // Store the row in the list
			}

			for(NewsVO aaa:listnormal){
				System.out.println(aaa.getNewsno());
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
		return listnormal;
	}
}