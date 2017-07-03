package com.rent_ord.model;
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

import com.equipment.model.EquipmentVO;

public class EquipmentForRentOrdDAO implements EquipmentForRentOrdDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String GET_ALL_STMT = "SELECT * FROM EQUIPMENT order by EMTNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EQUIPMENT where EMTNO = ?";
	private static final String GET_LEASABLE_EQUIPs_BY_ECNO = 
			"SELECT * FROM EQUIPMENT where ecno = ? and status = 'leasable' ";

	@Override
	public List<EquipmentVO> getLeasableEquipsByEcno(String ecno) {

		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO equipmentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_LEASABLE_EQUIPs_BY_ECNO);

			pstmt.setString(1, ecno);

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
				
				list.add(equipmentVO);
			}

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

	@Override
	public EquipmentVO findByPrimaryKey(String emtno) {

		EquipmentVO equipmentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
}
