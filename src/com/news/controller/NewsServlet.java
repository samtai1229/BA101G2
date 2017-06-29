package com.news.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.news.model.NewsService;
import com.news.model.NewsVO;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class NewsServlet extends HttpServlet{	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
	doPost(req, res);
}

public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

	req.setCharacterEncoding("UTF-8");
	String action = req.getParameter("action");
	
	
	if ("getOne_For_Display".equals(action)) { 
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
				String str = req.getParameter("newsno");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入最新消息編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/news/news_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			String news = null;
			try {
				news = new String(str);
			} catch (Exception e) {
				errorMsgs.add("編號不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/news/news_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			NewsService newsSvc = new NewsService();
			NewsVO newsVO = newsSvc.getOneNews(str);
			if (newsVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/news/news_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			req.setAttribute("newsVO", newsVO);
			String url = "/backend/news/listOneNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/backend/news/news_select_page.jsp");
			failureView.forward(req, res);
		}
	}
	
	
	if ("getOne_For_Update".equals(action)) { 

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		String requestURL = req.getParameter("requestURL");
		
		try {
			/***************************1.接收請求參數****************************************/
			String newsno = new String(req.getParameter("newsno"));
			
			/***************************2.開始查詢資料****************************************/
			NewsService newsSvc = new NewsService();
			NewsVO newsVO = newsSvc.getOneNews(newsno);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("newsVO", newsVO); 
			String url = "/backend/news/update_news_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/*****************************其他可能的錯誤處理********************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得要修改資料"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(requestURL);
			failureView.forward(req, res);
		}
	}
	
	
	if ("update".equals(action)) { 
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		String requestURL = req.getParameter("requestURL"); 
	
		try {
			/**************************** 1.接受請求參數-輸入格式的錯誤處理 **********************/
			String newsno = req.getParameter("newsno").trim();
			String admno = req.getParameter("admno").trim();
			String cont = req.getParameter("cont").trim();
			byte[] pic = null;
			Part part = req.getPart("pic");
		    try {
		    	if (getFileNameFromPart(part) != null )   
		          pic = getPictureByteArray(part);
		    	else {
		    		NewsService newsSvc = new NewsService();
					NewsVO newsVO = newsSvc.getOneNews(newsno);
				    pic = newsVO.getPic();
		    	}
		   
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
		    
			String title = req.getParameter("title").trim();
			String status = req.getParameter("status").trim();
			

			NewsVO newsVO = new NewsVO();
			newsVO.setNewsno(newsno);
			newsVO.setAdmno(admno);
			newsVO.setCont(cont);
			newsVO.setPic(pic);
			newsVO.setTitle(title);
			newsVO.setStatus(status);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("newsVO", newsVO); 
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/update_news_input.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/*************************** 2.開始修改資料 *****************************************/
			NewsService newsSvc = new NewsService();
			newsVO = newsSvc.updateNews(newsno,admno, cont, pic, title, status);
			
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/			
			req.setAttribute("newsVO", newsVO); 

            String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url);  
			successView.forward(req, res);

			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/news/update_news_input.jsp");
			failureView.forward(req, res);
		}
	}

    if ("insert".equals(action)) { 
		
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		try {
			/**************************** 1.接受請求參數-輸入格式的錯誤處理 **********************/
			String admno = req.getParameter("admno").trim();
			String cont = req.getParameter("cont").trim();
			byte[] pic = null;
		    try {
		     Part part = req.getPart("pic");
		     System.out.println(part);
		     pic = getPictureByteArray(part);
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
		    
			String title = req.getParameter("title").trim();
			String status = req.getParameter("status").trim();
			

			NewsVO newsVO = new NewsVO();
			newsVO.setAdmno(admno);
			newsVO.setCont(cont);
			newsVO.setPic(pic);
			newsVO.setTitle(title);
			newsVO.setStatus(status);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("newsVO", newsVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher("/news/addNews.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/*************************** 2.開始新增資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsVO = newsSvc.addNews(admno, cont, pic, title, status);
			
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			String url = "/backend/news/listAllNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);				
			
			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/news/addNews.jsp");
			failureView.forward(req, res);
		}
	}
	
   
	if ("delete".equals(action)) { 

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		
		String requestURL = req.getParameter("requestURL"); 

		try {
			/**************************** 1.接受請求參數 **********************/
			String newsno = req.getParameter("newsno");
			
			/*************************** 2.開始刪除資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(newsno);
			
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			
			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add("刪除資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(requestURL);
			failureView.forward(req, res);
		}
	}
}
public static byte[] getPictureByteArray(Part path) throws IOException {
InputStream fis = path.getInputStream();
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

public String getFileNameFromPart(Part part) {
	String header = part.getHeader("content-disposition");
	System.out.println("header=" + header); // ���ե�
	String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
	System.out.println("filename=" + filename); // ���ե�
	if (filename.length() == 0) {
		return null;
	}
	return filename;
}
}
