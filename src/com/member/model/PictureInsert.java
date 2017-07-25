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

	private static final String UPDATE = "UPDATE MEMBER set " + "  IDCARD1=? , IDCARD2=? , LICENSE=? where MEMNO = ?";

	public void update(MemberVO memVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE); //正面
//			pstmt2 = con.prepareStatement(UPDATE_B); //背面		
//            pstmt3 = con.prepareStatement(UPDATE_L);  //駕照
			pstmt.setBytes(1, memVO.getIdcard1()); //拿到正背面 or 駕照
//			pstmt1.setString(2, memVO.getMemno());
			pstmt.setBytes(2, memVO.getIdcard2()); //拿到正背面 or 駕照
//			pstmt2.setString(2, memVO.getMemno());
			pstmt.setBytes(3, memVO.getLicense()); //拿到正背面 or 駕照
			pstmt.setString(4, memVO.getMemno());
//
//			pstmt1.executeUpdate();
//			pstmt2.executeUpdate();
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

			byte[] pic1 ,pic2,pic3;

			try {
				pic1 = getPictureByteArray("DB_Image//idcard//F1.jpg"); //正面圖	
				pic2 = getPictureByteArray("DB_Image//idcard//B1.jpg"); //背面圖
				pic3 = getPictureByteArray("DB_Image//idcard//L1.jpg");
				mmVO1.setIdcard1(pic1);  //正面
	           mmVO1.setIdcard2(pic2);  //背面			
	         mmVO1.setLicense(pic3); //駕照
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
