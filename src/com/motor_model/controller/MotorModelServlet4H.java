package com.motor_model.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;

@WebServlet("/motorModel4H.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MotorModelServlet4H extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("MotorModelServlet in");
		System.out.println("action: " + action);

// insert
		if ("insert".equals(action)) {
			System.out.println("MotorModelServlet in insert-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the
			// ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/******** 1.接收請求參數 - 輸入格式的錯誤處理 ***************/
				String brand = req.getParameter("brand").trim();
				String name = req.getParameter("name").trim();
				String intro = req.getParameter("intro").trim();

				Integer displacement = null;
				try {
					displacement = new Integer(req.getParameter("displacement").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("排氣量請寫數字");
				}

				Integer renprice = null;
				try {
					renprice = new Integer(req.getParameter("renprice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("租賃價格請寫數字");
				}

				Integer saleprice = null;
				try {
					saleprice = new Integer(req.getParameter("saleprice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("出售價格請寫數字");
				}

				byte[] motpic = null;
				try {
					Part part = req.getPart("motpic");
					motpic = GetPictureByteArrayFromWeb.getPictureByteArrayFromWeb(part);
				} catch (Exception e) {
					errorMsgs.add("圖片格式不正確");
				}

				MotorModelVO mmVO = new MotorModelVO();
				mmVO.setBrand(brand);
				mmVO.setDisplacement(displacement);
				mmVO.setName(name);
				mmVO.setRenprice(renprice);
				mmVO.setSaleprice(saleprice);
				mmVO.setMotpic(motpic);
				mmVO.setIntro(intro);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor_model/addMotorModel.jsp");
					failureView.forward(req, res);
				}

				/**************** 2.開始新增資料 *************************/
				MotorModelService mmSvc = new MotorModelService();
				mmVO = mmSvc.addMotorModel(brand, displacement, name, renprice, saleprice, motpic, intro);
				/********** 3.新增完成,準備轉交(Send the Success view) *******/
				req.setAttribute("inserted", "inserted");
				RequestDispatcher successView = req.getRequestDispatcher("/backend/motor_model/listAllMotorModel.jsp"); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("insert成功");

				/************** 其他可能的錯誤處理 ********************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("insert失敗");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/motor_model/addMotorModel.jsp");
				failureView.forward(req, res);
			}
		} // end of insert

// getOne_For_Update
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			String requestURL = req.getParameter("requestURL");

			try {
				/*************** 1.接收請求參數 **********************/
				String modtype = req.getParameter("modtype").trim();
				System.out.println("modtype: " + modtype);
				/*************** 2.開始查詢資料 *******************/
				MotorModelService mmSvc = new MotorModelService();
				MotorModelVO mmVO = mmSvc.findByPK(modtype);

				/******** 3.查詢完成,準備轉交(Send the Success view)*******/
				req.setAttribute("mmVO", mmVO);
				String url = "/backend/motor_model/updateMotorModelInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交updateMotorInput.jsp
				successView.forward(req, res);
				System.out.println("getOne_For_Update成功");
				/************* 其他可能的錯誤處理 ******************/
			} catch (Exception e) {
				errorMsgs.add("修改車輛資料取出時失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				System.out.println("getOne_For_Update失敗");
			}
		} // end of getOne_For_Update

// update
		if ("update".equals(action)) { // 來自motor backendIndex.jsp的請求
			System.out.println("MotorServlet in update-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String url = "/backend/motor_model/updateMotorModelInput.jsp";

			try {
				/********** 1.接收請求參數 - 輸入格式的錯誤處理 ****************/
				String modtype = req.getParameter("modtype").trim();
				String brand = req.getParameter("brand").trim();
				String name = req.getParameter("name").trim();
				String intro = req.getParameter("intro").trim();

				Integer displacement = null;
				try {
					displacement = new Integer(req.getParameter("displacement").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("排氣量請寫數字");
				}

				Integer renprice = null;
				try {
					renprice = new Integer(req.getParameter("renprice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("租賃價格請寫數字");
				}

				Integer saleprice = null;
				try {
					saleprice = new Integer(req.getParameter("saleprice").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("出售價格請寫數字");
				}

				byte[] motpic = null;
				Part part = req.getPart("motpic");
				try {
					motpic = GetPictureByteArrayFromWeb.getPictureByteArrayFromWeb(part);
				} catch (Exception e) {
					errorMsgs.add("圖片格式不正確");
				}

				MotorModelVO mmVO = new MotorModelVO();
				mmVO.setBrand(modtype);
				mmVO.setBrand(brand);
				mmVO.setDisplacement(displacement);
				mmVO.setName(name);
				mmVO.setRenprice(renprice);
				mmVO.setSaleprice(saleprice);
				mmVO.setMotpic(motpic);
				mmVO.setIntro(intro);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				MotorModelService mmSvc = new MotorModelService();
				MotorModelVO mmVO2 = mmSvc.findByPK(modtype);
				
				byte[] defaultPic = mmVO2.getMotpic();
				System.out.println("part = " + GetPictureByteArrayFromWeb.getFileNameFromPart(part) != null);
				
				if(GetPictureByteArrayFromWeb.getFileNameFromPart(part) != null){
					mmVO = mmSvc.updateMotorModel(modtype, brand, displacement, name, renprice, saleprice, motpic, intro);
				}else 
				mmVO = mmSvc.updateMotorModel(modtype, brand, displacement, name, renprice, saleprice, defaultPic , intro);
				
				/********* 3.新增完成,準備轉交(Send the Success view) ***********/
				RequestDispatcher successView = req.getRequestDispatcher("/backend/motor_model/listAllMotorModel.jsp"); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("update 成功");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("update 失敗");
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of update

		// delete
		if ("delete".equals(action)) {
			System.out.println("MotorServlet in delete-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String url = "/backend/motor_model/listAllMotorModel.jsp";

			try {
				/********** 1.接收請求參數 - 輸入格式的錯誤處理 **********/
				String modtype = req.getParameter("modtype");
				System.out.println(modtype);

				MotorModelVO mmVO = new MotorModelVO();
				mmVO.setModtype(modtype);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				/**************** 2.開始新增資料 *************************/
				MotorModelService mmSvc = new MotorModelService();
				mmSvc.deleteMotorModel(modtype);

				/*********** 3.更改完成,準備轉交(Send the Success view) ***********/

				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);
				System.out.println("delete 成功");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("delete 失敗");
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} // end of delete

		// query
		if ("query".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String modtype = req.getParameter("modtype");
				System.out.println(modtype);
				if (modtype == null || (modtype.trim()).length() == 0) {
					errorMsgs.add("請輸入車輛型號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/get_motor_model_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/get_motor_model_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				MotorModelService mmSvc = new MotorModelService();
				MotorModelVO mmQueryVO = mmSvc.findByPK(modtype);
				if (mmQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/get_motor_model_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("mmQueryVO", mmQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("mmQueryVO.getModtype():" + mmQueryVO.getModtype());

				String url = "/backend/motor_model/get_motor_model_by_pk.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// Emp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_model/get_motor_model_by_pk.jsp");
				failureView.forward(req, res);
			}
		} // delete 'if' end
	}
}
