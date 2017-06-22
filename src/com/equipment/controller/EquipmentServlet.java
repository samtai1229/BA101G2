package com.equipment.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.equipment.model.*;

public class EquipmentServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

// getOne_For_Display
		if ("getOne_For_Display".equals(action)) { // 來自eqptMgmtSelectPage.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/******************************* 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("emtno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備編號(EMTNO)");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String emtno = "";
				try {
					emtno = str;
				} catch (Exception e) {
					errorMsgs.add("裝備編號emtno格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				EquipmentVO eqptVO = eqptSvc.getOneEquipment(emtno);
				if (eqptVO == null) {
					errorMsgs.add("查無裝備資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqptVO", eqptVO); // 資料庫取出的eqptVO物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher("/backend/equipment/listOneEqpt.jsp");
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得裝備(equipment)資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// getOne_For_Update
		if ("getOne_For_Update".equals(action)) { // 來自listAllEqpt.jsp或listEqpts_ByEcno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the
			// ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String emtno = req.getParameter("emtno");

				/*************************** 2.開始查詢資料 ****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				EquipmentVO eqptVO = eqptSvc.getOneEquipment(emtno);

				/****************************** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("eqptVO", eqptVO); // 資料庫取出的eqptVO物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(requestURL);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改裝備資料(equipment)取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

// update
		if ("update".equals(action)) { // 來自update_ed_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the
			// ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;
			try {
				/***************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String emtno = req.getParameter("emtno").trim();
				String ecno = req.getParameter("ecno").trim();
				String locno = req.getParameter("locno").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();

				Timestamp purchdate = null;
				try {
					purchdate = Timestamp.valueOf(req.getParameter("purchdate").trim());
				} catch (IllegalArgumentException e) {
					purchdate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				EquipmentVO eqptVO = new EquipmentVO();
				eqptVO.setEmtno(emtno);
				eqptVO.setEcno(ecno);
				eqptVO.setLocno(locno);
				eqptVO.setStatus(status);
				eqptVO.setNote(note);
				eqptVO.setPurchdate(purchdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eqptVO", eqptVO); // 含有輸入格式錯誤的eqptVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptVO = eqptSvc.updateEquipment(emtno, ecno, locno, purchdate, note, status);

				/***************************** 3.修改完成,準備轉交(Send the Success view)*************/
				// 暫時用不到
				// if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(deptno));
				// 資料庫取出的list物件,存入request

				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改裝備調度單資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// insert
		if ("insert".equals(action)) { // 來自addEpuipment.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/************************* 1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String ecno = req.getParameter("ecno").trim();
				String note = req.getParameter("note").trim();

				Timestamp purchdate = null;
				try {
					purchdate = Timestamp.valueOf(req.getParameter("purchdate").trim());
				} catch (IllegalArgumentException e) {
					purchdate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				EquipmentVO eqptVO = new EquipmentVO();
				eqptVO.setEcno(ecno);
				eqptVO.setNote(note);
				eqptVO.setPurchdate(purchdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eqptVO", eqptVO); // 含有輸入格式錯誤的eqptVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptVO = eqptSvc.addEquipment(ecno, purchdate, note);

				/***************************** 3.新增完成,準備轉交(Send the Success view)***********/

				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEqpt.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("靠，你媽知道你廢到連新增裝備(equipment)都會出錯嗎問號問號= = " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// delete
		if ("delete_Epqt".equals(action)) { // 來自listAllEqpts.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String emtno = req.getParameter("emtno");

				/*************************** 2.開始刪除資料 ***************************************/
				EquipmentService edSvc = new EquipmentService();
				edSvc.deleteEquipment(emtno);

				/***************************** 3.刪除完成,準備轉交(Send the Success view)***********/
				// String url = "/dept/listAllDept.jsp";//我直接回傳呼叫他的網址了
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除裝備(equipment)資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
	}
}
