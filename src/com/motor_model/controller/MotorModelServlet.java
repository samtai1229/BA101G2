package com.motor_model.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MotorModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

 
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("MotorModelServlet in");

		
//insert		
        if ("insert".equals(action)) { // 來自motor backendIndex.jsp的請求  
    		System.out.println("MotorModelServlet in insert-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			System.out.println("motor-model insert in");
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String brand = req.getParameter("brand").trim();
				String displacementStr = req.getParameter("displacement");
				String name = req.getParameter("name").trim();
				String renpriceStr = req.getParameter("renprice");
				String salepriceStr = req.getParameter("saleprice");	
				byte[] motpic = getPictureByteArrayFromWeb(req.getPart("motpic"));
				//直接處理圖片motpic，下面有part轉byte[]程式

				Integer displacement = null;
				try {
					displacement = new Integer(displacementStr);
				} catch (Exception e) {
					errorMsgs.add("排氣量格式不正確");
				}
				
				Integer renprice = null;
				try {
					renprice = new Integer(renpriceStr);
				} catch (Exception e) {
					errorMsgs.add("租賃價格格式不正確");
				}
				
				Integer saleprice = null;
				try {
					saleprice = new Integer(salepriceStr);
				} catch (Exception e) {
					errorMsgs.add("出售價格格式不正確");
				}

				MotorModelVO mmVO = new MotorModelVO();
/*				mmVO.setModtype(modtype);*/
				mmVO.setBrand(brand);
				mmVO.setDisplacement(displacement);
				mmVO.setName(name);
				mmVO.setRenprice(renprice);
				mmVO.setSaleprice(saleprice);
				
				//直接處理圖片，下面有part轉byte[]程式
				mmVO.setMotpic(motpic);
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				/***************************2.開始新增資料***************************************/
				MotorModelService mmSvc = new MotorModelService();
				mmVO = mmSvc.addMotorModel(brand, displacement, name, renprice, saleprice, motpic);
				
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor_model/backendMotorModel.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				System.out.println("motor-model insert update");
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("motor-model insert exception");
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
				failureView.forward(req, res);
			}
		}//insert 'if' end
        

//update        
        if ("update".equals(action)) { // 來自motor backendIndex.jsp的請求  
    		System.out.println("MotorServlet in update-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

/*			try {*/
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String modtype = req.getParameter("modtype").trim();
				String brand = req.getParameter("brand").trim();
				String displacementStr = req.getParameter("displacement");
				String name = req.getParameter("name").trim();
				String renpriceStr = req.getParameter("renprice");
				String salepriceStr = req.getParameter("saleprice");	

				byte[] motpic = getPictureByteArrayFromWeb(req.getPart("motpic"));
				//直接處理圖片motpic，下面有part轉byte[]程式
				
				Integer displacement = null;
				try {
					displacement = new Integer(displacementStr);
				} catch (Exception e) {
					errorMsgs.add("排氣量格式不正確");
				}
				
				Integer renprice = null;
				try {
					renprice = new Integer(renpriceStr);
				} catch (Exception e) {
					errorMsgs.add("租賃價格格式不正確");
				}
				
				Integer saleprice = null;
				try {
					saleprice = new Integer(salepriceStr);
				} catch (Exception e) {
					errorMsgs.add("出售價格格式不正確");
				}

				MotorModelVO mmVO = new MotorModelVO();
				
				mmVO.setModtype(modtype);
				mmVO.setBrand(brand);
				mmVO.setDisplacement(displacement);
				mmVO.setName(name);
				mmVO.setRenprice(renprice);
				mmVO.setSaleprice(saleprice);
				

				//直接處理圖片，下面有part轉byte[]程式
				mmVO.setMotpic(motpic);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("update start");
				/***************************2.開始新增資料***************************************/
				MotorModelService mmSvc = new MotorModelService();
				mmVO = mmSvc.updateMotorModel(modtype, brand, displacement, name, renprice, saleprice, motpic);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor_model/backendMotorModel.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
/*			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main update catch in");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
				failureView.forward(req, res);
			}*/
		}//update 'if' end
        
        
//delete            
        if ("delete".equals(action)) { // 來自motor backendIndex.jsp的請求  
    		System.out.println("MotorServlet in delete-action");			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String modtype = req.getParameter("modtype");
				System.out.println(modtype);
			
				if(modtype==null){
					errorMsgs.add("請選擇待刪車輛資料");
				}

				MotorModelVO mmVO = new MotorModelVO();
				mmVO.setModtype(modtype);
			
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("delete_!errorMsgs.isEmpty_start");
					req.setAttribute("mmVO", mmVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("delete start");
				
				/***************************2.開始新增資料***************************************/
				MotorModelService mmSvc = new MotorModelService();
				mmSvc.deleteMotorModel(modtype);
				
				/***************************3.更改完成,準備轉交(Send the Success view)***********/
				String url = "/backend/motor_model/backendMotorModel.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main delete catch in");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_model/backendMotorModel.jsp");
				failureView.forward(req, res);
			}
		}//delete 'if' end
        
        
//query         
		if ("query".equals(action)||"query_product_info".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
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
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
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
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				System.out.println("query-finished");
				req.setAttribute("mmQueryVO", mmQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("mmQueryVO.getModtype():"+mmQueryVO.getModtype());
				String url = "";
				if("query".equals(action)){
					url = "/backend/motor_model/get_motor_model_by_pk.jsp";
				}else if("query_product_info".equals(action)){
					url = "/frontend/rental_form/product_page.jsp";
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/motor_model/get_motor_model_by_pk.jsp");
				failureView.forward(req, res);
			}
		}//delete 'if' end

	}


	public static byte[] getPictureByteArrayFromWeb(Part part) throws IOException {
		  InputStream is = part.getInputStream();
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  byte[] buffer = new byte[8192];
		  int i;
		  while((i=is.read(buffer))!=-1){
		   baos.write(buffer, 0 , i);
		  }
		  baos.close();
		  is.close();
		  return baos.toByteArray();
		
	}

}
