package com.spots.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.spots.model.*;

public class SpotServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		doPost(req,res);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("spno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入景點編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String spno = str;
				try {
					if(str.length()!=8 || !str.contains("SP"))
						throw new Exception();
				} catch (Exception e) {
					errorMsgs.add("景點編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				SpotService spotSvc = new SpotService();
				SpotsVO spotVO = spotSvc.findByPK(spno);
				if (spotVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("spotVO", spotVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/spots/listOneSpot.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String spno = req.getParameter("spno");
				
				/***************************2.開始查詢資料****************************************/
				SpotService spotSvc = new SpotService();
				SpotsVO spotVO = spotSvc.findByPK(spno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("spotVO", spotVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/spot/update_spot_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String spno =req.getParameter("spno").trim();
				String spname = req.getParameter("spname").trim();
				
//				String job = req.getParameter("job").trim();				
//				
//				java.sql.Date hiredate = null;
//				try {
//					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//				} catch (IllegalArgumentException e) {
//					hiredate=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}

				Float longitude = null;
				try {
					longitude = new Float(req.getParameter("splong").trim());
				} catch (NumberFormatException e) {
					longitude = 0.f;
					errorMsgs.add("經度格式錯誤.");
				}

				Float latitude = null;
				try {
					latitude = new Float(req.getParameter("splat").trim());
				} catch (NumberFormatException e) {
					latitude = 0.f;
					errorMsgs.add("緯度格式錯誤.");
				}

				String locno = req.getParameter("locno").trim();

				SpotsVO spotVO = new SpotsVO();
				spotVO.setLocno(locno);;
				spotVO.setSplat(latitude);;
				spotVO.setSplong(longitude);;
				spotVO.setSpname(spname);
				spotVO.setSpno(spno);
			

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("spotVO", spotVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spots/update_spot_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				SpotService spSvc = new SpotService();
				spotVO = spSvc.updateOneSpot(spno, spname, locno, longitude, latitude);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("spotVO", spotVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/frontend/spots/listOneSpot.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/spots/update_spot_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String spno =req.getParameter("spno").trim();
				String spname = req.getParameter("spname").trim();
				
//				java.sql.Date hiredate = null;
//				try {
//					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
//				} catch (IllegalArgumentException e) {
//					hiredate=new java.sql.Date(System.currentTimeMillis());
//					errorMsgs.add("請輸入日期!");
//				}
				
				Float longitude = null;
				try {
					longitude = new Float(req.getParameter("splong").trim());
				} catch (NumberFormatException e) {
					longitude = 0.f;
					errorMsgs.add("經度格式錯誤.");
				}

				Float latitude = null;
				try {
					latitude = new Float(req.getParameter("splat").trim());
				} catch (NumberFormatException e) {
					latitude = 0.f;
					errorMsgs.add("緯度格式錯誤.");
				}
				
				String locno = req.getParameter("locno").trim();

				SpotsVO spotVO = new SpotsVO();
				spotVO.setLocno(locno);;
				spotVO.setSplat(latitude);;
				spotVO.setSplong(longitude);;
				spotVO.setSpname(spname);
				spotVO.setSpno(spno);
			

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("spotVO", spotVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/spots/addSpot.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				SpotService spSvc = new SpotService();
				spotVO = spSvc.updateOneSpot(spno, spname, locno, longitude, latitude);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/frontend/spots/listAllSpot.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/spots/addSpot.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
	
			try {
				/***************************1.接收請求參數***************************************/
				String spno = req.getParameter("spno");
				
				/***************************2.開始刪除資料***************************************/
				SpotService spSvc = new SpotService();
				SpotsVO spotVO = spSvc.findByPK(spno);
				spSvc.deleteOneSpot(spno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				DeptService deptSvc = new DeptService();
//				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno())); // 資料庫取出的list物件,存入request
//				
//				String url = requestURL;
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		
	}

}
