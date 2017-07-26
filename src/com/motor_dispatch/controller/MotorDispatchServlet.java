package com.motor_dispatch.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.motor_model.model.*;
import com.rent_ord.model.RentOrdService;
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
			MotorDispatchService mdSvc = new MotorDispatchService();
			MotorService motorSvc = new MotorService();
			RentOrdService roSvc = new RentOrdService();
			RentOrdVO roVO = new RentOrdVO();

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

				// 以地點編號，取出該地點以外所有"車型modtype"並刪除重複
				HashSet<MotorVO> modtypeSet = motorSvc.getModtypeByLocNo(locno);

				if (modtypeSet == null) {// 錯誤處理
					errorMsgs.add("查無資料");
				}

				// 該地點以外所有車輛的modtype
				String modtypeFromGetByLocno = null;
				List<MotorVO> getMotorsByModtypeAndLocnoInList = new ArrayList<MotorVO>();
				for (MotorVO motorVO : modtypeSet) {
					modtypeFromGetByLocno = motorVO.getModtype();
					System.out.println("modtypeFromGetByLocno: " + modtypeFromGetByLocno);
					// getMotorsByModtypeAndLocno(modtypeFromGetByLocno,
					// locno)：取出來的MotorVO符合以下兩個條件：1."此據點以外"
					// 2.狀態屬於"leasable"+"unleasable"的車輛
					getMotorsByModtypeAndLocnoInList
							.addAll(motorSvc.getMotorsByModtypeAndLocno(modtypeFromGetByLocno, locno));
				}

				// 用modtype來取車輛
				List<MotorVO> dispatchableMotorsInList = new ArrayList<MotorVO>();
				for (MotorVO aa : getMotorsByModtypeAndLocnoInList) {
					String motno = null;
					String modtype = null;
					motno = aa.getMotno();
					modtype = aa.getModtype();
					System.out.println("motno: " + motno);
					System.out.println("modtyp: " + modtype);
					
					Set<RentOrdVO> nullCheck = new HashSet<RentOrdVO>();// 只是拿來做檢查:roSvc.getBymotno(motno)取出來的roVO是不是null
					System.out.println("Answer: " + roSvc.getBymotno(motno).equals(nullCheck));
					if (roSvc.getBymotno(motno).equals(nullCheck)) {
						// 如果rentOrdVO是null，這台車從沒有出現在租賃單中，表示可調度。
						dispatchableMotorsInList.add(motorSvc.findByPkByHib(motno));
					} else if (mdSvc.checkDispatchableMotors(motno) != null) {
						// 如果rentOrdVO不是null，這台車曾經在租賃單中，必須再繼續下一個檢查。
						roVO = mdSvc.checkDispatchableMotors(motno);// 取出來的RentOrdVO符合：雖然在租賃單中但預約時間不在現在時間加3天
						dispatchableMotorsInList.add(motorSvc.findByPkByHib(roVO.getMotorVO().getMotno()));// 將RentOrdVO轉成MotorVO再放進list2中
					} else {
						System.out.println("表示：mdSvc.checkDispatchableMotors(motno) == null");
					}
				}

				for (MotorVO bb : dispatchableMotorsInList) {
					System.out.println(bb.getMotno());
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/********* 3.查詢完成,準備轉交(Send the Success view) ******/
				req.setAttribute("modtypeSet", modtypeSet);
				req.setAttribute("dispatchableMotorsInList", dispatchableMotorsInList);
				
				RequestDispatcher successView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
				successView.forward(req, res);
				System.out.println("getModtypeByLocno成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("getModtypeByLocno失敗");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println(e.getMessage());
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
				System.out.println("chooseLocForList 開始查詢資料");
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
				System.out.println("chooseLocForList成功");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("chooseLocForList失敗");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchForm.jsp");
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
				System.out.println("mdno: " + mdno);
				/******** 2.開始查詢資料 **************/
				MotorDispatchService mdSvc = new MotorDispatchService();
				MotorService motorSvc = new MotorService();
				RentOrdService roSvc = new RentOrdService();
				MotorDispatchVO mdVO = mdSvc.findByPkByHib(mdno);
				RentOrdVO roVO = new RentOrdVO();

				Set<MotorDispListVO> set = mdVO.getMotorDispLists();
				List<String> motnoInList = new ArrayList<String>();
				List<MotorVO> dispatchableMotorsInList = new ArrayList<MotorVO>();
				for (MotorDispListVO mdListVO : set) {
					String motno = mdListVO.getMotorVO().getMotno();
					motnoInList.add(motno);
					Set<RentOrdVO> nullCheck = new HashSet<RentOrdVO>();// 只是拿來做檢查:roSvc.getBymotno(motno)取出來的roVO是不是null
					if (roSvc.getBymotno(motno).equals(nullCheck)) {
						// 如果rentOrdVO是null，這台車從沒有出現在租賃單中，表示可調度。
						dispatchableMotorsInList.add(motorSvc.findByPkByHib(motno));
					} else if (mdSvc.checkDispatchableMotors(motno) != null) {
						// 如果rentOrdVO不是null，這台車曾經在租賃單中，必須再繼續下一個檢查。
						roVO = mdSvc.checkDispatchableMotors(motno);// 取出來的RentOrdVO符合：雖然在租賃單中但預約時間不在現在時間加3天
						dispatchableMotorsInList.add(motorSvc.findByPkByHib(roVO.getMotorVO().getMotno()));// 將RentOrdVO轉成MotorVO再放進list2中
					} else {
						System.out.println("表示：mdSvc.checkDispatchableMotors(motno) == null");
					}
				}

				/********* 3.查詢完成,準備轉交(Send the Success view) *****/

				List<RentOrdVO> unDispatchableMotorsInList = new ArrayList<>();
				unDispatchableMotorsInList = mdSvc.checkUndispatchableMotors();
				System.out.println("asgds;jkg;awig;owaejig;kajws;ksfd;s: " + unDispatchableMotorsInList);
				for (RentOrdVO aa : unDispatchableMotorsInList) {
					System.out.println("aaaaaaaaaaaaaaaaaaaaa: " + aa.getMotorVO().getMotno());
				}
				req.setAttribute("mdVO", mdVO);
				req.setAttribute("motnoInList", motnoInList);
				req.setAttribute("dispatchableMotorsInList", dispatchableMotorsInList);
				req.setAttribute("unDispatchableMotorsInList", unDispatchableMotorsInList);
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
			System.out.println("requestURL: "+requestURL);
			/************* 1.接收請求參數 - 輸入格式的錯誤處理 **************/
			String mdno = req.getParameter("mdno");
			String locno = req.getParameter("locno");
			String prog = req.getParameter("prog");
			String motnoArray[] = req.getParameterValues("motno");// 因為有複數motno

			try {
				MotorDispatchVO mdVO = new MotorDispatchVO();
				MotorDispListVO mdListVO = new MotorDispListVO();
				Set<MotorDispListVO> motorDispListVOset = new HashSet<MotorDispListVO>();
				MotorVO motorVO = new MotorVO();
				MotorDispatchService mdSvc = new MotorDispatchService();
				MotorService motorSvc = new MotorService();
				MotorDispListService mdListSvc = new MotorDispListService();
				RentOrdService roSvc = new RentOrdService();

				for (String motno : motnoArray) {
					System.out.println("motno: " + motno);
					String statusThisSec = motorSvc.findByPkByHib(motno).getStatus();
					System.out.println("statusThisSec " + statusThisSec);
					Set<RentOrdVO> nullCheck = new HashSet<RentOrdVO>();// 只是拿來做檢查:roSvc.getBymotno(motno)取出來的roVO是不是null
					if (statusThisSec.equals("reserved") || statusThisSec.equals("occupied")
							|| statusThisSec.equals("seconsale") || statusThisSec.equals("secpause")
							|| statusThisSec.equals("secreserved") || statusThisSec.equals("secsaled")
							|| statusThisSec.equals("other")) {

						req.setAttribute("getAlert", "Yes"); // 含有輸入格式錯誤的empVO物件,也存入req

						RequestDispatcher failureView = req.getRequestDispatcher(
								"/backend/motor_dispatch/md.do?action=getOne_For_Update&mdno=" + mdno);
						failureView.forward(req, res);
						return;
					} else if (!roSvc.getBymotno(motno).equals(nullCheck)
							&& mdSvc.checkDispatchableMotors(motno) == null) {
						req.setAttribute("getAlert", "Yes");
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
						String status = "dispatching";// 機車的狀態
						System.out.println(status);
						motorSvc.updateStatusByHib(motno, status);
						// 若調度單轉變為"調度完成(dispatched)"，則將此單中所有車輛狀態也轉為unleasable
					} else if (prog.equals("dispatched")) {
						String status = "unleasable";// 機車的狀態
						System.out.println(status);
						motorSvc.updateStatusByHib(motno, status);
						motorSvc.updateLocnoByHib(motno, locno);
						closeddate = new Timestamp(System.currentTimeMillis());
						mdVO.setCloseddate(closeddate);
						mdVO.setProg("closed");
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
				if(requestURL.equals("/BA101G2/backend/loc_motor_dispatch/locMotorDispatchForm.jsp")){
					List<MotorDispatchVO> list2 = mdSvc.getByLocnoByHib(locno);
					req.setAttribute("getByLocnoByHib", list2);
					RequestDispatcher successView = req.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchForm.jsp");
					successView.forward(req, res);
					System.out.println("update 成功");
					return;
				}
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
