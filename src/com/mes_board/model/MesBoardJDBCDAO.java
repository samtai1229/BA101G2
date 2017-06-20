package com.mes_board.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.auth_cate.model.AuthCateVO;
import com.news.model.NewsVO;

public class MesBoardJDBCDAO implements MesBoardDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO mes_board(mesno,memno,cont,pic,status) VALUES('MB'||lpad(to_char(mesno_seq.NEXTVAL),3,'0'),?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM mes_board order by mesno";
	private static final String GET_ONE_STMT = "SELECT * FROM mes_board where mesno = ?";
	private static final String DELETE = "DELETE FROM mes_board where mesno = ?";
	private static final String UPDATE = "UPDATE mes_board set memno=?, cont=?, pic=?, status=? where mesno = ?";

	@Override
	public void insert(MesBoardVO mesboardvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mesboardvo.getMemno());
			pstmt.setString(2, mesboardvo.getCont());
			pstmt.setBytes(3, mesboardvo.getPic());
			pstmt.setString(4, mesboardvo.getStatus());
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
	public void update(MesBoardVO mesboardvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mesboardvo.getMemno());
			pstmt.setString(2, mesboardvo.getCont());
			pstmt.setBytes(3, mesboardvo.getPic());
			pstmt.setString(4, mesboardvo.getStatus());
			pstmt.setString(5, mesboardvo.getMesno());
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
	public void delete(String mesno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, mesno);

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
	public MesBoardVO findByPrimaryKey(String mesno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MesBoardVO mesboardVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mesno);
			rs = pstmt.executeQuery();
			rs.next();
			mesboardVO = new MesBoardVO();
			mesboardVO.setMesno(rs.getString("mesno"));
			mesboardVO.setMemno(rs.getString("memno"));
			mesboardVO.setDate(rs.getTimestamp("date"));
			mesboardVO.setCont(rs.getString("cont"));
			mesboardVO.setPic(rs.getBytes("pic"));
			mesboardVO.setStatus(rs.getString("status"));

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
		return mesboardVO;
	}

	@Override
	public List<MesBoardVO> getAll() {
		List<MesBoardVO> list = new ArrayList<MesBoardVO>();
		MesBoardVO mesboardVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mesboardVO = new MesBoardVO();
				mesboardVO.setMesno(rs.getString("mesno"));
				mesboardVO.setMemno(rs.getString("memno"));
				mesboardVO.setDate(rs.getTimestamp("date"));
				mesboardVO.setCont(rs.getString("cont"));
				mesboardVO.setPic(rs.getBytes("pic"));
				mesboardVO.setStatus(rs.getString("status"));
				list.add(mesboardVO);
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
		// TODO Auto-generated method stub

		MesBoardJDBCDAO mes = new MesBoardJDBCDAO();

		// MesBoardVO mesboardVO = new MesBoardVO();
		// mesboardVO.setMemno("MEM000003");
		// mesboardVO.setDate(new Timestamp(System.currentTimeMillis()));
		// mesboardVO.setCont("遊騎兵隊日籍投手達比修有今天在「德州內戰」出戰太空人隊，他主投7局僅失1分，被打1安打，3保送、4三振，共用103球完成任務，本季第6勝終於到手");
		// mesboardVO.setPic(null);
		// mesboardVO.setStatus("normal");
		// mes.insert(mesboardVO);
		// mesboardVO.setMemno("MEM000002");
		// mesboardVO.setDate(new Timestamp(System.currentTimeMillis()));
		// mesboardVO.setCont("遊騎兵隊出戰太空人隊，他主投7局僅失1分，被打1安打，3保送、4三振，共用103球完成任務，本季第6勝終於到手");
		// mesboardVO.setPic(null);
		// mesboardVO.setStatus("normal");
		// mes.insert(mesboardVO);
		// mesboardVO.setMemno("MEM000001");
		// mesboardVO.setDate(new Timestamp(System.currentTimeMillis()));
		// mesboardVO.setCont("遊騎兵隊日籍投手達比修有今天在「德州內戰」出戰太空人隊");
		// mesboardVO.setPic(null);
		// mesboardVO.setStatus("normal");
		// mes.insert(mesboardVO);
		// System.out.println("ok");

		// MesBoardVO mesboardVO1 = new MesBoardVO();
		// mesboardVO1.setMesno("MB000001");
		// mesboardVO1.setMemno("MEM000002");
		// //mesboardVO1.setDate(new Timestamp(System.currentTimeMillis()));
		// mesboardVO1.setCont("本季第6勝終於到手");
		// mesboardVO1.setPic(null);
		// mesboardVO1.setStatus("normal");
		// mes.update(mesboardVO1);
		// System.out.println("ok");

//		mes.delete("MB000001");
//		System.out.println("ok");

		MesBoardVO mesboardVO2 = mes.findByPrimaryKey("MB000001");
		System.out.println(mesboardVO2.getMesno() + ",");
		System.out.println(mesboardVO2.getMemno() + ",");
		System.out.println(mesboardVO2.getDate() + ",");
		System.out.println(mesboardVO2.getCont() + ",");
		System.out.println(mesboardVO2.getPic() + ",");
		System.out.println(mesboardVO2.getStatus() + ",");
		System.out.println("OK");
		System.out.println("====================================================");

		List<MesBoardVO> list = mes.getAll();
		for (MesBoardVO all : list) {
			printMethod(all);
		}

	}

	private static void printMethod(MesBoardVO mesboardVO2) {

		System.out.println(mesboardVO2.getMesno() + ",");
		System.out.println(mesboardVO2.getMemno() + ",");
		System.out.println(mesboardVO2.getDate() + ",");
		System.out.println(mesboardVO2.getCont() + ",");
		System.out.println(mesboardVO2.getPic() + ",");
		System.out.println(mesboardVO2.getStatus() + ",");
		System.out.println("OK");
		System.out.println();

	}
}