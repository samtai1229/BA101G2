package com.immediate_push.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.immediate_push.model.ImmediatePushService;
import com.immediate_push.model.ImmediatePushVO;




public class ImmediatePushServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
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
				
					String str = req.getParameter("ipno");
					System.out.println(str);
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入推播編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/immediate_push/immediatepush_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				String ipno = null;
				try {
					ipno = new String(str);
				} catch (Exception e) {
					errorMsgs.add("編號不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/immediate_push/immediatepush_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				ImmediatePushService immediatepushSvc = new ImmediatePushService();
				ImmediatePushVO immediatepushVO = immediatepushSvc.getOneImmediatePush(ipno);
				if (immediatepushVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/immediate_push/immediatepush_select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("immediatepushVO", immediatepushVO);
				String url = "/backend/immediate_push/listOneImmediatePush.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/immediate_push/immediatepush_select_page.jsp");
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
				String ipno = req.getParameter("ipno");
				
				/***************************2.開始查詢資料****************************************/
				ImmediatePushService immediatepushSvc = new ImmediatePushService();
				ImmediatePushVO immediatepushVO = immediatepushSvc.getOneImmediatePush(ipno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("immediatepushVO", immediatepushVO); 
				String url = "/backend/immediate_push/update_immediatepush_input.jsp";
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
				String admno = req.getParameter("admno").trim();
				String ipcont = req.getParameter("ipcont").trim();
				String pushno = req.getParameter("pushno").trim();
				String ipno = req.getParameter("ipno").trim();
				System.out.println("!!!!!"+ipno);
				
				ImmediatePushVO immediatepushVO = new ImmediatePushVO();
				immediatepushVO.setAdmno(admno);
				immediatepushVO.setIpcont(ipcont);
				immediatepushVO.setPushno(pushno);
				immediatepushVO.setIpno(ipno);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("immediatepushVO", immediatepushVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/immediate_push/update_immediatepush_input.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始修改資料 *****************************************/
				ImmediatePushService immediatepushSvc = new ImmediatePushService();
				immediatepushVO = immediatepushSvc.updateImmediatePush(admno,ipcont,pushno,ipno);
				System.out.println(immediatepushVO);
				System.out.println(immediatepushVO.getAdmno());
				System.out.println(immediatepushVO.getIpcont());
				System.out.println(immediatepushVO.getPushno());
				System.out.println(immediatepushVO.getIpno());
				/***************************** 3.修改完成,準備轉交(Send the Success view)*************/			
				req.setAttribute("immediatepushVO", immediatepushVO); 
	            String url = requestURL;
	            System.out.println("aaaaaaaaaaaaaaaaa");
				RequestDispatcher successView = req.getRequestDispatcher(url);  
				successView.forward(req, res);

				/***************************** 其他可能的錯誤處理 ********************************/
			} catch (Exception e) {
				System.out.println("ssssssssssssss");
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/immediate_push/update_immediatepush_input.jsp");
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
				String ipcont = req.getParameter("ipcont").trim();
				String pushno = req.getParameter("pushno").trim();
				
				ImmediatePushVO immediatepushVO = new ImmediatePushVO();
				immediatepushVO.setAdmno(admno);
				immediatepushVO.setIpcont(ipcont);
				immediatepushVO.setPushno(pushno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("immediatepushVO", immediatepushVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/immediate_push/addImmediatePush.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				ImmediatePushService immediatepushSvc = new ImmediatePushService();
				immediatepushVO = immediatepushSvc.addImmediatePush(admno,ipcont, pushno);
				
				/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/backend/immediate_push/listAllImmediatePush.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);				
				
				/***************************** 其他可能的錯誤處理 ********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/immediate_push/addImmediatePush.jsp");
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
				String ipno = req.getParameter("ipno");
				
				/*************************** 2.開始刪除資料 ***************************************/
				ImmediatePushService immediatepushSvc = new ImmediatePushService();
				immediatepushSvc.deleteImmediatePush(ipno);
				
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
}
