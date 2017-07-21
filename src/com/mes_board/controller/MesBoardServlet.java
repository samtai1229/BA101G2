package com.mes_board.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.mes_board.model.MesBoardService;
import com.mes_board.model.MesBoardVO;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MesBoardServlet extends HttpServlet{	public void doGet(HttpServletRequest req, HttpServletResponse res)
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
			
				String str = req.getParameter("mesno");
				System.out.println(str);
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入最新留言編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/mes_board/mesboard_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			String mesno = null;
			try {
				mesno = new String(str);
			} catch (Exception e) {
				errorMsgs.add("編號不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/mes_board/mesboard_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			MesBoardService mesboardSvc = new MesBoardService();
			System.out.println("mesno : "+mesno);
			System.out.println("str"+str);
			MesBoardVO mesboardVO = mesboardSvc.getOneMesBoard(mesno);
			System.out.println(mesboardVO);
			if (mesboardVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/mes_board/mesboard_select_page.jsp");
				failureView.forward(req, res);
				return;
			}
			
			req.setAttribute("mesboardVO", mesboardVO);
			String url = "/backend/mes_board/listOneMesBoard.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/backend/mes_board/mesboard_select_page.jsp");
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
			String mesno = req.getParameter("mesno");
			
			/***************************2.開始查詢資料****************************************/
			MesBoardService mesboardSvc = new MesBoardService();
			MesBoardVO mesboardVO = mesboardSvc.getOneMesBoard(mesno);
							
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("mesboardVO", mesboardVO); 
			String url = "/backend/mes_board/update_mesboard_input.jsp";
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
			String mesno = req.getParameter("mesno").trim();
			String memno = req.getParameter("memno").trim();
			byte[] pic = null;
		    try {
		     Part part = req.getPart("pic");
		     pic = getPictureByteArray(part);
		    } catch (Exception e) {
		    	System.out.println("pic null");
		     e.printStackTrace();
		    }
		    
			String cont = req.getParameter("cont").trim();
			String status = req.getParameter("status").trim();
			MesBoardVO mesboardVO = new MesBoardVO();
			mesboardVO.setMesno(mesno);
			mesboardVO.setMemno(memno);
			mesboardVO.setCont(cont);
			mesboardVO.setPic(pic);
			mesboardVO.setStatus(status);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("mesboardVO", mesboardVO); 
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/mes_board/update_mesboard_input.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始修改資料 *****************************************/
			MesBoardService mesboardSvc = new MesBoardService();
			System.out.println(cont);
			System.out.println(status);
			System.out.println(pic);
			System.out.println(mesno);
			System.out.println(memno);
			 mesboardVO = mesboardSvc.updateMesBoard(mesno,memno,cont, pic, status);
			
			System.out.println(mesboardVO.getMesno());
			System.out.println(mesboardVO.getMemno());
			System.out.println(mesboardVO.getMesdate());
			System.out.println(mesboardVO.getCont());
			System.out.println(mesboardVO.getStatus());
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/			
			req.setAttribute("mesboardVO", mesboardVO); 
            String url = requestURL;
			RequestDispatcher successView = req.getRequestDispatcher(url);  
			successView.forward(req, res);

			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:"+e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/backend/mes_board/update_mesboard_input.jsp");
			failureView.forward(req, res);
		}
	}

    if ("insert".equals(action)) { 
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);
		String requestURL=req.getParameter("requestURL");
		try {
			/**************************** 1.接受請求參數-輸入格式的錯誤處理 **********************/
			String memno = req.getParameter("memno").trim();
			String cont = req.getParameter("cont").trim();
			byte[] pic = null;
		    try {
		     Part part = req.getPart("pic");
		     System.out.println(part);
		     pic = getPictureByteArray(part);
		    } catch (Exception e) {
		     e.printStackTrace();
		    }
			//String status = req.getParameter("status").trim();
			

			MesBoardVO mesboardVO = new MesBoardVO();
			mesboardVO.setMemno(memno);
			mesboardVO.setCont(cont);
			mesboardVO.setPic(pic);
			//mesboardVO.setStatus(status);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("mesboardVO", mesboardVO);
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				//backend/mes_board/addMesBoard.jsp
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始新增資料 ***************************************/
			MesBoardService mesboardSvc = new MesBoardService();
			mesboardVO = mesboardSvc.addMesBoard(memno,cont, pic);
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			String url = "/forntend/mes_board/listAllMesBoard.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(requestURL);
			successView.forward(req, res);				
			
			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(requestURL);
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
			String mesno = req.getParameter("mesno");
			
			/*************************** 2.開始刪除資料 ***************************************/
			MesBoardService mesboardSvc = new MesBoardService();
			mesboardSvc.deleteMesBoard(mesno);
			
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
}
