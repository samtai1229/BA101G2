package com.immediate_push.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adminis.model.AdminisVO;
import com.auth_cate.model.AuthCateVO;

public class ImmediatePushJDBCDAO implements ImmediatePushDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO immediate_push(ipno,admno,ipcont,pushno) VALUES('IP'||lpad(to_char(ipno_seq.NEXTVAL),6,'0'), ?, ?, ?)";
	private static final String GET_ALL_STMT ="SELECT ipno,admno,ipcont,pushno FROM immediate_push order by ipno";
	private static final String GET_ONE_STMT ="SELECT ipno,admno,ipcont,pushno FROM immediate_push where ipno = ?";
	private static final String DELETE ="DELETE FROM immediate_push where ipno = ?";
	private static final String UPDATE ="UPDATE immediate_push set admno=?, ipcont=?, pushno=? where ipno = ?";
	@Override
	public void insert(ImmediatePushVO immediatepushvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, immediatepushvo.getAdmno());
			pstmt.setString(2, immediatepushvo.getIpcont());
			pstmt.setString(3, immediatepushvo.getPushno());
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void update(ImmediatePushVO immediatepushvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, immediatepushvo.getAdmno());
			pstmt.setString(2, immediatepushvo.getIpcont());
			pstmt.setString(3, immediatepushvo.getPushno());
			pstmt.setString(4, immediatepushvo.getIpno());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void delete(String ipno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ipno);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ipno);
			rs = pstmt.executeQuery();
			rs.next();
			immediatepushVO = new ImmediatePushVO();
			immediatepushVO.setIpno(rs.getString("ipno"));
			immediatepushVO.setAdmno(rs.getString("admno"));
			immediatepushVO.setIpcont(rs.getString("ipcont"));
			immediatepushVO.setPushno(rs.getString("pushno"));
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				immediatepushVO = new ImmediatePushVO();
				immediatepushVO.setIpno(rs.getString("ipno"));
				immediatepushVO.setAdmno(rs.getString("admno"));
				immediatepushVO.setIpcont(rs.getString("ipcont"));
				immediatepushVO.setPushno(rs.getString("pushno"));
				list.add(immediatepushVO);}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImmediatePushJDBCDAO imm = new ImmediatePushJDBCDAO();
		
//		ImmediatePushVO immediatepushVO = new ImmediatePushVO();
//		immediatepushVO.setAdmno("A000004");
//		immediatepushVO.setIpcont("RSZ 熱租排行TOP8");
//		immediatepushVO.setPushno("排行");
//		imm.insert(immediatepushVO);
//		immediatepushVO.setAdmno("A000004");
//		immediatepushVO.setIpcont("新款二手車上架了");
//		immediatepushVO.setPushno("廣告");
//		imm.insert(immediatepushVO);
//		immediatepushVO.setAdmno("A000004");
//		immediatepushVO.setIpcont("新款二手車上架了");
//		immediatepushVO.setPushno("廣告");
//		imm.insert(immediatepushVO);
//		System.out.println("ok");
		
//		ImmediatePushVO immediatepushVO1 = new ImmediatePushVO();
//		immediatepushVO1.setIpno("IP000002");
//		immediatepushVO1.setAdmno("A000003");
//		immediatepushVO1.setIpcont("本月壽星租車一律88折");
//		immediatepushVO1.setPushno("折扣");
//		imm.update(immediatepushVO1);
//		System.out.println("ok");
		
		ImmediatePushVO immediatepushVO2 = imm.findByPrimaryKey("IP000002");
		System.out.println(immediatepushVO2.getIpno() + ",");
		System.out.println(immediatepushVO2.getAdmno() + ",");
		System.out.println(immediatepushVO2.getIpcont() + ",");
		System.out.println(immediatepushVO2.getPushno() + ",");
		System.out.println("OK");
		System.out.println("====================================================");
		
		List<ImmediatePushVO> list = imm.getAll();
		for (ImmediatePushVO all : list) {
			printMethod(all);
		}

	}

	private static void printMethod(ImmediatePushVO immediatepushVO2) {

		System.out.println(immediatepushVO2.getIpno() + ",");
		System.out.println(immediatepushVO2.getAdmno() + ",");
		System.out.println(immediatepushVO2.getIpcont() + ",");
		System.out.println(immediatepushVO2.getPushno() + ",");
		System.out.println("OK");
		System.out.println();

	}
}