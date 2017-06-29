package com.motor_model.controller;

import java.io.*;

import javax.servlet.http.Part;

public class GetPictureByteArrayFromWeb {
	public static byte[] getPictureByteArrayFromWeb(Part part) throws IOException {
		System.out.println("123132");
		System.out.println(part);
		InputStream is = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = is.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		is.close();
		
		return baos.toByteArray();
	}
	//用來判斷是否有上傳圖片，如此，如果沒有上傳圖片可以把原本存在的圖片在存回一次。
	public static String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}