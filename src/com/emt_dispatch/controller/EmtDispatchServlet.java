package com.emt_dispatch.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emt_disp_list.model.EmtDispListService;
import com.emt_disp_list.model.EmtDispListVO;
import com.emt_dispatch.model.*;
import com.equipment.model.EquipmentService;
import com.equipment.model.EquipmentVO;
import com.motor.model.MotorService;
import com.motor.model.MotorVO;
import com.motor_disp_list.model.MotorDispListService;
import com.motor_disp_list.model.MotorDispListVO;
import com.motor_dispatch.model.MotorDispatchService;
import com.motor_dispatch.model.MotorDispatchVO;

public class EmtDispatchServlet extends HttpServlet {
	private static final long serialVersionUID = -2541820793843626192L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("開始執行 EmtDispatchServlet.java");
		System.out.println("action: " + action);

		// getOne_For_Display
		if ("getOne_For_Display".equals(action)) { // 來自EdMgmtSelectPage.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/****** 1.接收請求參數 - 輸入格式的錯誤處理 ******/
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

				String emtno = req.getParameter("emtno").trim();
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

				/****************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
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
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = "/backend/emt_dispatch/updateEmtDispatch.jsp";

			try {
				/********* 1.接收請求參數 ***********/
				String edno = req.getParameter("edno");
				System.out.println("edno" + edno);
				/******** 2.開始查詢資料 **************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispatchVO edVO = edSvc.findByPkByHib(edno);

				Set<EmtDispListVO> set = edVO.getEmtDispLists();
				List<String> list = new ArrayList<String>();
				for (EmtDispListVO edListVO : set) {
					String emtno = edListVO.getEquipmentVO().getEmtno();
					list.add(emtno);
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) *****/
				req.setAttribute("edVO", edVO);
				req.setAttribute("emtnoInList", list);
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				System.out.println("getOne_For_Update成功");
				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				errorMsgs.add("修改調度單取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				System.out.println("getOne_For_Update失敗");
			}
		} // end of getOne_For_Update

		// update(審核調度單
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String edno = req.getParameter("edno");
			String locno = req.getParameter("locno");
			String prog = req.getParameter("prog");
			String emtnoArray[] = req.getParameterValues("emtno");// 因為有複數emtno
			
			try {
				/************* 1.接收請求參數 - 輸入格式的錯誤處理 **************/
				EmtDispatchVO edVO = new EmtDispatchVO();
				EmtDispListVO edListVO = new EmtDispListVO();
				Set<EmtDispListVO> emtDispListVOset = new HashSet<EmtDispListVO>();
				EquipmentVO emtVO = new EquipmentVO();
				EmtDispatchService edSvc = new EmtDispatchService();
				EquipmentService emtSvc = new EquipmentService();
				EmtDispListService edListSvc = new EmtDispListService();

				for (String emtno : emtnoArray) {

					String statusThisSec = emtSvc.findByPkByHib(emtno).getStatus();
					System.out.println("statusThisSec " + statusThisSec);
					System.out.println("333333333333");

					if (statusThisSec.equals("reserved") || statusThisSec.equals("occupied")
							|| statusThisSec.equals("seconsale") || statusThisSec.equals("secpause")
							|| statusThisSec.equals("secreserved") || statusThisSec.equals("secsaled")
							|| statusThisSec.equals("other")) {
						System.out.println("55555555555");
						req.setAttribute("getAlert", "Yes"); // 含有輸入格式錯誤的empVO物件,也存入req
						// req.setAttribute("action", "getOne_For_Update");
						// req.setAttribute("mdno", mdno);
						RequestDispatcher failureView = req.getRequestDispatcher(
								"/backend/emt_dispatch/ed.do?action=getOne_For_Update&edno=" + edno);
						failureView.forward(req, res);
						return;
					}
				}
				
				
				// 處理日期
				Timestamp demanddate = null;
				Timestamp closeddate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					// demanddate: 正常轉型、驗證格式

					// 因為我允許closedddate為空值，故，isNULL就不轉型不驗證。
					if (req.getParameter("closeddate").trim().equals(null) || req.getParameter("closeddate").trim().equals("null")) {
						System.out.println("closeddate = null");
					} else {
						// getTime = long 老吳：取得long就取得全世界
						// String 轉成 sdf(日期) 再轉成long
						long closeddateTypeLong = (sdf.parse(req.getParameter("closeddate").trim())).getTime();
						closeddate = new Timestamp(closeddateTypeLong);
					}

					long demanddateTypeLong = (sdf.parse(req.getParameter("demanddate").trim())).getTime();
					demanddate = new Timestamp(demanddateTypeLong);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入日期");
				}

				
				// 先刪除調度單明細裡面全部的資料
				edListSvc.deleteByHib(edno);

				// 有幾件裝備，我就執行幾次
				for (String emtno : emtnoArray) {
					edVO.setEdno(edno);// 調度單編號放入edVO
					emtVO.setEmtno(emtno);// 裝備編號放入emtVO
					edListVO.setEquipmentVO(emtVO);// 把已放入裝備編號的emtVO再放入edListVO
					edListVO.setEmtDispatchVO(edVO);
					emtDispListVOset.add(edListVO);// 將edListVO放進HashSet<EmtDispListVO>
					edVO.setEmtDispLists(emtDispListVOset);// 將HashSet放進edVO
					edVO.setEdno(edno);
					edVO.setLocno(locno);
					edVO.setProg(prog);
					edVO.setDemanddate(demanddate);
					// closeddate isNotNull才放入edVO
					if (req.getParameter("closeddate").trim().equals(null) || req.getParameter("closeddate").trim().equals("null")) {
					} else {
						edVO.setCloseddate(closeddate);
					}
					
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("edVO", edVO); // 含有輸入格式錯誤的VO物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
						failureView.forward(req, res);
						return;
					}
System.out.println("prog: "  + prog);
					// 若調度單轉變為"調度中(dispatching)"，則將此單中所有裝備狀態也轉為dispatching
					if (prog.equals("dispatching")) {
						String status = "dispatching";
						System.out.println("prog is 'dispatching'");
						emtSvc.updateStatusByHib(emtno, status);
						// 若調度單轉變為"調度完成(dispatched)"，則將此單中所有裝備狀態也轉為unleasable
					} else if (prog.equals("dispatched")) {
						String status = "unleasable";
						System.out.println("prog is 'dispatched'");
						emtSvc.updateStatusByHib(emtno, status);
						// 若調度單設為結案，且結案日期內容為空值則結案日期colseddate設現在時間systime
					} else if (prog.equals("closed") || prog.equals("canceled") && (req.getParameter("closeddate").trim().equals(null)
							|| req.getParameter("closeddate").trim().equals("null"))) {
						System.out.println("prog is 'closed'");
						closeddate = new Timestamp(System.currentTimeMillis());
						edVO.setCloseddate(closeddate);
					}

					/******************* 2.開始新增資料 ****************************/
					System.out.println("update start");
					// 開始執行EmtDispatchService的update方法
					edSvc.updateByHib(edVO);
				}

				/*************** 3.新增完成,準備轉交(Send the Success view) ***********/
				List<EmtDispatchVO> list = edSvc.getAllByHib();
				req.setAttribute("list", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/emt_dispatch/emtDispatchMgmtHq.jsp");
				successView.forward(req, res);
				System.out.println("update 成功");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("update失敗");
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		} // end of update

		// insert
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String url = "/backend/emt_dispatch/locEmtDispatchApply.jsp";

			try {
				/********** 1.接收請求參數 - 輸入格式的錯誤處理 ***************/
				String locno = req.getParameter("locno");
				String[] emtnos = req.getParameterValues("emtno");

				EmtDispatchVO edVO = new EmtDispatchVO();
				EmtDispListVO edListVO = new EmtDispListVO();
				EquipmentVO emtVO = new EquipmentVO();
				EmtDispatchService edSvc = new EmtDispatchService();
				Set<EmtDispListVO> set = new HashSet<EmtDispListVO>();

				edVO.setLocno(locno);
				for (String emtno : emtnos) {

					emtVO.setEmtno(emtno);
					edListVO.setEquipmentVO(emtVO);
					edListVO.setEmtDispatchVO(edVO);
					set.add(edListVO);
					edVO.setEmtDispLists(set);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("edVO", edVO); // 含有輸入格式錯誤的VO物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher(url);
						failureView.forward(req, res);
						return;
					}
					/******** 2.開始新增資料 **********/

					edSvc.insertByHib(edVO);
				}

				// insert(申請)完調度單完立即查詢全部該據點調度單
				List<EmtDispatchVO> list = edSvc.getByLocnoByHib(locno);

				if (list == null) {
					errorMsgs.add("查無資料");
				}

				Set<EmtDispListVO> emtDispLists = new HashSet<EmtDispListVO>();
				for (EmtDispatchVO edVO2 : list) {
					emtDispLists = edVO2.getEmtDispLists();
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				} // end of "insert(申請)完調度單完立即查詢全部該據點調度單"

				/****** 3.新增完成,準備轉交(Send the Success view) *****/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/emt_dispatch/locEmtDispatchForm.jsp"); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("insert 成功");
				/******** 其他可能的錯誤處理 *****************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("insert 失敗");
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of insert

		// delete
		if ("delete_Ed".equals(action)) { // 來自/emt_dispatch/listAllEd.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String edno = req.getParameter("edno").trim();
				String emtno = req.getParameter("emtno").trim();

				/*************************** 2.開始刪除資料 ***************************************/
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispListService edListSvc = new EmtDispListService();
				edSvc.deleteEmtDispatch(edno);
				edListSvc.deleteEmtDispList(edno, emtno);

				/****************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除調度單資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of delete

		// chooseLoc
		if ("chooseLoc".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String url = "/backend/emt_dispatch/locEmtDispatchApply.jsp";

			try {
				/********* 1.接收請求參數 - 輸入格式的錯誤處理 **********/
				String locno = req.getParameter("locno");
				System.out.println(locno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/******* 2.開始查詢資料 *******/
				System.out.println("getEcnoByLocno 開始查詢資料");
				EquipmentService emtSvc = new EquipmentService();

				// 以地點編號，取出該地點以外所有"裝備類別編號ecno"並刪除重複
				List<String> ecnoList = emtSvc.getEcnoByLocnoByHib(locno);

				if (ecnoList == null) {
					errorMsgs.add("查無資料");
				}

				// 該地點以外所有裝備的ecno
				String ecno = null;
				List<EquipmentVO> list = new ArrayList<EquipmentVO>();
				for (String ecnoX : ecnoList) {
					ecno = ecnoX;
					System.out.println("ecno: " + ecno);
					list.addAll(emtSvc.getEmtsByEcnoAndLocnoByHib(ecno, locno));
				}
				System.out.println("list" + list);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) ******/
				req.setAttribute("ecnoList", ecnoList);
				req.setAttribute("dispatchableEmts", list);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				System.out.println("getEcnoByLocno成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("getEcnoeByLocno失敗");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of chooseLoc

		// chooseLocForList
		if ("chooseLocForList".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String url = "/backend/emt_dispatch/locEmtDispatchForm.jsp";

			try {
				/********* 1.接收請求參數 - 輸入格式的錯誤處理 **********/
				String locno = req.getParameter("locno");
				System.out.println(locno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/******* 2.開始查詢資料 *******/
				System.out.println("chooseLocForList 開始查詢資料");
				EmtDispatchService edSvc = new EmtDispatchService();

				List<EmtDispatchVO> list = edSvc.getByLocnoByHib(locno);

				if (list == null) {
					errorMsgs.add("查無資料");
				}

				Set<EmtDispListVO> emtDispLists = new HashSet<EmtDispListVO>();
				for (EmtDispatchVO edVO : list) {
					emtDispLists = edVO.getEmtDispLists();
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) ******/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				System.out.println("chooseLocForList 成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("chooseLocForList 失敗");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		} // end of chooseLocForList

		// cancel
		if ("cancel".equals(action)) {
			System.out.println("MotorServlet in cancel-action");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			String url = "/backend/emt_dispatch/locEmtDispatchForm.jsp";

			try {
				/************ 1.接收請求參數 - 輸入格式的錯誤處理 **************/
				String edno = req.getParameter("edno");
				String locno = req.getParameter("locno");
				if (edno == null) {
					errorMsgs.add("無法接收到欲取消裝備調度單資料");
				}
				EmtDispatchService edSvc = new EmtDispatchService();
				EmtDispatchVO edVO = new EmtDispatchVO();
				List<EmtDispatchVO> list = edSvc.getByLocnoByHib(locno);
				edVO.setEdno(edno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("edVO", edVO); // 含有輸入格式錯誤的edVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				/************ 2.開始新增資料 ***********************/
				System.out.println("開始 cancel");
				edSvc.cancelByHib(edno);

				/********** 3.更改完成,準備轉交(Send the Success view) *******/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				System.out.println("cancel 成功");

				/******************* 其他可能的錯誤處理 *********************/
			} catch (Exception e) {
				errorMsgs.add("資料取消失敗:" + e.getMessage());
				System.out.println("cancel 失敗");
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of cancel

	}
}