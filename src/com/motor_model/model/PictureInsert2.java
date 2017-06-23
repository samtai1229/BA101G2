package com.motor_model.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PictureInsert2 {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

	private static final String UPDATE = "UPDATE MOTOR_MODEL set " + "  motpic=? where modtype = ?";

	
	public void update(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setBytes(1, mmVO.getMotpic());
			pstmt.setString(2, mmVO.getModtype());

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

	public static void main(String[] args) throws SQLException {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "ba101g2";
		String passwd = "ba101g2";

		MotorModelJDBCDAO dao = new MotorModelJDBCDAO();
		
		
		Connection con2 = null;
		try {
			Class.forName(driver);
			con2 = DriverManager.getConnection(url, userid, passwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Statement stmt = con2.createStatement();
		
		//請改成要置圖的TABLE，目前為MOTOR_MODEL
		ResultSet rs2 = stmt.executeQuery("SELECT count(*) AS count FROM MOTOR_MODEL");
		rs2.next();
		int len = rs2.getInt("count"); 
		//取到總欄數， 對應到該放幾張圖


//		for (int i = 1; i < 10; i++) {
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
//				pic = getPictureByteArray("C://motor//H00" + i + ".jpg");
//				mmVO1.setMotpic(pic);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			dao.insert(mmVO1);
//		}

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
