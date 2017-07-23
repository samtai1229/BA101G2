package com.location.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import org.slf4j.Marker;

import com.location.model.LocationService;
import com.location.model.LocationVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class LocationServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("getJson".equals(req.getParameter("action"))){
			LocationService locationSvc = new LocationService();
			List<LocationVO> list = locationSvc.getAll();
			LocationVO location = new LocationVO();
			location.setLocname(location.getLocname());
System.out.println("locname= "+location.getLocname());
			location.setAddr(location.getAddr());
			location.setLon(location.getLon());
			location.setLat(location.getLat());
			location.setTel(location.getTel());
			list.add(location);
			
            
			JSONArray array = new JSONArray();
			for(LocationVO locations:list){
				JSONObject obj = new JSONObject();
				try{
					obj.put("Locname", locations.getLocname());
					obj.put("Addr", locations.getAddr());
					obj.put("Lon", locations.getLon());
					obj.put("Lat", locations.getLat());
					obj.put("Tel", locations.getTel());
				}catch(Exception e){}
				array.add(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		
		
		
		
		if("marker".equals(action)){
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
			
			try{
				/******************************1.接收請求參數 - 輸入格式的錯誤處理*******************/
				LocationService locationSvc = new LocationService(); 
				String locno = req.getParameter("locno");
				System.out.println(locno);
				LocationVO locationVO = locationSvc.getOneLocation(locno);
				String locname = locationVO.getLocname();
				System.out.println("locname= "+locname);
				String addr = locationVO.getAddr();
				Float lon = locationVO.getLon();
				System.out.println(lon);
				Float lat = locationVO.getLat();
				System.out.println(lat);
				String tel = locationVO.getTel();
				
				/***************************2.查詢經緯度(Send the Success view)*************/
				req.setAttribute("locationVO", locationVO);
				System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
				req.setAttribute("lon", lon);
				System.out.println("XXXXXXXXXXXXXXXXXXXXXXX");
				req.setAttribute("lat", lat);
				System.out.println("YYYYYYYYYYYY!");
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				String url = "/frontend/location/locationOnly.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneLocation.jsp
				System.out.println("ZZZZZZZZZZZZZZZZZZZZ");
				successView.forward(req, res);
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("locno");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入據點編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/location/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String locno = null;
				try {
					locno = new String(str);
				} catch (Exception e) {
					errorMsgs.add("據點編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/location/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				LocationService locationSvc = new LocationService();
				LocationVO locationVO = locationSvc.getOneLocation(locno);
				if (locationVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/location/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("locationVO", locationVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/location/listOneLocation.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listOneLocation.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/location/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp 的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】		
			
			try {
				/***************************1.接收請求參數****************************************/
				String locno = req.getParameter("locno");
				
				/***************************2.開始查詢資料****************************************/
				LocationService locationSvc = new LocationService();
				LocationVO locationVO = locationSvc.getOneLocation(locno);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("locationVO", locationVO); // 資料庫取出的empVO物件,存入req
				String url = "/backend/location/update_location_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
//			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				LocationService locationSvc = new LocationService();
				LocationVO locationVO = new LocationVO();
				String locno = req.getParameter("locno").trim();
				String locname = req.getParameter("locname").trim();
				String tel = req.getParameter("tel").trim();
				String addr = req.getParameter("addr").trim();
				
				Float lon = new Float(req.getParameter("lon").trim());
				Float lat = new Float(req.getParameter("lat").trim());
				
				byte[] pic;
				if(req.getPart("pic").getSize()==0){
					locationVO = locationSvc.getOneLocation(locno);
					pic = locationVO.getPic();
				}else{
					pic = getPictureByteArrayFromWeb(req.getPart("pic"));
				}
				
				String status = req.getParameter("status").trim();
				
//				LocationVO locationVO = new LocationVO();
				locationVO.setLocno(locno);
				locationVO.setLocname(locname);
				locationVO.setTel(tel);
				locationVO.setAddr(addr);
				locationVO.setPic(pic);
				locationVO.setLon(lon);
				locationVO.setLat(lat);
				locationVO.setStatus(status);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("locationVO", locationVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/location/listAllLocation.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
//				LocationService locationSvc = new LocationService();
				locationVO = locationSvc.update(locno,locname, tel, addr, pic, lon, lat, status);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
				req.setAttribute("locationVO",locationVO);
				RequestDispatcher successView = req.getRequestDispatcher("/backend/location/listAllLocation.jsp");   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/location/listAllLocation.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String locno = req.getParameter("locno").trim();
				
//				if(locno==null || locno.length()==0){
//					errorMsgs.add("請輸入據點編號");
//				}
//				
				String locname = req.getParameter("locname").trim();
//				if(locname == null || locname.length() == 0){
//					errorMsgs.add("請輸入據點名稱");
//				}
				String tel = req.getParameter("tel").trim();
//				if(tel == null || tel.length() == 0){
//					errorMsgs.add("請輸入據點電話");
//				}
				String addr = req.getParameter("addr").trim();
//				if(addr == null || addr.length() == 0){
//					errorMsgs.add("請輸入據點地址");
//				}
				byte[] pic;
				LocationService locationSvc = new LocationService();
				LocationVO locationVO = new LocationVO();
				if(req.getPart("pic").getSize()==0){
					locationVO = locationSvc.getOneLocation(locno);
					pic = locationVO.getPic();
				}else{
					pic = getPictureByteArrayFromWeb(req.getPart("pic"));
				}
				
				Float lon = new Float(req.getParameter("lon").trim());
				if(lon==null || lon.isNaN(0f)){
					errorMsgs.add("請輸入據點地圖精度");
				}
				Float lat= new Float(req.getParameter("lat").trim());
				if(lat==null || lat.isNaN(0f)){
					errorMsgs.add("請輸入據點地圖緯度");
				}
				
//				String status = req.getParameter("status").trim();
				
				locationVO.setLocno(locno);
				locationVO.setLocname(locname);
				locationVO.setTel(tel);
				locationVO.setAddr(addr);
				locationVO.setPic(pic);
				locationVO.setLon(lon);
				locationVO.setLat(lat);
//				locationVO.setStatus(status);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("locationVO", locationVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/location/listAllLocation.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				locationVO = locationSvc.insert(locno,locname, tel, addr, pic, lon, lat);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/backend/location/listAllLocation.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/backend/location/listAllLocation.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				String locno = req.getParameter("locno").trim();

				if( locno == ""){
					errorMsgs.add("請選擇據點資料");
				}
				
				LocationVO locationVO = new LocationVO();
				locationVO.setLocno(locno);
				
				if(!errorMsgs.isEmpty()){
					req.setAttribute("locationVO", locationVO);
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始刪除資料***************************************/
				LocationService locationSvc = new LocationService();
				locationSvc.delete(locno);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
//				DeptService deptSvc = new DeptService();
//				if(requestURL.equals("/dept/listEmps_ByDeptno.jsp") || requestURL.equals("/dept/listAllDept.jsp"))
//					req.setAttribute("listEmps_ByDeptno",deptSvc.getEmpsByDeptno(empVO.getDeptno())); // 資料庫取出的list物件,存入request
				req.setAttribute("locationVO",locationVO);
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}

	public static byte[] getPictureByteArrayFromWeb(Part part) throws IOException {
		InputStream in = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int i;
		while((i=in.read(buf))!=-1){
			baos.write(buf,0,i);
		}
		baos.close();
		in.close();        
        return baos.toByteArray();
    }
}
