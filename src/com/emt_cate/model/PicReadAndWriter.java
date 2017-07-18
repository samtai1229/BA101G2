package com.emt_cate.model;

import java.io.*;

public class PicReadAndWriter {
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		// 輸出到一個byteArray裡面
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 8kb 多用此單位，亦可用別的 但不推薦
		byte[] buffer = new byte[8192];
		int i;
		// 當buffer 等於 -1 表示沒資料了
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		// 回傳給bytearray
		return baos.toByteArray();
	}

													 // name = 檔名，必須有附檔名
	public static void readPicture(byte[] bytes, String name) throws IOException {
		FileOutputStream fos = new FileOutputStream("WebContent/backend/images/output/" + name);
		fos.write(bytes);
		fos.flush();
		fos.close();
	}
}
