package com.member.model;

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

	private static final String UPDATE_F = "UPDATE MEMBER set " + "  IDCARD1=?  where MEMNO = ?";
	private static final String UPDATE_B = "UPDATE MEMBER set " + "  IDCARD2=?  where MEMNO = ?";
	private static final String UPDATE_L = "UPDATE MEMBER set " + "  LICENSE=?  where MEMNO = ?";
	public void update(MemberVO memVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_F); //正面
	//		pstmt = con.prepareStatement(UPDATE_B); //背面		
    //        pstmt = con.prepareStatement(UPDATE_L);  //駕照
			pstmt.setBytes(1, memVO.getLicense()); //拿到正背面 or 駕照
			pstmt.setString(2, memVO.getMemno());


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

		for (int i = 1; i <=10; i++) {

			MemberVO mmVO1 = new MemberVO();

			byte[] pic;

			try {
				pic = getPictureByteArray("C://IDCARD//F1.jpg"); //正面圖	
		//		pic = getPictureByteArray("C://IDCARD//B1.jpg"); //背面圖
		//		pic = getPictureByteArray("C://IDCARD//L1.jpg");
		//		mmVO1.setIdcard1(pic);  //正面
	   //        mmVO1.setIdcard2(pic);  //背面			
	         mmVO1.setLicense(pic); //駕照
			} catch (IOException e) {
				e.printStackTrace();
			}

			mmVO1.setMemno("MEM00000"+i);
			if(i==10)
				mmVO1.setMemno("MEM0000"+i);

			dao.update(mmVO1);
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
