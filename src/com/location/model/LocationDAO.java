package com.location.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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


public class LocationDAO implements LocationDAO_interface{

private static DataSource ds = null;
static{
	try{
		Context ctx = new InitialContext();
		ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
	}catch (NamingException e){
		e.printStackTrace();
	}
}

private static final String INSERT_STMT = 
"INSERT INTO LOCATION (locno,locname,tel,addr,pic,lon,lat,status) "
+ "VALUES ('L'||LPAD(TO_CHAR(locno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?)";

private static final String UPDATE = 
"UPDATE LOCATION set locname= ? ,tel= ? ,addr= ? ,pic= ? ,lon= ? ,lat= ? ,status= ? where locno = ?";

private static final String DELETE = 
"DELETE FROM LOCATION where locno = ?";

private static final String selectfactor = "SELECT locno,locname,tel,addr,pic,lon,lat,status ";

private static final String GET_ALL_STMT = selectfactor	+ " FROM LOCATION order by locno";

private static final String GET_ONE_STMT = selectfactor	+ " FROM LOCATION where locno = ?";
	
	
	@Override
	public void insert(LocationVO locationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, locationVO.getLocname());
			pstmt.setString(2, locationVO.getTel());
			pstmt.setString(3, locationVO.getAddr());
			pstmt.setBytes(4, locationVO.getPic());
			pstmt.setFloat(5, locationVO.getLon());
			pstmt.setFloat(6, locationVO.getLat());
			pstmt.setString(7, locationVO.getStatus());

			pstmt.executeUpdate();
			}catch (SQLException se) {
				throw new RuntimeException("A database error occured. "+ se.getMessage());
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
	public void update(LocationVO locationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
//			ResultSet rs = pstmt.executeQuery();

			pstmt.setString(1, locationVO.getLocname());
			pstmt.setString(2, locationVO.getTel());
//			memberVO.setShowbirth(new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("birth")));
			pstmt.setString(3, locationVO.getAddr());
			pstmt.setBytes(4, locationVO.getPic());
			pstmt.setFloat(5, locationVO.getLon());
			pstmt.setFloat(6, locationVO.getLat());
			pstmt.setString(7, locationVO.getStatus());
			pstmt.setString(8, locationVO.getLocno());
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
	public void delete(String locno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, locno);

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
	public LocationVO findByPrimaryKey(String locno) {
		LocationVO locationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, locno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				locationVO = new LocationVO();
				locationVO.setLocno(rs.getString("locno"));
				locationVO.setLocname(rs.getString("locname"));
				locationVO.setTel(rs.getString("tel"));
				locationVO.setAddr(rs.getString("addr"));
				locationVO.setPic(rs.getBytes("pic"));
				locationVO.setLon(rs.getFloat("lon"));
				locationVO.setLat(rs.getFloat("lat"));
				locationVO.setStatus(rs.getString("status"));
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
		return locationVO;
	}
	@Override
	public List<LocationVO> getAll() {
		List<LocationVO> list = new ArrayList<LocationVO>();
		LocationVO locationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

/*	String locno,String locname,Integer tel,String addr,byte[] pic,float lon,
	float lat,String status,*/
			while (rs.next()) {
				locationVO = new LocationVO();
				locationVO.setLocno(rs.getString("locno"));
				locationVO.setLocname(rs.getString("locname"));
				locationVO.setTel(rs.getString("tel"));
				locationVO.setAddr(rs.getString("addr"));
				locationVO.setPic(rs.getBytes("pic"));
				locationVO.setLon(rs.getFloat("lon"));
				locationVO.setLat(rs.getFloat("lat"));
				locationVO.setStatus(rs.getString("status"));
				list.add(locationVO); // Store the row in the list
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
	
	public static InputStream getPictureStream(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		return fis;
	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
