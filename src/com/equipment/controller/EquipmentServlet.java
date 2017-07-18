package com.equipment.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.equipment.model.*;
import com.motor.model.MotorVO;

public class EquipmentServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("action: " + action);

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
				EquipmentService emtSvc = new EquipmentService();
				EquipmentVO emtVO = emtSvc.getOneEquipment(emtno);
				if (emtVO == null) {
					errorMsgs.add("查無裝備資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("emtVO", emtVO); // 資料庫取出的emtVO物件,存入req
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
				/*********** 1.接收請求參數 ********/
				String emtno = req.getParameter("emtno");

				/********* 2.開始查詢資料 ***************/
				EquipmentService emtSvc = new EquipmentService();
				EquipmentVO emtVO = emtSvc.getOneEquipment(emtno);

				/********** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("emtVO", emtVO); // 資料庫取出的emtVO物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher("/backend/equipment/updateEmtInput.jsp");
				successView.forward(req, res);
				System.out.println("getOne_For_Update 成功");

				/********** 其他可能的錯誤處理 *******/
			} catch (Exception e) {
				errorMsgs.add("修改裝備資料(equipment)取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}//end of getOne_For_Update

// update
		if ("update".equals(action)) { 
			System.out.println("開始 update");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the
			// ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;
			try {
				/********** 1.接收請求參數 - 輸入格式的錯誤處理******/
				String emtno = req.getParameter("emtno");
				String ecno = req.getParameter("ecno");
				String locno = req.getParameter("locno");
				String status = req.getParameter("status");
				String note = req.getParameter("note");

				// 處理日期
				Timestamp purchdate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					// getTime = long 老吳：取得long就取得全世界
					// String 轉成 sdf(日期) 再轉成long
					System.out.println(req.getParameter("purchdate"));
					long purchdateTypeLong = (sdf.parse(req.getParameter("purchdate").trim())).getTime();
					purchdate = new Timestamp(purchdateTypeLong);
				} catch (IllegalArgumentException e) {
					purchdate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期");
				}
				EquipmentVO emtVO = new EquipmentVO();
				emtVO.setEmtno(emtno);
				emtVO.setEcno(ecno);
				emtVO.setLocno(locno);
				emtVO.setStatus(status);
				emtVO.setNote(note);
				emtVO.setPurchdate(purchdate);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emtVO", emtVO); // 含有輸入格式錯誤的emtVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/********* 2.開始修改資料 *************/
				EquipmentService emtSvc = new EquipmentService();
				emtVO = emtSvc.updateEquipment(emtno, ecno, locno, purchdate, status, note);
				
				/**** 3.修改完成,準備轉交(Send the Success view)******/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/equipment/listAllEmts.jsp"); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				System.out.println("update 成功");
				
				/********* 其他可能的錯誤處理 ************/
			} catch (Exception e) {
				System.out.println("update 失敗");
				e.printStackTrace();
				errorMsgs.add("修改裝備調度單資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// insert
		if ("insert".equals(action)) { // 來自addEpuipment.jsp的請求
			System.out.println("開始insert");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/************************* 1.接收請求參數 - 輸入格式的錯誤處理*************************/

				String ecno = req.getParameter("ecno").trim();
				String note = req.getParameter("note").trim();

				// 處理日期
				Timestamp purchdate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					// getTime = long 老吳：取得long就取得全世界
					// String 轉成 sdf(日期) 再轉成long
					System.out.println(req.getParameter("purchdate"));
					long purchdateTypeLong = (sdf.parse(req.getParameter("purchdate").trim())).getTime();
					purchdate = new Timestamp(purchdateTypeLong);
				} catch (IllegalArgumentException e) {
					purchdate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期");
				}

				EquipmentVO emtVO = new EquipmentVO();
				emtVO.setEcno(ecno);
				emtVO.setNote(note);
				emtVO.setPurchdate(purchdate);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("emtVO", emtVO); // 含有輸入格式錯誤的emtVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EquipmentService emtSvc = new EquipmentService();
				emtVO = emtSvc.addEquipment(ecno, purchdate, note);

				/***************************** 3.新增完成,準備轉交(Send the Success view)***********/

				RequestDispatcher successView = req.getRequestDispatcher("/backend/equipment/listAllEmts.jsp"); // 新增成功後轉交listAllEqpt.jsp
				successView.forward(req, res);
				System.out.println("insert 成功");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println("insert 失敗");
				errorMsgs.add("靠，你媽知道你廢到連新增裝備(equipment)都會出錯嗎問號問號= = " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// delete
		if ("delete".equals(action)) { // 來自listAllEqpts.jsp的請求

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
