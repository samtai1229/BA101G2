package com.motor_model.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class MotorModelJNDIDAO implements MotorModelDAO_interface {
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MOTOR_MODEL"
			+ " (modtype, brand, displacement, name, renprice, saleprice, motpic"
			+ ") VALUES ('MM'||LPAD(TO_CHAR(modtype_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?,?)";

	private static final String UPDATE = "UPDATE MOTOR_MODEL set brand=?,"
			+ " displacement=?, name=?, renprice=?, saleprice=?, motpic=? where modtype = ?";

	private static final String DELETE = "DELETE FROM MOTOR_MODEL where modtype = ?";

	private static final String GET_ONE = "SELECT modtype, brand, displacement,"
			+ "  name, renprice, saleprice, motpic FROM MOTOR_MODEL where modtype = ?";

	private static final String GET_ALL = "SELECT modtype, brand, displacement,"
			+ "  name, renprice, saleprice, motpic FROM MOTOR_MODEL";

	@Override
	public void insert(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			pstmt.setBytes(6, mmVO.getMotpic());

			pstmt.executeUpdate();

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
	public void update(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			pstmt.setBytes(6, mmVO.getMotpic());
			pstmt.setString(7, mmVO.getModtype());

			pstmt.executeUpdate();

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
	public void delete(String modtype) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, modtype);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public MotorModelVO findByPrimaryKey(String modtype) {

		MotorModelVO mmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			// modtype, brand, displacement, name, renprice, saleprice, motpic

			pstmt.setString(1, modtype);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs);
			}

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
		return mmVO;
	}

	@Override
	public List<MotorModelVO> getAll() {
		List<MotorModelVO> list = new ArrayList<MotorModelVO>();
		MotorModelVO mmVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs); // 拉出來寫成一個方法
				list.add(mmVO); // Store the row in the list

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	private void setAttribute(MotorModelVO mmVO, ResultSet rs) {
		try {
			mmVO.setModtype(rs.getString("modtype"));
			mmVO.setBrand(rs.getString("brand"));
			mmVO.setDisplacement(rs.getInt("displacement"));
			mmVO.setName(rs.getString("name"));
			mmVO.setRenprice(rs.getInt("renprice"));
			mmVO.setSaleprice(rs.getInt("saleprice"));
			mmVO.setMotpic(rs.getBytes("motpic"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readPicture(byte[] motpic) {
		try {
			// 不給放在根目錄，一定要有資料夾???
			FileOutputStream fos = new FileOutputStream("C://temp//0001.gif");
			fos.write(motpic);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
