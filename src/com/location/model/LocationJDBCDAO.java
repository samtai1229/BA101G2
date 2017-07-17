package com.location.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.model.MemberVO;

public class LocationJDBCDAO implements LocationDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, locationVO.getLocname());
			pstmt.setString(2, locationVO.getTel());
			pstmt.setString(3, locationVO.getAddr());
			pstmt.setBytes(4, locationVO.getPic());
			pstmt.setFloat(5, locationVO.getLon());
			pstmt.setFloat(6, locationVO.getLat());
			pstmt.setString(7, locationVO.getStatus());

			pstmt.executeUpdate();
			}catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
				// Handle any SQL errors
			} catch (SQLException se) {
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		List<LocationVO> list = new ArrayList<LocationVO>();
		LocationVO locationVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return list;
	}
	
	public static void main(String[] arg){
		
		LocationJDBCDAO dao = new LocationJDBCDAO();
		
		// 新增locno,locname,tel,addr,pic,lon,lat,status
//		LocationVO locationVO1 = new LocationVO();
//		locationVO1.setLocname("台北");
//		locationVO1.setTel("0471236467");
//		locationVO1.setAddr("東坡谷");
//		byte[] picture;
//		try {
//			picture = getPictureByteArray("C://Users//Java//Pictures//mouse.png");
//			locationVO1.setPic(picture);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		locationVO1.setLon(122.133f);
//		locationVO1.setLat(226.824f);
//		locationVO1.setStatus("closed");
//		dao.insert(locationVO1);
//		System.out.println("已新增一筆據點資料");
		
		//修改
//		LocationVO location2 = new LocationVO();
//		location2.setLocname("南投");
//		location2.setTel("0492745678");
//		location2.setAddr("邱明山");
//		byte[] picture2;
//		try {
//			picture2 = getPictureByteArray("C://Users//Java//Pictures//cry.png");
//			location2.setPic(picture2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		location2.setLon(39.12756f);
//		location2.setLat(47.13754f);
//		location2.setStatus("closed");
//		location2.setLocno("L000001");
//		dao.update(location2);
//		System.out.println(location2.getLocno()+"已修改。");
//		/*		locno,locname,tel,addr,pic,lon,lat,status*/
//		
//		//刪除
//		dao.delete("L000002");
//		System.out.println("已刪除一筆據點資料");
//		
		//查詢
//		LocationVO location3 = dao.findByPrimaryKey("L000001");
//		System.out.print(location3.getLocno()+",");
//		System.out.print(location3.getLocname()+",");
//		System.out.print(location3.getTel()+",");
//		System.out.print(location3.getAddr()+",");
//		System.out.print(location3.getPic()+",");
//		System.out.print(location3.getLon()+",");
//		System.out.print(location3.getLat()+",");
//		System.out.println(location3.getStatus());
//		System.out.println("------已查詢編號第 "+location3.getLocno()+"筆資料----------------");
		
//		//查詢ALL
		List<LocationVO> list = dao.getAll();
		for(LocationVO locvo : list){
			System.out.print(locvo.getLocno()+",");
			System.out.print(locvo.getLocname()+",");
			System.out.print(locvo.getTel()+",");
			System.out.print(locvo.getAddr()+",");
			System.out.print(locvo.getPic()+",");
			System.out.print(locvo.getLon()+",");
			System.out.print(locvo.getLat()+",");
			System.out.print(locvo.getStatus());
			System.out.println("------------------------------");
		}
		System.out.println("所有資料已查詢，共"+list.size()+"筆資料");
		
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
	@Override
	public List<LocationVO> getAllStatusOpen() {
		// TODO Auto-generated method stub
		return null;
	}
}
