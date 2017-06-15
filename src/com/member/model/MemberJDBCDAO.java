package com.member.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



public class MemberJDBCDAO implements MemberDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";
	private static final String INSERT_STMT = 
		"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,credate,status) "
		+ "VALUES ('M'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE = 
		"UPDATE member set memno= ? ,memname= ? ,sex= ? ,birth= ? ,mail= ? ,phone= ? ,addr= ? ,acc= ? ,pwd= ? ,idcard1= ? ,idcard2= ? ,license= ? ,credate= ? ,status= ? ";
	
	private static final String DELETE = "DELETE FROM MEMBER where memno = ?";
	
	private static final String selectfactor ="SELECT memno,memname,sex,to_char(birth,'yyyy-mm-dd') birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,to_char(credate,'yyyy-mm-dd') credate,status ";
	
	private static final String GET_ALL_STMT = selectfactor	+ "FROM MEMBER order by memno";
	
	private static final String GET_ONE_STMT = selectfactor	+ "FROM MEMBER where memno = ?";
	
	
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
			pstmt.setString(3, memberVO.getMail());
			pstmt.setInt(4, memberVO.getPhone());
			pstmt.setString(5, memberVO.getAddr());
			pstmt.setString(6, memberVO.getAcc());
			pstmt.setString(7, memberVO.getPwd());
			pstmt.setBytes(8, memberVO.getIdcard1());
			pstmt.setBytes(9, memberVO.getIdcard2());
			pstmt.setBytes(10, memberVO.getLicense());
			pstmt.setTimestamp(11, memberVO.getBirth());
			pstmt.setString(12, memberVO.getStatus());

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
//			ResultSet rs = pstmt.executeQuery();

			pstmt.setString(1, memberVO.getMemname());
			pstmt.setString(2, memberVO.getSex());
//			memberVO.setShowbirth(new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("birth")));
			pstmt.setTimestamp(3, memberVO.getBirth());
			pstmt.setString(4, memberVO.getMail());
			pstmt.setInt(5, memberVO.getPhone());
			pstmt.setString(6, memberVO.getAddr());
			pstmt.setString(7, memberVO.getAcc());
			pstmt.setString(8, memberVO.getPwd());
			pstmt.setBytes(9, memberVO.getIdcard1());
			pstmt.setBytes(10, memberVO.getIdcard2());
			pstmt.setBytes(11, memberVO.getLicense());
			pstmt.setTimestamp(12, memberVO.getCredate());
			pstmt.setString(13, memberVO.getStatus());
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
				// empVo �]�٬� Domain objects
				memberVO = new MemberVO();
				memberVO.setMemno(rs.getString("memno"));
				memberVO.setMemname(rs.getString("memname"));
				memberVO.setSex(rs.getString("sex"));
				memberVO.setBirth(rs.getTimestamp("birth"));
				memberVO.setMail(rs.getString("mail"));
				memberVO.setPhone(rs.getInt("phone"));
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
// empVO �]�٬� Domain objects
/*	String memno,String memname,String sex,Timestamp birth,String mail,Integer phone,
	String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,
	Timestamp credate,String status,*/
				empVO = new MemberVO();
				empVO.setMemno(rs.getString("memno"));
				empVO.setMemname(rs.getString("memname"));
				empVO.setSex(rs.getString("sex"));
				empVO.setBirth(rs.getTimestamp("birth"));
				empVO.setMail(rs.getString("mail"));
				empVO.setPhone(rs.getInt("phone"));
				empVO.setAddr(rs.getString("addr"));
				empVO.setAcc(rs.getString("acc"));
				empVO.setPwd(rs.getString("pwd"));
				empVO.setIdcard1(rs.getBytes("idcard1"));
				empVO.setIdcard2(rs.getBytes("idcard2"));
				empVO.setCredate(rs.getTimestamp("credate"));
				empVO.setLicense(rs.getBytes("license"));
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

}
