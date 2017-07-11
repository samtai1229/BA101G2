package com.adminis.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminisJDBCDAO implements AdminisDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO adminis(admno,locno,authno,name,acc,pw) VALUES('A'||lpad(to_char(admno_seq.NEXTVAL),3,'0'),?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT admno,locno,authno,name,acc,pw FROM adminis order by admno";
	private static final String GET_ONE_STMT = "SELECT admno,locno,authno,name,acc,pw FROM adminis where admno = ?";
	private static final String DELETE = "DELETE FROM adminis where admno = ?";
	private static final String UPDATE = "UPDATE adminis set locno=?, authno=?, name=?, acc=?, pw=? where admno = ?";
	private static final String GET_BYACCOUNT = "SELECT * FROM adminis where acc=?";
	@Override
	public void insert(AdminisVO adminisvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminisvo.getLocno());
			pstmt.setString(2, adminisvo.getAuthno());
			pstmt.setString(3, adminisvo.getName());
			pstmt.setString(4, adminisvo.getAcc());
			pstmt.setString(5, adminisvo.getPw());
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
	public void update(AdminisVO adminisvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, adminisvo.getLocno());
			pstmt.setString(2, adminisvo.getAuthno());
			pstmt.setString(3, adminisvo.getName());
			pstmt.setString(4, adminisvo.getAcc());
			pstmt.setString(5, adminisvo.getPw());
			pstmt.setString(6, adminisvo.getAdmno());
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
	public void delete(String admno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, admno);

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
	public AdminisVO findByPrimaryKey(String admno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminisVO adminisvo = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, admno);
			rs = pstmt.executeQuery();
			rs.next();
			adminisvo = new AdminisVO();
			adminisvo.setAdmno(rs.getString("admno"));
			adminisvo.setLocno(rs.getString("locno"));
			adminisvo.setAuthno(rs.getString("authno"));
			adminisvo.setName(rs.getString("name"));
			adminisvo.setAcc(rs.getString("acc"));
			adminisvo.setPw(rs.getString("pw"));

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
		return adminisvo;
	}

	@Override
	public List<AdminisVO> getAll() {
		List<AdminisVO> list = new ArrayList<AdminisVO>();
		AdminisVO adminisvo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminisvo = new AdminisVO();
				adminisvo.setAdmno(rs.getString("admno"));
				adminisvo.setLocno(rs.getString("locno"));
				adminisvo.setAuthno(rs.getString("authno"));
				adminisvo.setName(rs.getString("name"));
				adminisvo.setAcc(rs.getString("acc"));
				adminisvo.setPw(rs.getString("pw"));
				list.add(adminisvo);
			}
		} catch (ClassNotFoundException e) {
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
		AdminisJDBCDAO adm = new AdminisJDBCDAO();
		AdminisVO adminisvo = new AdminisVO();
		// adminisvo.setAdmno("A000003");
		// adminisvo.setLocno("12345");
		// adminisvo.setAuthno("345345");
		// adminisvo.setName("Mary");
		// adminisvo.setAcc("qwfasf");
		// adminisvo.setPw("ewrw");
		// adm.update(adminisvo);
		// System.out.println("ok");
		
//		 adminisvo.setLocno("231");
//		 adminisvo.setAuthno("132");
//		 adminisvo.setName("Tom");
//		 adminisvo.setAcc("234567");
//		 adminisvo.setPw("asd");
//		 adm.insert(adminisvo);
//		 System.out.println("ok");
//		
		// adm.delete("A000001");
		// System.out.println("ok");
		AdminisVO adminisvo2 = adm.findByPrimaryKey("A000003");
		System.out.println(adminisvo2.getAdmno() + ",");
		System.out.println(adminisvo2.getLocno() + ",");
		System.out.println(adminisvo2.getAuthno() + ",");
		System.out.println(adminisvo2.getName() + ",");
		System.out.println(adminisvo2.getAcc() + ",");
		System.out.println(adminisvo2.getPw() + ",");
		System.out.println("OK");
		System.out.println("====================================================");
		
		List<AdminisVO> list = adm.getAll();
		for (AdminisVO all : list) {
			printMethod(all);
		}

	}

	private static void printMethod(AdminisVO adminisvo2) {

		System.out.println(adminisvo2.getAdmno() + ",");
		System.out.println(adminisvo2.getLocno() + ",");
		System.out.println(adminisvo2.getAuthno() + ",");
		System.out.println(adminisvo2.getName() + ",");
		System.out.println(adminisvo2.getAcc() + ",");
		System.out.println(adminisvo2.getPw() + ",");
		System.out.println("OK");
		System.out.println();

	}

	@Override
	public AdminisVO findByAccount(String acc) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminisVO adminisvo = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_BYACCOUNT);

			pstmt.setString(1, acc);
			rs = pstmt.executeQuery();
			rs.next();
			adminisvo = new AdminisVO();
			adminisvo.setAdmno(rs.getString("admno"));
			adminisvo.setLocno(rs.getString("locno"));
			adminisvo.setAuthno(rs.getString("authno"));
			adminisvo.setName(rs.getString("name"));
			adminisvo.setAcc(rs.getString("acc"));
			adminisvo.setPw(rs.getString("pw"));

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
		return adminisvo;
	}

}
