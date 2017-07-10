package com.emt_cate.controller;


import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.emt_cate.model.*;
import com.equipment.model.*;
import com.motor_model.controller.GetPictureByteArrayFromWeb;
import com.motor_model.model.MotorModelVO;

@WebServlet("/ec.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class EmtCateServlet extends HttpServlet {
	private static final long serialVersionUID = -6205327103041308017L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println("啟動EmtCateServlet");
		System.out.println("action: " + action);

		
// getOne_For_Display
		if ("getOne_For_Display".equals(action)) { // 來自ecMgmtSelectPage.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("ecno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String ecno = "";
				try {
					ecno = str;
				} catch (Exception e) {
					errorMsgs.add("裝備類別編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				EmtCateService ecSvc = new EmtCateService();
				EmtCateVO ecVO = ecSvc.getOneEmtCate(ecno);
				if (ecVO == null) {
					errorMsgs.add("查無裝備類別資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("ecVO", ecVO); // 資料庫取出的ecVO物件,存入req
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEc.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得裝備類別資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// list all equipment by ECNO
		    // 來自eqptMgmtSelectPage.jsp的請求               // 來自 equipment/listAllEqpts.jsp的請求
		if ("listEqpts_ByEcno_A".equals(action) || "listEqpts_ByEcno_B".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String ecno = req.getParameter("ecno");

				/*************************** 2.開始查詢資料 ****************************************/
				EmtCateService ecSvc = new EmtCateService();
				Set<EquipmentVO> set = ecSvc.getEqptsByEcno(ecno);

				/**************************** 3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listEqpts_ByEcno", set); // 資料庫取出的list物件,存入request

				String url = null;
				if ("listEqpts_ByEcno_A".equals(action))
					url = "/backend/emt_cate/listEqpts_ByEcno.jsp"; // 成功轉交emt_cate/listEqpts_ByEcno.jsp
				else if ("listEqpts_ByEcno_B".equals(action))
					url = "/backend/emt_cate/listAllEc.jsp"; // 成功轉交emt_cate/listAllEc.jsp

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

// getOne_For_Update
		if ("getOne_For_Update".equals(action)) { // 來自listAllEc.jsp 或/emt_cate/listEc_ByEcno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:可能為【/emt_cate/listAllEc.jsp】或【/dept/listEmps_ByDeptno.jsp】或 【/dept/listAllDept.jsp】

			try {
				/*************** 1.接收請求參數 **************/
				String ecno = req.getParameter("ecno");

				/*********** 2.開始查詢資料 ********************/
				EmtCateService ecSvc = new EmtCateService();
				EmtCateVO ecVO = ecSvc.getOneEmtCate(ecno);

				/******* 3.查詢完成,準備轉交(Send the Success view)********/
				req.setAttribute("ecVO", ecVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/emt_cate/updateEcInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_ec_input.jsp
				successView.forward(req, res);
				
				/************ 其他可能的錯誤處理 **************/
			} catch (Exception e) {
				errorMsgs.add("修改裝備類別資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

// update
		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑:可能為【/emt_cate/listAllEc.jsp】或【/emt_cate/listEc_ByEcno.jsp】或 【/emt_cate/listAllEc.jsp】
			String url = requestURL;
			try {
				/**************************** 1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ecno = req.getParameter("ecno").trim();
				String type = req.getParameter("type").trim();
				
				Integer price = null;
				try {
					price = new Integer(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					price = 0;
					errorMsgs.add("租賃價格限填數字.");
				}
				
				byte[] pic = null;
				Part part = req.getPart("pic");
				try {
					pic = GetPictureByteArrayFromWeb.getPictureByteArrayFromWeb(part);
				} catch (Exception e) {
					errorMsgs.add("圖片格式不正確");
				}

				EmtCateVO ecVO = new EmtCateVO();
				ecVO.setEcno(ecno);
				ecVO.setType(type);
				ecVO.setPrice(price);
				ecVO.setPic(pic);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ecVO", ecVO); // 含有輸入格式錯誤的ecVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				EmtCateService ecSvc = new EmtCateService();
				EmtCateVO ecVO2 = ecSvc.getOneEmtCate(ecno);
				
				byte[] defaultPic = ecVO2.getPic();
				System.out.println("part = " + GetPictureByteArrayFromWeb.getFileNameFromPart(part) != null);
				
				if(GetPictureByteArrayFromWeb.getFileNameFromPart(part) != null){
					ecVO = ecSvc.updateEmtCate(ecno, type, pic, price);
				}else 
				ecVO = ecSvc.updateEmtCate(ecno, type, defaultPic, price);


				/**************************** 3.修改完成,準備轉交(Send the Success view)*************/
				//暫時用不到
				// if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(deptno));
				// // 資料庫取出的list物件,存入request

				RequestDispatcher successView = req.getRequestDispatcher("/backend/emt_cate/listAllEcs.jsp"); // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);
				System.out.println("update 成功");
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("update 失敗");
				errorMsgs.add("修改裝備類別資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}

// insert
		if ("insert".equals(action)) { // 來自addEc.jsp的請求
System.out.println("EmtCate insert start!");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/************************ 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String type = req.getParameter("type").trim();

				Integer price = null;
				try {
					price = new Integer(req.getParameter("price").trim());
				} catch (NumberFormatException e) {
					price = 0;
					errorMsgs.add("租賃價格限填數字.");
				}
				
				byte[] pic = null;
				try {
					Part part = req.getPart("pic");
					pic = GetPictureByteArrayFromWeb.getPictureByteArrayFromWeb(part);
				} catch (Exception e) {
					errorMsgs.add("圖片格式不正確");
				}

				EmtCateVO ecVO = new EmtCateVO();
				ecVO.setType(type);
				ecVO.setPrice(price);
				ecVO.setPic(pic);
				System.out.println("3323");

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ecVO", ecVO); // 含有輸入格式錯誤的ecVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/emt_cate/addEc.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EmtCateService ecSvc = new EmtCateService();
				ecVO = ecSvc.addEmtCate(type, pic, price);

				/**************************** 3.新增完成,準備轉交(Send the Success view)***********/

				RequestDispatcher successView = req.getRequestDispatcher("/backend/emt_cate/listAllEcs.jsp"); // 新增成功後轉交listAllEcs.jsp
				successView.forward(req, res);
				System.out.println("insert 成功");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/emt_cate/addEc.jsp");
				failureView.forward(req, res);
			}
		}// end of insert

// delete
		if ("delete".equals(action)) { // 來自/emt_cate/listAllEc.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");
			String url = requestURL;

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String ecno = req.getParameter("ecno");

				/*************************** 2.開始刪除資料 ***************************************/
				EmtCateService ecSvc = new EmtCateService();
				ecSvc.deleteEmtCate(ecno);

				/**************************** 3.刪除完成,準備轉交(Send the Success view)***********/
				// String url = "/dept/listAllDept.jsp";//我直接回傳呼叫他的網址了
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除裝備類別資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
	}
}
