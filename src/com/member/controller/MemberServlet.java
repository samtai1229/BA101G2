package com.member.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.spots.model.*;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {

	public static byte[] getPicByteArray(Part part) throws IOException {
		InputStream fis = null;
		try {
			fis = part.getInputStream();
		} catch (IOException e) {

			e.printStackTrace();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("進來了 要做的是 "+action);
		if ("login".equals(action)) {
			System.out.println("Login~~~~~~~~~~~~~");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String acc = req.getParameter("acc");
				String pwd = req.getParameter("pwd");
				if (acc == null || pwd==null || (acc.trim()).length() == 0 || (pwd.trim()).length() == 0) {

					errorMsgs.add("帳號/密碼不可為空");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}


				/*************************** 2.開始比對帳密會員資料 *****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMemberByAccAndPwd(acc,pwd);

				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				else
				{
					if(!pwd.equals(memVO.getPwd()))
					{
						errorMsgs.add("帳號/密碼錯誤");
					}
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 * 轉交到會員專區的網頁
				 *************/

//				req.getSession().setAttribute("acc", acc);
//				req.getSession().setAttribute("pwd", pwd);
				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memno = req.getParameter("memno");

				if (memno == null || (memno.trim()).length() == 0) {

					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				try {
					if (memno.length() != 9 || !memno.contains("MEM"))
						throw new Exception();
				} catch (Exception e) {
					errorMsgs.add("會員編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/memeber/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMember(memno);

				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或
													// /dept/listEmps_ByDeptno.jsp
													// 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String memno = req.getParameter("memno");
				/*************************** 2.開始查詢資料 ****************************************/
				MemberService memSvc = new MemberService();
				MemberVO memVO = memSvc.getOneMember(memno);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("memVO", memVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/member/update_member_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ************************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String memno = req.getParameter("memno");
				String memname = req.getParameter("memname").trim();
				String sex = req.getParameter("sex");
				String acc = req.getParameter("acc");
				String pwd = req.getParameter("pwd");
				String addr = req.getParameter("addr");
				String phone = req.getParameter("phone");
				String mail = req.getParameter("mail");
				String status = req.getParameter("status");
				Timestamp birth = Timestamp.valueOf(req.getParameter("birth") + " 00:00:00");
				Part idcard1 = req.getPart("idcard1");
				Part idcard2 = req.getPart("idcard2");
				Part license = req.getPart("license");

				MemberVO memVO = new MemberVO();
				memVO.setMemname(memname);
				memVO.setAcc(acc);
				memVO.setPwd(pwd);
				memVO.setPhone(phone);
				memVO.setMail(mail);
				memVO.setAddr(addr);
				memVO.setIdcard1(getPicByteArray(idcard1));
				memVO.setIdcard2(getPicByteArray(idcard2));
				memVO.setLicense(getPicByteArray(license));
				memVO.setBirth(birth);
				memVO.setSex(sex);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/spots/update_spot_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				MemberService memSvc = new MemberService();
				 memVO = memSvc.update(memno, memname, sex, birth, mail, phone, addr, acc, pwd, getPicByteArray(idcard1), getPicByteArray(idcard2), getPicByteArray(license),status);

				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/backend/member/listOneMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/spots/update_spot_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addSpot.jsp的請求
			System.out.println("我要新增會員資料");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String memname = req.getParameter("memname").trim();
				String sex = req.getParameter("sex");
				String acc = req.getParameter("acc");
				String pwd = req.getParameter("pwd");
				String addr = req.getParameter("address");
				String phone = req.getParameter("phone");
				String mail = req.getParameter("mail");
				Timestamp birth = Timestamp.valueOf(req.getParameter("birth") + " 00:00:00");
				Part idcard1 = req.getPart("idcard1");
				Part idcard2 = req.getPart("idcard2");
				Part license = req.getPart("license");

				MemberVO memVO = new MemberVO();
				memVO.setMemname(memname);
				memVO.setAcc(acc);
				memVO.setPwd(pwd);
				memVO.setPhone(phone);
				memVO.setMail(mail);
				memVO.setAddr(addr);
				memVO.setIdcard1(getPicByteArray(idcard1));
				memVO.setIdcard2(getPicByteArray(idcard2));
				memVO.setLicense(getPicByteArray(license));
				memVO.setBirth(birth);
				memVO.setSex(sex);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				MemberService memSvc = new MemberService();
				memVO = memSvc.insert(memname, sex, birth, mail, phone, addr, acc, pwd, getPicByteArray(idcard1),
						getPicByteArray(idcard2), getPicByteArray(license), null);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				System.out.println("轉到全部會員清單");
				String url = "/backend/member/listAllMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/member/addMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

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
				/*************************** 1.接收請求參數 ***************************************/
				 String memno = req.getParameter("memno");

				/*************************** 2.開始刪除資料 ***************************************/
				MemberService mmSvc = new MemberService();

				mmSvc.delete(memno);;

				/***************************
				 * 3.刪除完成,準備轉交(Send the Success view)
				 ***********/
				// DeptService deptSvc = new DeptService();
				// if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") ||
				// requestURL.equals("/dept/listAllDept.jsp"))
				// req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno()));
				// // 資料庫取出的list物件,存入request
				//
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				//
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

	}

}
