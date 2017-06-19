package com.motor.controller;
import java.io.IOException;
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

public class MotorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("MotorServlet in");
		System.out.println("action = "+action);

		
//insert	
        if ("insert".equals(action)) { 
    		System.out.println("MotorServlet in insert-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String modtype = req.getParameter("modtype").trim();
				String plateno = req.getParameter("plateno").trim();
				String engno = req.getParameter("engno").trim();
				String locno = req.getParameter("locno").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();				
 
				//處理日期
				String dateString = req.getParameter("manudate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date;
				java.sql.Timestamp manudate=null;
				long longTime;
				try {
					date = (java.util.Date) sdf.parse(dateString);//String to Date
					longTime = date.getTime(); //取long
					manudate = new java.sql.Timestamp(longTime); //切為SQL專用格式	
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入日期!");
				} //處理日期--end
				
				
				
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
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				MotorService motorSvc = new MotorService();
				motorVO = motorSvc.addMotor(modtype, plateno, engno, manudate, mile, locno, status, note);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main insert catch in");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		}//insert 'if' end
        

//update	        
        if ("update".equals(action)) {
    		System.out.println("MotorServlet in update-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String modtype = req.getParameter("modtype").trim();
				String plateno = req.getParameter("plateno").trim();
				String engno = req.getParameter("engno").trim();
				String locno = req.getParameter("locno").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();
				String motno = req.getParameter("motno").trim();
				
				//處理日期
				String dateString = req.getParameter("manudate");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date;
				java.sql.Timestamp manudate=null;
				long longTime;
				try {
					date = (java.util.Date) sdf.parse(dateString);//String to Date
					longTime = date.getTime(); //取long
					manudate = new java.sql.Timestamp(longTime); //切為SQL專用格式	
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入日期!");
					System.out.println("err date in");
				} //處理日期--end
				
			
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
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("update start");
				/***************************2.開始新增資料***************************************/
				MotorService motorSvc = new MotorService();
				motorVO = motorSvc.updateMotor(motno, modtype, plateno, engno, manudate, mile, locno, status, note);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main update catch in");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		}//update 'if' end
        
        
//delete        
        if ("delete".equals(action)) { 
    		System.out.println("MotorServlet in delete-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String motno = req.getParameter("motno");
				System.out.println(motno);
			
				if(motno==null){
					errorMsgs.add("請選擇待刪車輛資料");
				}

				MotorVO motorVO = new MotorVO();
				motorVO.setMotno(motno);
			
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("delete_!errorMsgs.isEmpty_start");
					req.setAttribute("motorVO", motorVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("delete start");
				
				/***************************2.開始新增資料***************************************/
				MotorService motorSvc = new MotorService();
				motorSvc.deleteMotor(motno);
				
				/***************************3.更改完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main delete catch in");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		}//delete 'if' end
        
        
//query        
		if ("query".equals(action)) { 
			System.out.println("MotorServlet_query in");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String motno = req.getParameter("motno");
				System.out.println(motno);
				if (motno == null || (motno.trim()).length() == 0) {
					errorMsgs.add("請輸入車輛編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}

				/***************************2.開始查詢資料*****************************************/
				System.out.println("query-started");
				MotorService motorSvc = new MotorService();
				MotorVO motorQueryVO = motorSvc.findByPK(motno);
				if (motorQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				System.out.println("query-finished");
				req.setAttribute("motorQueryVO", motorQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getMotno());
				System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getStatus());
				
				String url = "/backend/motor/get_motor_by_pk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/get_motor_by_pk.jsp");
				failureView.forward(req, res);
			}
		}//delete 'if' end
		
		
//getOne_For_Display??        
		if ("getOne_For_Display".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String motno = req.getParameter("motno");
				System.out.println(motno);
				
				if (motno == null || (motno.trim()).length() == 0) {
					errorMsgs.add("請輸入車輛編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
								
				/***************************2.開始查詢資料*****************************************/
				System.out.println("query-started");
				MotorService motorSvc = new MotorService();
				MotorVO motorQueryVO = motorSvc.findByPK(motno);
				if (motorQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				System.out.println("query-finished");
				req.setAttribute("motorQueryVO", motorQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getMotno());
				System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getStatus());
				
				String url = "/backend/motor/backendMotor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/backendMotor.jsp");
				failureView.forward(req, res);
			}
		}//getOne_For_Display 'if' end		

		
//get_motors_by_modtype		
		if ("get_motors_by_modtype".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("action = "+action);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String modtype = req.getParameter("modtype");
				System.out.println(modtype);
				if (modtype == null || (modtype.trim()).length() == 0) {
					errorMsgs.add("請選擇車輛型號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				System.out.println("get_motors_by_modtype-started");
				MotorService motorSvc = new MotorService();
				Set<MotorVO> set = motorSvc.getMotorsByModelType(modtype);
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor/backendMotor.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				System.out.println("query-finished");
				req.setAttribute("get_motors_by_modtype", set); // 資料庫取出Set,存入req

				
				String url = "/backend/motor/get_motors_by_modtype.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor/get_motors_by_modtype.jsp");
				failureView.forward(req, res);
			}
		}//get_motors_by_modtype 'if' end	
	}

}
