package com.location.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.member.model.MemberVO;

public class LocationJDBCDAO implements LocationDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "BA101";
	String passwd = "BA101";
//	locno,locname,tel,addr,pic,lon,lat,status
	private static final String INSERT_STMT = 
			"INSERT INTO member (locno,locname,tel,addr,pic,lon,lat,status) VALUES (location_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = 
			"UPDATE location set locno= ? ,locname= ? ,tel= ? ,addr= ? ,pic= ? ,lon= ? ,lat= ? ,status= ? ";
	
	private static final String DELETE = 
			"DELETE FROM location where locno = ?";
	
	private static final String selectfactor = "SELECT locno,locname,tel,addr,pic,lon,lat,status ";
	
	private static final String GET_ALL_STMT = selectfactor	+ " FROM location order by locno";
	
	private static final String GET_ONE_STMT = selectfactor	+ " FROM location where locno = ?";
	
	
	@Override
	public void insert(LocationVO locationDAO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, locationDAO.getLocname());
			pstmt.setInt(2, locationDAO.getTel());
			pstmt.setString(3, locationDAO.getAddr());
			pstmt.setBytes(4, locationDAO.getPic());
			pstmt.setFloat(5, locationDAO.getLon());
			pstmt.setFloat(6, locationDAO.getLat());
			pstmt.setString(7, locationDAO.getStatus());

			pstmt.executeUpdate();
			}catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
	public void update(LocationVO locationDAO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
//			ResultSet rs = pstmt.executeQuery();

			pstmt.setString(1, locationDAO.getLocname());
			pstmt.setInt(2, locationDAO.getTel());
//			memberVO.setShowbirth(new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("birth")));
			pstmt.setString(3, locationDAO.getAddr());
			pstmt.setBytes(4, locationDAO.getPic());
			pstmt.setFloat(5, locationDAO.getLon());
			pstmt.setFloat(6, locationDAO.getLat());
			pstmt.setString(7, locationDAO.getStatus());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(String locno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, locno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public LocationVO findByPrimaryKey(String locno) {
		LocationVO locationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, locno);

			rs = pstmt.executeQuery();

/*	String locno,String locname,Integer tel,String addr,byte[] pic,float lon,
	float lat,String status,*/
			while (rs.next()) {
				// empVo ¤]ºÙ¬° Domain objects
				locationVO = new LocationVO();
				locationVO.setLocno(rs.getString("locno"));
				locationVO.setLocname(rs.getString("locname"));
				locationVO.setTel(rs.getInt("tel"));
				locationVO.setAddr(rs.getString("addr"));
				locationVO.setPic(rs.getBytes("pic"));
				locationVO.setLon(rs.getFloat("lon"));
				locationVO.setLat(rs.getFloat("lat"));
				locationVO.setStatus(rs.getString("status"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
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
		return locationVO;
	}
	@Override
	public List<LocationVO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
