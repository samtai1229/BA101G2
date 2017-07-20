package com.motor_dispatch.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.motor_model.model.*;
import com.rent_ord.model.RentOrdVO;
import com.motor.model.*;
import com.motor_disp_list.model.MotorDispListService;
import com.motor_disp_list.model.MotorDispListVO;
import com.motor_dispatch.model.MotorDispatchService;
import com.motor_dispatch.model.MotorDispatchVO;

@WebServlet("/md.do")

public class MotorDispatchServlet extends HttpServlet {
	private static final long serialVersionUID = -240195811604764232L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("開始執行 MotorDispatchServlet.java");
		System.out.println("action: " + action);

		// chooseLoc
		if ("chooseLoc".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/********* 1.接收請求參數 - 輸入格式的錯誤處理 **********/
				String locno = req.getParameter("locno");
				System.out.println(locno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/******* 2.開始查詢資料 *******/
				System.out.println("getModtypeByLocno 開始查詢資料");
				MotorService motorSvc = new MotorService();

				// 以地點編號，取出該地點以外所有"車型modtype"並刪除重複
				HashSet<MotorVO> modtypeSet = motorSvc.getModtypeByLocNo(locno);

				if (modtypeSet == null) {
					errorMsgs.add("查無資料");
				}

				// 該地點以外所有車輛的modtype
				String modtypeFromGetByLocno = null;
				List<MotorVO> list = new ArrayList<MotorVO>();
				for (MotorVO motorVO : modtypeSet) {
					modtypeFromGetByLocno = motorVO.getModtype();
					System.out.println("modtypeFromGetByLocno: " + modtypeFromGetByLocno);
					list.addAll(motorSvc.getMotorsByModtypeAndLocno(modtypeFromGetByLocno, locno));
				}

				// 用modtype來取車輛

				String motno = null;
				String modtype = null;
				for (MotorVO aa : list) {
					motno = aa.getMotno();
					modtype = aa.getModtype();
					System.out.println("motno: " + motno);
					System.out.println("modtyp: " + modtype);
				}

				// if(status.equals("leasable")||status.equals("unleasable")||
				// roMotno != null){
				// req.setAttribute("dispatchableMotors", set);
				// }

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) ******/
				req.setAttribute("modtypeSet", modtypeSet);
				req.setAttribute("dispatchableMotors", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
				successView.forward(req, res);
				System.out.println("getModtypeByLocno成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("getModtypeByLocno失敗");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
				failureView.forward(req, res);
			}
		} // end of chooseLoc

		// insert
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

			try {
				/********** 1.接收請求參數 - 輸入格式的錯誤處理 ***************/
				String locno = req.getParameter("locno");
				String[] motnos = req.getParameterValues("motno");

				MotorDispatchVO mdVO = new MotorDispatchVO();
				MotorDispListVO mdListVO = new MotorDispListVO();
				MotorVO motorVO = new MotorVO();
				MotorDispatchService mdSvc = new MotorDispatchService();
				Set<MotorDispListVO> set = new HashSet<MotorDispListVO>();

				mdVO.setLocno(locno);
				for (String motno : motnos) {

					motorVO.setMotno(motno);
					mdListVO.setMotorVO(motorVO);
					mdListVO.setMotorDispatchVO(mdVO);
					set.add(mdListVO);
					mdVO.setMotorDispLists(set);

					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("mdVO", mdVO); // 含有輸入格式錯誤的VO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
						failureView.forward(req, res);
						return;
					}

					/************ 2.開始新增資料 ****************/

					mdSvc.insertByHib(mdVO);
				}

				// insert(申請)完調度單完立即查詢全部該據點調度單
				List<MotorDispatchVO> list = mdSvc.getByLocnoByHib(locno);

				if (list == null) {
					errorMsgs.add("查無資料");
				}

				Set<MotorDispListVO> motorDispLists = new HashSet<MotorDispListVO>();
				for (MotorDispatchVO mdVOtemp : list) {
					motorDispLists = mdVOtemp.getMotorDispLists();
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				} // end of "insert(申請)完調度單完立即查詢全部該據點調度單"

				/************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchForm.jsp"); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("insert 成功");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("insert 失敗");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
				failureView.forward(req, res);
			}
		} // end of insert

		// chooseLocForList
		if ("chooseLocForList".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");

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
				System.out.println("getByLocnoByHib 開始查詢資料");
				MotorDispatchService mdSvc = new MotorDispatchService();

				List<MotorDispatchVO> list = mdSvc.getByLocnoByHib(locno);

				if (list == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) ******/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchForm.jsp");
				successView.forward(req, res);
				System.out.println("getByLocnoByHib成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("getByLocnoByHib失敗");
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
			String url = "/backend/loc_motor_dispatch/locMotorDispatchForm.jsp";

			try {
				/************ 1.接收請求參數 - 輸入格式的錯誤處理 **************/
				String mdno = req.getParameter("mdno");
				String locno = req.getParameter("locno");
				if (mdno == null) {
					errorMsgs.add("無法接收到欲取消調度單資料");
				}
				MotorDispatchService mdSvc = new MotorDispatchService();
				MotorDispatchVO mdVO = new MotorDispatchVO();
				List<MotorDispatchVO> list = mdSvc.getByLocnoByHib(locno);
				mdVO.setMdno(mdno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mdVO", mdVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				/************ 2.開始新增資料 ***********************/
				System.out.println("開始 cancel");
				mdSvc.cancelByHib(mdno);

				/********** 3.更改完成,準備轉交(Send the Success view) *******/
				req.setAttribute("getByLocnoByHib", list);
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
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

		// getOne_For_Update
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/********* 1.接收請求參數 ***********/
				String mdno = req.getParameter("mdno");
				System.out.println("mdno" + mdno);
				/******** 2.開始查詢資料 **************/
				MotorDispatchService mdSvc = new MotorDispatchService();
				MotorDispatchVO mdVO = mdSvc.findByPkByHib(mdno);

				Set<MotorDispListVO> set = mdVO.getMotorDispLists();
				List<String> list = new ArrayList<String>();
				for (MotorDispListVO mdListVO : set) {
					String motno = mdListVO.getMotorVO().getMotno();
					list.add(motno);
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) *****/
				req.setAttribute("mdVO", mdVO);
				req.setAttribute("motnoInList", list);
				String url = "/backend/loc_motor_dispatch/updateMotorDispatch.jsp";
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
			System.out.println("requestURL :  " + requestURL);
			/************* 1.接收請求參數 - 輸入格式的錯誤處理 **************/
			String mdno = req.getParameter("mdno");
			String locno = req.getParameter("locno");
			String prog = req.getParameter("prog");
			String motnoArray[] = req.getParameterValues("motno");// 因為有複數motno
			System.out.println("111111111111: " + motnoArray.length);
			try {
				MotorDispatchVO mdVO = new MotorDispatchVO();
				MotorDispListVO mdListVO = new MotorDispListVO();
				Set<MotorDispListVO> motorDispListVOset = new HashSet<MotorDispListVO>();
				MotorVO motorVO = new MotorVO();
				MotorDispatchService mdSvc = new MotorDispatchService();
				MotorService motorSvc = new MotorService();
				MotorDispListService mdListSvc = new MotorDispListService();

				for (String motno : motnoArray) {

					String statusThisSec = motorSvc.findByPkByHib(motno).getStatus();
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
								"/backend/motor_dispatch/md.do?action=getOne_For_Update&mdno=" + mdno);
						failureView.forward(req, res);
						return;
					}
				}

				// 處理日期
				Timestamp filldate = null;
				Timestamp closeddate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					// filldate: 正常轉型、驗證格式
					System.out.println("filldate: " + req.getParameter("filldate"));

					// 因為我允許closedddate為空值，故，isNULL就不轉型不驗證。
					if (req.getParameter("closeddate").equals(null) || req.getParameter("closeddate").equals("null")) {
						System.out.println("closeddate = null");
					} else {
						// getTime = long 老吳：取得long就取得全世界
						// String 轉成 sdf(日期) 再轉成long
						long closeddateTypeLong = (sdf.parse(req.getParameter("closeddate").trim())).getTime();
						closeddate = new Timestamp(closeddateTypeLong);
					}

					long filldateTypeLong = (sdf.parse(req.getParameter("filldate").trim())).getTime();
					filldate = new Timestamp(filldateTypeLong);
				} catch (IllegalArgumentException e) {
					errorMsgs.add("請輸入日期");
				}

				// 先刪除調度單明細裡面全部的資料
				mdListSvc.deleteByHib(mdno);

				// 有幾台機車，我就執行幾次
				for (String motno : motnoArray) {
					mdVO.setMdno(mdno);// 調度單編號放入mdVO
					motorVO.setMotno(motno);// 車編放入motorVO
					mdListVO.setMotorVO(motorVO);// 把已放入車編的mototVO再放入mdListVO
					mdListVO.setMotorDispatchVO(mdVO);
					motorDispListVOset.add(mdListVO);// 將mdListVO放進
														// HashSet<MotorDispListVO>
					mdVO.setMotorDispLists(motorDispListVOset);// 將HashSet放進mdVO
					mdVO.setMdno(mdno);
					mdVO.setLocno(locno);
					mdVO.setProg(prog);
					mdVO.setFilldate(filldate);
					// closeddate isNotNull才放入mdVO
					if (req.getParameter("closeddate").equals(null) || req.getParameter("closeddate").equals("null")) {
					} else {
						mdVO.setCloseddate(closeddate);
					}
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("mdVO", mdVO); // 含有輸入格式錯誤的VO物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher(
								"/backend/motor_dispatch/md.do?action=getOne_For_Update&mdno=" + mdno);
						failureView.forward(req, res);
						return;
					}

					// 若調度單轉變為"調度中(dispatching)"，則將此單中所有車輛狀態也轉為dispatching
					if (prog.equals("dispatching")) {
						String status = "dispatching";
						System.out.println(status);
						motorSvc.updateStatusByHib(motno, status);
						// 若調度單轉變為"調度完成(dispatched)"，則將此單中所有車輛狀態也轉為unleasable
					} else if (prog.equals("dispatched")) {
						String status = "unleasable";
						System.out.println(status);
						motorSvc.updateStatusByHib(motno, status);
						// 若調度單設為結案，且結案日期內容為空值則結案日期colseddate設現在時間systime
					} else if (prog.equals("closed")
							|| prog.equals("canceled") && (req.getParameter("closeddate").equals(null)
									|| req.getParameter("closeddate").equals("null"))) {
						closeddate = new Timestamp(System.currentTimeMillis());
						mdVO.setCloseddate(closeddate);
					}

					/******************* 2.開始新增資料 ****************************/
					System.out.println("update start");
					// 開始執行MotorDispatchService的update方法
					mdSvc.updateByHib(mdVO);
				}

				/*************** 3.新增完成,準備轉交(Send the Success view) ***********/
				List<MotorDispatchVO> list = mdSvc.getAllByHib();
				req.setAttribute("list", list);
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/motorDispatchMgmtHq.jsp");
				successView.forward(req, res);
				System.out.println("update 成功");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("update失敗");
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_dispatch/md.do?action=getOne_For_Update&mdno=" + mdno);
				failureView.forward(req, res);
			}
		} // end of update

	}

}
