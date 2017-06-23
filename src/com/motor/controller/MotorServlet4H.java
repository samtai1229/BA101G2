package com.motor.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.motor.model.MotorService;
import com.motor.model.MotorVO;

public class MotorServlet4H extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("MotorServlet in");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxaction = " + action);

		// insert
		if ("insert".equals(action)) {
			System.out.println("MotorServlet in insert-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String modtype = req.getParameter("modtype").trim();
				String plateno = req.getParameter("plateno").trim();
				String engno = req.getParameter("engno").trim();
				String locno = req.getParameter("locno").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();

				// 處理日期
				Timestamp manudate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				try {
					//String 轉成 sdf(日期) 再轉成long
					System.out.println(req.getParameter("manudate"));
					long manudateTypeLong = (sdf.parse(req.getParameter("manudate").trim())).getTime();
					manudate = new Timestamp(manudateTypeLong);
				} catch (IllegalArgumentException e) {
					manudate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer mile = null;
				try {
					mile = new Integer(req.getParameter("mile").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("里程數請寫數字");
				}

				MotorVO motorVO = new MotorVO();
				motorVO.setModtype(modtype);
				motorVO.setPlateno(plateno);
				motorVO.setEngno(engno);
				motorVO.setLocno(locno);
				motorVO.setManudate(manudate);
				motorVO.setMile(mile);
				motorVO.setNote(note);
				motorVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("motorVO", motorVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				MotorService motorSvc = new MotorService();
				motorVO = motorSvc.addMotor(modtype, plateno, engno, manudate, mile, locno, status, note);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main insert catch in");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		} // insert 'if' end

// update
		if ("update".equals(action)) {
			System.out.println("MotorServlet in update-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");	
			System.out.println("requestURL :  "+requestURL);
			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String modtype = req.getParameter("modtype").trim();
				String plateno = req.getParameter("plateno").trim();
				String engno = req.getParameter("engno").trim();
				String locno = req.getParameter("locno").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();
				String motno = req.getParameter("motno").trim();
				
				// 處理日期
				Timestamp manudate = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				try {
					//getTime = long 老吳：取得long就取得全世界
					//String 轉成 sdf(日期) 再轉成long
					System.out.println(req.getParameter("manudate"));
					long manudateTypeLong = (sdf.parse(req.getParameter("manudate").trim())).getTime();
					manudate = new Timestamp(manudateTypeLong);
				} catch (IllegalArgumentException e) {
					manudate = new Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入日期");
				}

				Integer mile = null;
				try {
					mile = new Integer(req.getParameter("mile").trim());
				} catch (NumberFormatException e) {
					System.out.println("err mile in");
					errorMsgs.add("里程數請寫數字");
				}
				
				MotorVO motorVO = new MotorVO();
				motorVO.setMotno(motno);
				motorVO.setModtype(modtype);
				motorVO.setPlateno(plateno);
				motorVO.setEngno(engno);
				motorVO.setLocno(locno);
				motorVO.setManudate(manudate);
				motorVO.setMile(mile);
				motorVO.setNote(note);
				motorVO.setStatus(status);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("motorVO", motorVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				System.out.println("update start");
				/*************************** 2.開始新增資料 ***************************************/
				MotorService motorSvc = new MotorService();
				motorVO = motorSvc.updateMotor(motno, modtype, plateno, engno, manudate, mile, locno, status, note);
				System.out.println("motorVO"+motorVO);
				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(requestURL); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main update catch in"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		} // update 'if' end
		
//getOne_For_Update
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");		
			
			try {
				/***************************1.接收請求參數****************************************/
				String motno = req.getParameter("motno").trim();
				System.out.println("motno" + motno);
				/***************************2.開始查詢資料****************************************/
				MotorService motorSvc = new MotorService();
				MotorVO motorVO = motorSvc.findByPK(motno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("motorVO", motorVO); // 資料庫取出的motorVO物件,存入req
				String url = "/backend/motor/updateMotorInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交updateMotorInput.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改車輛資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}//end of getOne_For_Update

// delete
		if ("delete".equals(action)) {
			System.out.println("MotorServlet in delete-action");
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
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String motno = req.getParameter("motno");
				System.out.println(motno);

				if (motno == null) {
					errorMsgs.add("請選擇待刪車輛資料");
				}

				MotorVO motorVO = new MotorVO();
				motorVO.setMotno(motno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("delete_!errorMsgs.isEmpty_start");
					req.setAttribute("motorVO", motorVO); // 含有輸入格式錯誤的empVO物件,也存入req
					String url = requestURL;
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				System.out.println("delete start");

				/*************************** 2.開始新增資料 ***************************************/
				MotorService motorSvc = new MotorService();
				motorSvc.deleteMotor(motno);

				/***************************
				 * * 3.更改完成,準備轉交(Send the Success view)
				 ***********/
				if (requestURL.equals("/backend/motor/listAllMotor.jsp")
						|| requestURL.equals("/backend/motor/motorMgmtHqSelectPage.jsp"))
					req.setAttribute("listAllMotor", motorSvc.findByPK(motorVO.getMotno())); // 資料庫取出的list物件,存入request

				if (requestURL.equals("/backend/motor/motorMgmtHqSelectPage.jsp")) {
					List<MotorVO> list = motorSvc.getAll();
					req.setAttribute("listAllMotor", list);
				}

				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				System.out.println("err main delete catch in");
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		} // delete 'if' end

		// query
		if ("query".equals(action)) {
			System.out.println("MotorServlet_query in");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String motno = req.getParameter("motno");
				System.out.println(motno);
				if (motno == null || (motno.trim()).length() == 0) {
					errorMsgs.add("請輸入車輛編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				MotorService motorSvc = new MotorService();
				MotorVO motorQueryVO = motorSvc.findByPK(motno);
				if (motorQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("motorQueryVO", motorQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("motnoVO.motorQueryVO:" + motorQueryVO.getMotno());
				System.out.println("motnoVO.motorQueryVO:" + motorQueryVO.getStatus());

				String url = "/backend/motor/get_motor_by_pk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// Emp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
				failureView.forward(req, res);
			}
		} // delete 'if' end

		// getOne_For_Display??
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String motno = req.getParameter("motno");
				System.out.println(motno);

				if (motno == null || (motno.trim()).length() == 0) {
					errorMsgs.add("請輸入車輛編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				MotorService motorSvc = new MotorService();
				MotorVO motorQueryVO = motorSvc.findByPK(motno);
				if (motorQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("motorQueryVO", motorQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("motnoVO.motorQueryVO:" + motorQueryVO.getMotno());
				System.out.println("motnoVO.motorQueryVO:" + motorQueryVO.getStatus());

				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// Emp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		} // getOne_For_Display 'if' end

		// get_motors_by_modtype
		if ("get_motors_by_modtype".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("action = " + action);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String modtype = req.getParameter("modtype");
				System.out.println(modtype);
				if (modtype == null || (modtype.trim()).length() == 0) {
					errorMsgs.add("請選擇車輛型號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("get_motors_by_modtype-started");
				MotorService motorSvc = new MotorService();
				Set<MotorVO> set = motorSvc.getMotorsByModelType(modtype);
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("get_motors_by_modtype", set); // 資料庫取出Set,存入req

				String url = "/backend/motor/get_motors_by_modtype.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// Emp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor/get_motors_by_modtype.jsp");
				failureView.forward(req, res);
			}
		} // get_motors_by_modtype 'if' end

//include listAllMotor.jsp
		// 來自MotorMgmtHqSelectPage.jsp的請求 // 來自 motor/listAllMotor.jsp的請求
		if ("listAllMotor_A".equals(action) || "listAllMotor_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				// String motno = new String(req.getParameter("motno"));

				/*************************** 2.開始查詢資料 ****************************************/
				MotorService motorSvc = new MotorService();
				List<MotorVO> list = motorSvc.getAll();

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/

				req.setAttribute("listAllMotor", list); // 資料庫取出的list物件,存入request

				String url = null;
				if ("listAllMotor_A".equals(action))
					url = "/backend/motor/listAllMotor.jsp"; // 成功轉交
																// dept/listEmps_ByDeptno.jsp
				else if ("listAllMotor_B".equals(action))
					url = "/backend/motor/motorMgmtHqSelectPage.jsp"; // 成功轉交
																		// dept/listAllDept.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

//include updateMotorInput.jsp
		if ("updateMotorInput".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				//------ 1.接收請求參數
				String motno = new String(req.getParameter("motno"));

				//------2.開始查詢資料 
				MotorService motorSvc = new MotorService();
				List<MotorVO> list = motorSvc.getAll();

				//------3.查詢完成,準備轉交(Send the Success view)

				req.setAttribute("updateMotorInput", list); // 資料庫取出的list物件,存入request

				String url = "/backend/motor/motorMgmtHqSelectPage.jsp"; 

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}

}
