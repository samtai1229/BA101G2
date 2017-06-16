package com.member.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class MemberJDBCDAO implements MemberDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";
	private static final String INSERT_STMT = 
		"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,credate,status) "
		+ "VALUES ('M'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 
/*"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,credate,status) "
+ "VALUES ('M'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";*/
private static final String UPDATE = "UPDATE MEMBER set memname = ?, sex = ?, birth = ?, mail = ?, phone = ?, addr = ?,"
			+ " acc = ? , pwd = ?,idcard1=? ,idcard2=? ,license=? , status = ? where memno = ?";

	
	private static final String DELETE = "DELETE FROM MEMBER where memno = ?";
	
	private static final String selectfactor ="SELECT memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,credate,status ";
	
	private static final String GET_ALL_STMT = selectfactor	+ "FROM MEMBER order by memno";
	
	private static final String GET_ONE_STMT = selectfactor	+ "FROM MEMBER where memno = ?";
	/*	String memno,String memname,String sex,Timestamp birth,String mail,String phone,
		String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,
		Timestamp credate,String status,*/
	
	@Override
	public void insert(MemberVO memberVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memberVO.getMemname());
			pstmt.setString(2, memberVO.getSex());
			pstmt.setTimestamp(3, memberVO.getBirth());
			pstmt.setString(4, memberVO.getMail());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setString(6, memberVO.getAddr());
			pstmt.setString(7, memberVO.getAcc());
			pstmt.setString(8, memberVO.getPwd());
//			pstmt.setBytes(9, memberVO.getIdcard1());
//			pstmt.setBytes(10, memberVO.getIdcard2());
//			pstmt.setBytes(11, memberVO.getLicense());
			pstmt.setTimestamp(9,memberVO.getCredate());
			pstmt.setString(10, memberVO.getStatus());
			
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
	public void update(MemberVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			Blob blob1 = con.createBlob();
			Blob blob2 = con.createBlob();
			Blob blob3 = con.createBlob();
			
/*memname= ? ,sex= ? ,birth= ? ,mail= ? ,phone= ? ,addr= ? ,acc= ? ,pwd= ? ,idcard1= ?
 *  ,idcard2= ? ,license= ? ,status= ?  where memno= ?*/
			pstmt.setString(1, memberVO.getMemname());
			pstmt.setString(2, memberVO.getSex());
			pstmt.setTimestamp(3, memberVO.getBirth());
			pstmt.setString(4, memberVO.getMail());
			pstmt.setString(5, memberVO.getPhone());
			pstmt.setString(6, memberVO.getAddr());
			pstmt.setString(7, memberVO.getAcc());
			pstmt.setString(8, memberVO.getPwd());
			pstmt.setBytes(9, memberVO.getIdcard1());
			pstmt.setBytes(10, memberVO.getIdcard2());
			pstmt.setBytes(11, memberVO.getLicense());
//			blob1.setBytes(1, memberVO.getIdcard1());
//			pstmt.setBlob(9,blob1);
//			blob2.setBytes(1,memberVO.getIdcard2());
//			pstmt.setBlob(10,blob2);
//			blob3.setBytes(1,memberVO.getLicense());
//			pstmt.setBlob(11,blob3);
		
//			pstmt.setTimestamp(12, memberVO.getCredate());
			pstmt.setString(12, memberVO.getStatus());
			pstmt.setString(13, memberVO.getMemno());
			pstmt.executeUpdate();
			// 1. setBlob
//			pstmt.setInt(1, 1);
//			pstmt.setString(2, "拜仁慕尼黑");
//			Blob blob = con.createBlob();
//			byte[] pic2 = getPictureByteArray("items/FC_Bayern.png");
//			blob.setBytes(1, pic2);
//			pstmt.setBlob(3, blob);
//			pstmt.executeUpdate();
			

	

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
	public void delete(String memno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, memno);

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
	public MemberVO findByPrimaryKey(String memno) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, memno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getString("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setSex(rs.getString("sex"));
				memberVO.setBirth(rs.getTimestamp("birth"));
				memberVO.setMail(rs.getString("mail"));
				memberVO.setPhone(rs.getString("phone"));
				memberVO.setAddr(rs.getString("addr"));
				memberVO.setAcc(rs.getString("acc"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setIdcard1(rs.getBytes("idcard1"));
				memberVO.setIdcard2(rs.getBytes("idcard2"));
				memberVO.setLicense(rs.getBytes("license"));
				memberVO.setCredate(rs.getTimestamp("Credate"));
				memberVO.setStatus(rs.getString("status"));
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
		return memberVO;
	}
	@Override
	public List<MemberVO> getAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				empVO = new MemberVO();
				empVO.setMemno(rs.getString("memno"));
				empVO.setMemname(rs.getString("memname"));
				empVO.setSex(rs.getString("sex"));
				empVO.setBirth(rs.getTimestamp("birth"));
				empVO.setMail(rs.getString("mail"));
				empVO.setPhone(rs.getString("phone"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setAcc(rs.getString("acc"));
				empVO.setPwd(rs.getString("pwd"));
				empVO.setIdcard1(rs.getBytes("idcard1"));
				empVO.setIdcard2(rs.getBytes("idcard2"));
				empVO.setLicense(rs.getBytes("license"));
				empVO.setCredate(rs.getTimestamp("credate"));
				empVO.setStatus(rs.getString("status"));
				list.add(empVO); // Store the row in the list
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

	public static void main(String [] arg){
		
		MemberJDBCDAO dao = new MemberJDBCDAO();
		
		//新增
//		MemberVO memberVO1 = new MemberVO();
//		memberVO1.setMemname("Sarah");
//		memberVO1.setSex("girl");
////		memberVO1.setBirth(new Timestamp(System.currentTimeMillis()));
//		memberVO1.setBirth(Timestamp.valueOf("1991-07-15 13:06:10"));
//		memberVO1.setMail("sarah80715@yahoo.com.tw");
//		memberVO1.setPhone("0972086328");
//		memberVO1.setAddr("台中縣豐原鄉OO路XX街123巷45弄6-7號8樓9室");
//		memberVO1.setAcc("sarah800715");
//		memberVO1.setPwd("0972086328");
//		memberVO1.setIdcard1(null);
//		memberVO1.setIdcard2(null);
//		memberVO1.setLicense(null);
//		memberVO1.setCredate(new Timestamp(System.currentTimeMillis()));
//		memberVO1.setStatus("unconfirm");
//		dao.insert(memberVO1);
//		System.out.print("已新增一筆資料!");
		
	/*  2. setBytes
	 	pstmt.setInt(1, 2);
	 	pstmt.setString(2, "巴塞隆納");
	 	byte[] pic = getPictureByteArray("items/FC_Barcelona.png");
	 	pstmt.setBytes(3, pic); */
	// 1. setBlob
//		pstmt.setInt(1, 1);
//		pstmt.setString(2, "拜仁慕尼黑");
//		Blob blob = con.createBlob();
//		byte[] pic2 = getPictureByteArray("items/FC_Bayern.png");
//		blob.setBytes(1, pic2);
//		pstmt.setBlob(3, blob);
//		pstmt.executeUpdate();	
		

		//修改
//		MemberVO memberVO2 = new MemberVO();
//		memberVO2.setMemname("SSSS");
//		memberVO2.setSex("boy");
//		memberVO2.setBirth(Timestamp.valueOf("1999-12-23 13:14:52"));
//		memberVO2.setMail("Apple123@hotmail.com");
//		memberVO2.setPhone("0987654321");
//		memberVO2.setAddr("QOOQLE");
//		memberVO2.setAcc("apple23321");
//		memberVO2.setPwd("Banana321321");
//		
//		byte[] pic,pic2,pic3;
//		try {
//			pic = getPictureByteArray("C://Users//Java//Pictures//cat.png");
//			pic2= getPictureByteArray("C://Users//Java//Pictures//panda.png");
//			pic3= getPictureByteArray("C://Users//Java//Pictures//85.jpg");
//			memberVO2.setIdcard1(pic);
//			memberVO2.setIdcard2(pic2);
//			memberVO2.setLicense(pic3);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		memberVO2.setStatus("confirmed");
//		memberVO2.setMemno("M000003");
//		dao.update(memberVO2);
//		System.out.println(memberVO2.getMemno()+"已修改!");
		
//		//刪除
//		dao.delete("M000001");
//		System.out.println("K.KO!");
//		
//		//查詢
//		MemberVO memberVO3 = dao.findByPrimaryKey("M000003");
//		System.out.print(memberVO3.getMemno()+",");
//		System.out.print(memberVO3.getMemname()+",");
//		System.out.print(memberVO3.getSex()+",");
//		System.out.print(memberVO3.getBirth()+",");
//		System.out.print(memberVO3.getMail()+",");
//		System.out.print(memberVO3.getPhone()+",");
//		System.out.print(memberVO3.getAddr()+",");
//		System.out.print(memberVO3.getAcc()+",");
//		System.out.print(memberVO3.getPwd()+",");
//		System.out.print(memberVO3.getIdcard1()+",");
//		System.out.print(memberVO3.getIdcard2()+",");
//		System.out.print(memberVO3.getLicense()+",");
//		System.out.print(memberVO3.getCredate()+",");
//		System.out.println(memberVO3.getStatus());
//		System.out.println("---------------------");
		
		//查詢ALL
		List<MemberVO> list =dao.getAll();
		for(MemberVO memvo : list){
			System.out.print(memvo.getMemno()+",");
			System.out.print(memvo.getMemname()+",");
			System.out.print(memvo.getSex()+",");
			System.out.print(memvo.getBirth()+",");
			System.out.print(memvo.getMail()+",");
			System.out.println(memvo.getPhone()+",");
			System.out.print(memvo.getAddr()+",");
			System.out.print(memvo.getPwd()+",");
			System.out.print(memvo.getIdcard1()+",");
			System.out.print(memvo.getIdcard2()+",");
			System.out.print(memvo.getLicense()+",");
			System.out.println(memvo.getCredate()+",");
			System.out.println(memvo.getStatus());
			System.out.println("-----------------------------");
		}
		System.out.println("查詢完畢");
	}
	
	
	
	// 使用InputStream資料流方式
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
