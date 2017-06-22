package com.sec_ord.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sec_ord.model.*;

public class SecondOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
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
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String sono = req.getParameter("sono");
				String memno = req.getParameter("memno");

				if ((sono == null || (sono.trim()).length() == 0) && (memno == null || (memno.trim()).length() == 0)) {

					errorMsgs.add("請輸入訂單或會員編號");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				SecOrdService soSvc = new SecOrdService();
				SecOrdVO soVO = null;
				if (sono != null)
					soVO = soSvc.getOneSecOrdBySono(sono);
				else
					soVO = soSvc.getOneSecOrdByMemno(memno);
				if (soVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("soVO", soVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/second_order/listOneSecOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或
													// /dept/listEmps_ByDeptno.jsp
													// 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				
				String sono = req.getParameter("sono");
				/*************************** 2.開始查詢資料 ****************************************/
				SecOrdService soSvc = new SecOrdService();
				SecOrdVO soVO = soSvc.getOneSecOrdBySono(sono);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("soVO", soVO); // 資料庫取出的empVO物件,存入req
				String url = "/frontend/second_order/update_SecOrd_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
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
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String sono = req.getParameter("sono").trim();

				String memno = req.getParameter("memno").trim();
			

				SecOrdVO soVO = new SecOrdVO();

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("soVO", soVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/frontend/second_order/update_SecOrd_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				SecOrdService soSvc = new SecOrdService();
				soVO = soSvc.addSecOrd(sono, memno);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("soVO", soVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/frontend/second_order/listOneSecOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/frontend/second_order/update_SecOrd_input.jsp");
				failureView.forward(req, res);
			}
		}

		
		
		if ("listSecOrd_ByStatus".equals(action)) { // 來自ListAllSecOrd.jsp的請求
           System.out.println("近來狀態查詢");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String status = req.getParameter("status");
				System.out.println("欲查詢狀態:"+status);
				

				

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ***********/
				req.setAttribute("status", status);
				String url = "/frontend/second_order/listAllSecOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				//
				// /*************************** 其他可能的錯誤處理
				// **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order//frontend/second_order/listAllSecOrd.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addSpot.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String memno = req.getParameter("memno").trim();
				String motno = req.getParameter("motno").trim();

				SecOrdVO soVO = new SecOrdVO();
				soVO.setMotorno(motno);
				soVO.setMemno(memno);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("soVO", soVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/addSecOrd.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				SecOrdService soSvc = new SecOrdService();
				soVO = soSvc.addSecOrd(memno, motno);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/frontend/second_order/listAllSecOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				//
				// /*************************** 其他可能的錯誤處理
				// **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/frontend/second_order/addSecOrd.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑:
																// 可能為【/emp/listAllEmp.jsp】
																// 或
																// 【/dept/listEmps_ByDeptno.jsp】
																// 或 【
																// /dept/listAllDept.jsp】

			try {
				/*************************** 1.接收請求參數 ***************************************/
				 String sono = req.getParameter("sono");
				/*************************** 2.開始刪除資料 ***************************************/
				 SecOrdService soSvc = new SecOrdService();

				 soSvc.deleteSecOrd(sono);

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno()));
				// // 資料庫取出的list物件,存入request
				//
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				//
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}
}
