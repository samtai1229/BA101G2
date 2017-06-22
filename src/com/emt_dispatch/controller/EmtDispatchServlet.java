package com.emt_dispatch.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emt_disp_list.model.EmtDispListService;
import com.emt_disp_list.model.EmtDispListVO;
import com.emt_dispatch.model.*;
import com.equipment.model.EquipmentVO;

public class EmtDispatchServlet extends HttpServlet  {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

// getOne_For_Display
		if ("getOne_For_Display".equals(action)) { // 來自EdMgmtSelectPage.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/***************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("edno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備調度單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String edno = "";
				try {
					edno = str;
				} catch (Exception e) {
					errorMsgs.add("裝備調度單格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				
				String emtno= req.getParameter("emtno").trim();
				EmtDispatchVO edVO = edSvc.getOneEmtDispatch(edno);
				EmtDispListVO edListVO = edListSvc.getOneEmtDispList(edno, emtno);
				
				if (edVO == null) {
					errorMsgs.add("查無裝備調度單資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/**************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("edVO", edVO); // 資料庫取出的edVO物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher("/backend/emt_dispatch/listOneEd.jsp"); // 成功轉交listOneEd.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得裝備調度單資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// getOne_For_Update
		if ("getOne_For_Update".equals(action)) { // 來自listAllEd.jsp 或/emt_dispatch/listEd_ByEdno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:可能為【/emt_dispatch/listAllEd.jsp】或【/dept/listEmps_ByDeptno.jsp】或 【/dept/listAllDept.jsp】

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String edno = req.getParameter("edno");
				String emtno = req.getParameter("emtno");

				/*************************** 2.開始查詢資料 ****************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				
				EmtDispatchVO edVO = edSvc.getOneEmtDispatch(edno);
				EmtDispListVO edListVO = edListSvc.getOneEmtDispList(edno, emtno);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("edVO", edVO); // 資料庫取出的edVO物件,存入req
				String url = "/emt_dispatch/update_ed_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_ed_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改裝備類別資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

// update
		if ("update".equals(action)) { // 來自update_ed_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:可能為【/emt_cate/listAllEc.jsp】或【/emt_cate/listEc_ByEcno.jsp】或 【/emt_cate/listAllEc.jsp】
			String url = requestURL;
			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String edno = req.getParameter("edno").trim();
				String locno = req.getParameter("locno").trim();
				String prog = req.getParameter("prog").trim();
				String emtno= req.getParameter("emtno").trim();
				
				Timestamp demanddate = null;
				try {
					demanddate = Timestamp.valueOf(req.getParameter("demanddate").trim());
				} catch (IllegalArgumentException e) {
					demanddate = new Timestamp (System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Timestamp closeddate = null;
				try {
					closeddate = Timestamp.valueOf(req.getParameter("closeddate").trim());
				} catch (IllegalArgumentException e) {
					closeddate = new Timestamp (System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				EmtDispatchVO edVO = new EmtDispatchVO();
				EmtDispListVO edListVO = new EmtDispListVO();
				edVO.setEdno(edno);
				edVO.setLocno(locno);
				edVO.setProg(prog);
				edVO.setDemanddate(demanddate);
				edVO.setCloseddate(closeddate);
				edListVO.setEdno(edno);
				edListVO.setEmtno(emtno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("edVO", edVO); // 含有輸入格式錯誤的edVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				edVO = edSvc.updateEmtDispatch(edno, locno, demanddate, closeddate, prog);
				edListVO = edListSvc.updateEmtDispList(edno, emtno);

				/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
				//暫時用不到
//				 if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//				 req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(deptno));
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
		if ("insert".equals(action)) { // 來自addEmtDispatch.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String locno = req.getParameter("locno").trim();
				String edno = req.getParameter("edno").trim();
				String emtno= req.getParameter("emtno").trim();

				EmtDispatchVO edVO = new EmtDispatchVO();
				EmtDispListVO edListVO = new EmtDispListVO();
				edVO.setLocno(locno);
				edListVO.setEdno(edno);
				edListVO.setEmtno(emtno);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("edVO", edVO); // 含有輸入格式錯誤的edVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				edVO = edSvc.addEmtDispatch(locno);
				edListVO = edListSvc.addEmtDispList(edno, emtno);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/

				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEd.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("靠，你媽知道你廢到連新增裝備調度單都會出錯嗎問號問號= = " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// delete
		if ("delete_Ed".equals(action)) { // 來自/emt_dispatch/listAllEd.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String edno = req.getParameter("edno").trim();
				String emtno= req.getParameter("emtno").trim();

				/*************************** 2.開始刪除資料 ***************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				edSvc.deleteEmtDispatch(edno);
				edListSvc.deleteEmtDispList(edno, emtno);

				/**************************** 3.刪除完成,準備轉交(Send the Success view)***********/
				// String url = "/dept/listAllDept.jsp";//我直接回傳呼叫他的網址了
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除調度單資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
	}
}