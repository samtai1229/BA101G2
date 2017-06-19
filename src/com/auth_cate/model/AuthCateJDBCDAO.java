package com.auth_cate.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.adminis.model.AdminisJDBCDAO;
import com.adminis.model.AdminisVO;

public class AuthCateJDBCDAO implements AuthCateDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO auth_cate(authno,descr) VALUES('AC'||lpad(to_char(authno_seq.NEXTVAL),6,'0'), ?)";
	private static final String GET_ALL_STMT = "SELECT authno,descr FROM auth_cate order by authno";
	private static final String GET_ONE_STMT = "SELECT authno,descr FROM auth_cate where authno = ?";
	private static final String DELETE = "DELETE FROM auth_cate where authno = ?";
	private static final String UPDATE = "UPDATE auth_cate set descr=? where authno = ?";

	@Override
	public void insert(AuthCateVO authcatevo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, authcatevo.getDescr());

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
	public void update(AuthCateVO authcatevo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, authcatevo.getDescr());
			pstmt.setString(2, authcatevo.getAuthno());

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
	public void delete(String authno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, authno);

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
	public AuthCateVO findByPrimaryKey(String authno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AuthCateVO authcateVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, authno);
			rs = pstmt.executeQuery();
			rs.next();
			authcateVO = new AuthCateVO();
			authcateVO.setAuthno(rs.getString("authno"));
			authcateVO.setDescr(rs.getString("descr"));

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
		return authcateVO;
	}

	@Override
	public List<AuthCateVO> getAll() {
		List<AuthCateVO> list = new ArrayList<AuthCateVO>();
		AuthCateVO authcateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				authcateVO = new AuthCateVO();
				authcateVO.setAuthno(rs.getString("authno"));
				authcateVO.setDescr(rs.getString("descr"));
				list.add(authcateVO);
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
		AuthCateJDBCDAO aut = new AuthCateJDBCDAO();

		// AuthCateVO authcatevo = new AuthCateVO();
		// authcatevo.setDescr("locadm");
		// aut.insert(authcatevo);
		// authcatevo.setDescr("hqadm");
		// aut.insert(authcatevo);
		// System.out.println("ok");

		// AuthCateVO authcatevo1 = new AuthCateVO();
		// authcatevo1.setAuthno("AC000001");
		// authcatevo1.setDescr("backadm");
		// aut.update(authcatevo1);
		// System.out.println("ok");

		// AuthCateVO authcatevo2 = new AuthCateVO();
		// aut.delete("AC000001");
		// System.out.println("ok");

		AuthCateVO authcatevo3 = aut.findByPrimaryKey("AC000002");
		System.out.println(authcatevo3.getAuthno() + ",");
		System.out.println(authcatevo3.getDescr() + ",");
		System.out.println("OK");
		System.out.println("====================================================");

		List<AuthCateVO> list = aut.getAll();
		for (AuthCateVO all : list) {
			printMethod(all);
		}

	}

	private static void printMethod(AuthCateVO authcatevo3) {

		System.out.println(authcatevo3.getAuthno() + ",");
		System.out.println(authcatevo3.getDescr() + ",");
		System.out.println("OK");
		System.out.println();

	}
}