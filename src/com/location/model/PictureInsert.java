package com.location.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PictureInsert {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba101g2";
	String passwd = "ba101g2";

	private static final String UPDATE = "UPDATE LOCATION set " + "  PIC=?  where LOCNO = ?";
	
	public void update(LocationVO locVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
	
            pstmt = con.prepareStatement(UPDATE);  
			pstmt.setBytes(1, locVO.getPic()); 
			pstmt.setString(2, locVO.getLocno());


			pstmt.executeUpdate();
			//con.commit();
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

	public static void main(String[] args) {

		PictureInsert dao = new PictureInsert();

		for (int i = 1; i <=6; i++) {

			LocationVO locVO = new LocationVO();

			byte[] pic;

			try {
				pic = getPictureByteArray("C://Location//logo3.png"); 


				locVO.setPic(pic);
			} catch (IOException e) {
				e.printStackTrace();
			}

			locVO.setLocno("L00000"+i);
			
			dao.update(locVO);
		}
		System.out.println("insert ok");

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
