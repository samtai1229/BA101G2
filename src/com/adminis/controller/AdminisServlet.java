package com.adminis.controller;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminis.model.AdminisService;
import com.adminis.model.AdminisVO;

public class AdminisServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接受請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("admno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入管理員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/adminis/adm_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String admno = null;
				try {
					admno = new String(str);
				} catch (Exception e) {
					errorMsgs.add("管理員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/adminis/adm_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************2.開始查詢資料*****************************************/
				AdminisService adminisSvc = new AdminisService();
				AdminisVO adminisVO = adminisSvc.getOneAdminis(admno);
				if (adminisVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/adminis/adm_select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				System.out.println("查詢完成");
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("adminisVO", adminisVO); // 資料庫取出的adminisVO物件,存入req
				String url = "/backend/adminis/listOneAdminis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //成功轉交listOneAdminis.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/adminis/adm_select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
	
		if ("getOne_For_Update".equals(action)) { // 來自listAllAdminis.jsp的請求
			
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數****************************************/
				String admno = new String(req.getParameter("admno"));
				
				/***************************2.開始查詢資料****************************************/
				AdminisService adminisSvc = new AdminisService();
				AdminisVO adminisVO = adminisSvc.getOneAdminis(admno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("adminisVO", adminisVO);         // 資料庫取出的adminisVO物件,存入req
				String url = "/backend/adminis/update_adminis_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_adminis_input.jsp
				successView.forward(req, res);

				/*****************************其他可能的錯誤處理********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/adminis/listAllAdminis.jsp");
				failureView.forward(req, res);
			}
		}

	if("update".equals(action))

	{ // 來自update_adminis_input.jsp的請求

		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/**************************** 1.接受請求參數-輸入格式的錯誤處理 **********************/
			String admno = req.getParameter("admno").trim();
			String locno = req.getParameter("locno").trim();
			String authno = req.getParameter("authno").trim();
			String name = req.getParameter("name").trim();
			String acc = req.getParameter("acc").trim();
			String pw = req.getParameter("pw").trim();

			AdminisVO adminisVO = new AdminisVO();
			adminisVO.setAdmno(admno);
			adminisVO.setLocno(locno);
			adminisVO.setAuthno(authno);
			adminisVO.setName(name);
			adminisVO.setAcc(acc);
			adminisVO.setPw(pw);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("adminisVO", adminisVO); // 含有輸入格式錯誤的adminisVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/adminis/update_adminis_input.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			}

			/*************************** 2.開始修改資料 *****************************************/
			AdminisService adminisSvc = new AdminisService();
			adminisVO = adminisSvc.updateAdminis(admno, locno, authno, name, acc, pw);

			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			req.setAttribute("adminisVO", adminisVO); // 資料庫update成功後,正確的adminisVO物件,存入req
			String url = "/backend/adminis/listOneAdminis.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後listOneAdminis.jsp
			successView.forward(req, res);

			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add("修改資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/backend/adminis/update_adminis_input.jsp");
			failureView.forward(req, res);
		}
	}

	if("insert".equals(action))
	{ // 來自addAdminis.jsp的請求
		System.out.print("INSERT");
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/**************************** 1.接受請求參數-輸入格式的錯誤處理 **********************/
			String locno = req.getParameter("locno").trim();
			String authno = req.getParameter("authno").trim();
			String name = req.getParameter("name").trim();
			String acc = req.getParameter("acc").trim();
			String pw = req.getParameter("pw").trim();

			AdminisVO adminisVO = new AdminisVO();
			adminisVO.setLocno(locno);
			adminisVO.setAuthno(authno);
			adminisVO.setName(name);
			adminisVO.setAcc(acc);
			adminisVO.setPw(pw);
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				System.out.println("IF");
				req.setAttribute("adminisVO", adminisVO); 
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/adminis/addAdminis.jsp");
				failureView.forward(req, res);
				return;
			}
			/*************************** 2.開始新增資料 ***************************************/
			AdminisService adminisSvc = new AdminisService();
			adminisVO = adminisSvc.addAdminis(locno, authno, name, acc, pw);
			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			String url = "/backend/adminis/listAllAdminis.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			/***************************** 其他可能的錯誤處理 ********************************/
			
		} catch (Exception e) {
			errorMsgs.add(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/backend/adminis/addAdminis.jsp");
			failureView.forward(req, res);
		}
	}

	if("delete".equals(action))
	{ // 來自listAllAdminis.jsp
		List<String> errorMsgs = new LinkedList<String>();
		// Store this set in the request scope, in case we need to
		// send the ErrorPage view.
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/**************************** 1.接受請求參數 **********************/
			String admno = req.getParameter("admno");

			/*************************** 2.開始刪除資料 ***************************************/
			AdminisService adminisSvc = new AdminisService();
			adminisSvc.deleteAdminis(admno);

			/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
			String url = "/backend/adminis/listAllAdminis.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);

			/***************************** 其他可能的錯誤處理 ********************************/
		} catch (Exception e) {
			errorMsgs.add("刪除資料失敗:" + e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/backend/adminis/listAllAdminis.jsp");
			failureView.forward(req, res);
			}
		}
	if (action.equals("logout")) {

		HttpSession session = req.getSession();
		session.removeAttribute("account");
		res.sendRedirect("/BA101G2/backend/BackendLogin.jsp");

	}

 	}
}