package com.member.model;

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



public class MemberDAO implements MemberDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = 
		"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,status) "
		+ "VALUES ('MEM'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'uncompleted')";
	 
/*"INSERT INTO MEMBER (memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,credate,status) "
+ "VALUES ('M'||LPAD(TO_CHAR(memno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";*/
private static final String UPDATE = "UPDATE MEMBER set memname = ?, sex = ?, birth = ?, mail = ?, phone = ?, addr = ?,"
			+ " acc = ? , pwd = ?,idcard1=? ,idcard2=? ,license=? , status = ? , credate =? where memno = ?";

	
	private static final String DELETE = "DELETE FROM MEMBER where memno = ?";
	
	private static final String selectfactor ="SELECT memno,memname,sex,birth,mail,phone,addr,acc,pwd,idcard1,idcard2,license,credate,status ";
	
	private static final String GET_ALL_STMT = selectfactor	+ "FROM MEMBER order by DECODE(status,'verifing',1), memno";
	private static final String GET_ONE_STMT_BY_ID = selectfactor	+ "FROM MEMBER where memname = ?";
	private static final String GET_ONE_STMT = selectfactor	+ "FROM MEMBER where memno = ?";
	private static final String GET_ONE_STMT_BY_ACC_PWD = selectfactor	+ "FROM MEMBER where acc = ? AND pwd = ?";
	private static final String GET_ONE_STMT_BY_ACC = selectfactor	+ "FROM MEMBER where acc = ?";

	@Override
		public void updateStatus(String memno, String status) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement("UPDATE MEMBER set status = ? where memno = ?");
				pstmt.setString(1, status);
				pstmt.setString(2, memno);
				pstmt.executeUpdate();
	
			} catch (SQLException se) {
					se.printStackTrace(System.err);
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



	/*	String memno,String memname,String sex,Timestamp birth,String mail,String phone,
		String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,
		Timestamp credate,String status,*/
	
	
	
	@Override
	public MemberVO findByPrimaryKeyById(String memid) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_ID);

			pstmt.setString(1, memid);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
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
	public void insert(MemberVO memberVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

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
			
			pstmt.executeUpdate();
			
			}catch (SQLException se) {
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			
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
			pstmt.setString(12, memberVO.getStatus());
			pstmt.setTimestamp(13, memberVO.getCredate());
			pstmt.setString(14, memberVO.getMemno());
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
	public void delete(String memno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, memno);

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
	public MemberVO findByPrimaryKeyByAccAndPwd(String acc,String pwd) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_ACC_PWD);

			pstmt.setString(1, acc);
			pstmt.setString(2,pwd);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
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
	public MemberVO findByPrimaryKey(String memno) {
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, memno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
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
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				MemberVO memberVO = new MemberVO();
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
				memberVO.setCredate(rs.getTimestamp("credate"));
				memberVO.setStatus(rs.getString("status"));
				list.add(memberVO); // Store the row in the list
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
//	public static void main(String[] args){
//		
//	}
//	// 使用InputStream資料流方式
//		public static InputStream getPictureStream(String path) throws IOException {
//			File file = new File(path);
//			FileInputStream fis = new FileInputStream(file);
//			return fis;
//		}
//
//		// 使用byte[]方式
//		public static byte[] getPictureByteArray(String path) throws IOException {
//			File file = new File(path);
//			FileInputStream fis = new FileInputStream(file);
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			byte[] buffer = new byte[8192];
//			int i;
//			while ((i = fis.read(buffer)) != -1) {
//				baos.write(buffer, 0, i);
//			}
//			baos.close();
//			fis.close();
//
//			return baos.toByteArray();
//		}

	@Override
	public MemberVO findByAcc(String acc) {
		
		MemberVO memberVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_BY_ACC);
			pstmt.setString(1, acc);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// memberVO 也稱為 Domain objects
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

	
}
