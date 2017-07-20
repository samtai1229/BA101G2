package com.equipment.model;

import java.util.*;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EquipmentJDBCDAO implements EquipmentDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EQUIPMENT (EMTNO, ECNO, LOCNO, PURCHDATE, STATUS, NOTE) VALUES ('E'||lpad(to_char(ecno_seq.NEXTVAL),6,'0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EQUIPMENT order by EMTNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EQUIPMENT where EMTNO = ?";
	private static final String DELETE = "DELETE FROM EQUIPMENT where EMTNO = ?";
	private static final String UPDATE = "UPDATE EQUIPMENT set ECNO=?, LOCNO=?, PURCHDATE=?, STATUS=?, NOTE=? where EMTNO = ?";

	@Override
	public void insert(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, equipmentVO.getEcno());
			pstmt.setString(2, equipmentVO.getLocno());
			pstmt.setTimestamp(3, equipmentVO.getPurchdate());
			pstmt.setString(4, equipmentVO.getStatus());
			pstmt.setString(5, equipmentVO.getNote());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, equipmentVO.getEcno());
			pstmt.setString(2, equipmentVO.getLocno());
			pstmt.setTimestamp(3, equipmentVO.getPurchdate());
			pstmt.setString(4, equipmentVO.getStatus());
			pstmt.setString(5, equipmentVO.getNote());
			pstmt.setString(6, equipmentVO.getEmtno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String emtno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emtno);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public EquipmentVO findByPrimaryKey(String emtno) {

		EquipmentVO equipmentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emtno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// equipmentVO 也稱為 Domain objects
				equipmentVO = new EquipmentVO();
				equipmentVO.setEmtno(rs.getString("emtno"));
				equipmentVO.setEcno(rs.getString("ecno"));
				equipmentVO.setLocno(rs.getString("locno"));
				equipmentVO.setPurchdate(rs.getTimestamp("purchdate"));
				equipmentVO.setStatus(rs.getString("status"));
				equipmentVO.setNote(rs.getString("note"));
			}

			// Handle any driver errors
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
		return equipmentVO;
	}

	@Override
	public List<EquipmentVO> getAll() {
		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO equipmentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// equipmentVO 也稱為 Domain objects
				equipmentVO = new EquipmentVO();
				equipmentVO.setEmtno(rs.getString("emtno"));
				equipmentVO.setEcno(rs.getString("ecno"));
				equipmentVO.setLocno(rs.getString("locno"));
				equipmentVO.setPurchdate(rs.getTimestamp("purchdate"));
				equipmentVO.setStatus(rs.getString("status"));
				equipmentVO.setNote(rs.getString("note"));
				list.add(equipmentVO); // Store the row in the list
			}

			// Handle any driver errors
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

	public static void main(String[] args) throws IOException {

		EquipmentJDBCDAO dao = new EquipmentJDBCDAO();
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		// 新增
		// EquipmentVO equipmentVO1 = new EquipmentVO();
		// equipmentVO1.setEcno("EC000001");
		// equipmentVO1.setLocno("L000001");
		// equipmentVO1.setPurchdate(Timestamp.valueOf("2017-01-01 11:11:11"));
		// equipmentVO1.setStatus("leasable");
		// equipmentVO1.setNote("none");
		// dao.insert(equipmentVO1);

		// 修改
		// EquipmentVO equipmentVO2 = new EquipmentVO();
		// equipmentVO2.setEcno("EC000004");
		// equipmentVO2.setLocno("L000004");
		// equipmentVO2.setPurchdate(Timestamp.valueOf("2017-01-01 11:11:11"));
		// equipmentVO2.setStatus("leasable");
		// equipmentVO2.setNote("しな人");
		// equipmentVO2.setEmtno("E000027");
		// dao.update(equipmentVO2);

		// 刪除
		// dao.delete("E000027");

		// 查詢
		EquipmentVO equipmentVO3 = dao.findByPrimaryKey("E000001");
		System.out.print(equipmentVO3.getEmtno() + ",");
		System.out.print(equipmentVO3.getEcno() + ",");
		System.out.print(equipmentVO3.getLocno() + ",");
		System.out.print(sdf.format(equipmentVO3.getPurchdate()) + ",");
		System.out.print(equipmentVO3.getStatus() + ",");
		System.out.print(equipmentVO3.getNote());
		System.out.println("---------------------");

		// 查詢
		// List<EquipmentVO> list = dao.getAll();
		// for (EquipmentVO aEquipment : list) {
		// System.out.print(aEquipment.getEmtno() + ",");
		// System.out.print(aEquipment.getEcno() + ",");
		// System.out.print(aEquipment.getLocno() + ",");
		// System.out.print(sdf.format(aEquipment.getPurchdate()) + ",");
		// System.out.print(aEquipment.getStatus() + ",");
		// System.out.print(aEquipment.getNote());
		// System.out.println();
		// }
	}

}
