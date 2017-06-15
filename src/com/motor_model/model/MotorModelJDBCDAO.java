package com.motor_model.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MotorModelJDBCDAO implements MotorModelDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "servlet";
	String passwd = "123456";

//	private static final String INSERT_STMT = "INSERT INTO MOTOR_MODEL"
//			+ " (modtype, brand, displacement, name, renprice, saleprice, motpic"
//			+ ") VALUES ('MM'||LPAD(TO_CHAR(modtype_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?,?)";
	
	
	private static final String INSERT_STMT = "INSERT INTO MOTOR_MODEL"
			+ " (modtype, brand, displacement, name, renprice, saleprice "
			+ ") VALUES ('MM'||LPAD(TO_CHAR(modtype_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?)";

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			//pstmt.setBytes(6, mmVO.getMotpic());

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
	public void update(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			pstmt.setBytes(6, mmVO.getMotpic());
			pstmt.setString(7, mmVO.getModtype());

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
	public void delete(String modtype) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, modtype);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE);

			// modtype, brand, displacement, name, renprice, saleprice, motpic

			pstmt.setString(1, modtype);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs);

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs); // 拉出來寫成一個方法
				list.add(mmVO); // Store the row in the list

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	public static void main(String[] args) {

		MotorModelJDBCDAO dao = new MotorModelJDBCDAO();

		
//insert without picture
//		for (int i = 1; i < 19; i++) {
//
//			MotorModelVO mmVO1 = new MotorModelVO();
//			mmVO1.setBrand("setBrand");
//			mmVO1.setDisplacement(300);
//			mmVO1.setName("name");
//			mmVO1.setRenprice(1000);
//			mmVO1.setSaleprice(300000);
//			byte[] pic;
//
//			try {
//				pic = getPictureByteArray("C://motor//M" + i + ".jpg");
//				mmVO1.setMotpic(pic);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

//			dao.insert(mmVO1);
//		}
//
//		System.out.println("insert ok");

//		 MotorModelVO mmVO2 = new MotorModelVO();
//		 mmVO2.setBrand("setBrand");
//		 mmVO2.setDisplacement(250);
//		 mmVO2.setName("name2");
//		 mmVO2.setRenprice(10001);
		// mmVO2.setSaleprice(20001);
		// byte[] pic2;
		//
		// try {
		// pic2 = getPictureByteArray("C://tomcat2.gif");
		// mmVO2.setMotpic(pic2);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// mmVO2.setModtype("MM000004");
		// dao.update(mmVO2);
		// System.out.println("update ok");

		// dao.delete("MM000003");
		// System.out.println("delete ok");

		// MotorModelVO mmVO3 = dao.findByPrimaryKey("MM000004");
		// System.out.println(mmVO3.getModtype()+",");
		// System.out.println(mmVO3.getBrand()+",");
		// System.out.println(mmVO3.getDisplacement()+",");
		// System.out.println(mmVO3.getName()+",");
		// System.out.println(mmVO3.getRenprice()+",");
		// System.out.println(mmVO3.getSaleprice()+",");
		// readPicture(mmVO3.getMotpic());

		 List<MotorModelVO> list = dao.getAll();
		 for (MotorModelVO aMM : list) {
		 printMethod(aMM);
		 }

	}

	private static void printMethod(MotorModelVO aMM) {
		System.out.println("getModtype :" + aMM.getModtype() + ",");
		System.out.println("getBrand :" + aMM.getBrand() + ",");
		System.out.println("getDisplacement :" + aMM.getDisplacement() + ",");
		System.out.println("getName :" + aMM.getName() + ",");
		System.out.println("getRenprice :" + aMM.getRenprice() + ",");
		System.out.println("getSaleprice :" + aMM.getSaleprice() + ",");
		readPicture(aMM.getMotpic());
		// System.out.println("getMotpic 未讀取");
		System.out.println();

	}

	// ???????????
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
