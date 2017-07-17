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
		System.out.println("開始執行MotorDispatchServlet");
		System.out.println("action: " + action);

// chooseLoc
		if ("chooseLoc".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("action = " + action);
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
				Set<MotorDispListVO> set = new HashSet<MotorDispListVO>();
				
				mdVO.setLocno(locno);
				for(String motno : motnos){
					
					motorVO.setMotno(motno);
					mdListVO.setMotorVO(motorVO);
					mdListVO.setMotorDispatchVO(mdVO);
					set.add(mdListVO);
					mdVO.setMotorDispLists(set);
					
					// Send the use back to the form, if there were errors
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("mdVO", mdVO); // 含有輸入格式錯誤的VO物件,也存入req
						RequestDispatcher failureView = req.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
						failureView.forward(req, res);
						return;
					}
					
					/************ 2.開始新增資料 ****************/
					MotorDispatchService mdSvc = new MotorDispatchService();
					mdSvc.insertByHib(mdVO);
				}
				
				/************** 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp"); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("insert 成功");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("insert 失敗");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/loc_motor_dispatch/locMotorDispatchApply.jsp");
				failureView.forward(req, res);
			}
		} // insert 'if' end
	}

}
