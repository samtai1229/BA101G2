package com.rent_ord.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.equipment.model.EquipmentService;
import com.equipment.model.EquipmentVO;
import com.location.model.LocationService;
import com.location.model.LocationVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.motor.model.MotorService;
import com.motor.model.MotorVO;
import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;
import com.rent_ord.model.RentOrdService;
import com.rent_ord.model.RentOrdVO;

public class RentOrdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("RentOrdServlet in");
		
		
	
		
//return_form_parameter_setting
		if ("returnform_noreturn".equals(action) || 
				"returnform_overtime".equals(action)) {

			System.out.println("ro return_form_status_changer in");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String rentno = req.getParameter("rentno");
				System.out.println("rlocno:" + rentno);

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("return_form_status_changer - started");

				RentOrdService roSvc = new RentOrdService();
				RentOrdVO roQueryVO = roSvc.findByPK(rentno);

				Set<EquipmentVO> set = roSvc.getEquipmentVOsByRentno(rentno);
				
				String differDate = roSvc.differDateCalculator(rentno);
				
				MotorService motorSvc = new MotorService();
				MotorVO motorQueryVO = motorSvc.findByPK(roQueryVO.getMotno());

				MemberService memSvc = new MemberService();
				MemberVO memQueryVO = memSvc.getOneMember(roQueryVO.getMemno());

				LocationService locSvc = new LocationService();
				LocationVO slocQueryVO = locSvc.getOneLocation(roQueryVO.getSlocno());
				LocationVO rlocQueryVO = locSvc.getOneLocation(roQueryVO.getRlocno());

				MotorModelService mmSvc = new MotorModelService();
				MotorModelVO mmQueryVO = mmSvc.findByPK(motorQueryVO.getModtype());

				System.out.println("Set<EquipmentVO>.size()"+set.size());
				System.out.println("roQueryVO.getRentno(): " + roQueryVO.getRentno());

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/return.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/ // 資料庫取出的VO物件,存入req
				System.out.println("query-finished");
				req.setAttribute("roQueryVO", roQueryVO);
				req.setAttribute("memQueryVO", memQueryVO);
				req.setAttribute("motorQueryVO", motorQueryVO);
				req.setAttribute("slocQueryVO", slocQueryVO);
				req.setAttribute("rlocQueryVO", rlocQueryVO);
				req.setAttribute("mmQueryVO", mmQueryVO);
				req.setAttribute("get_equipmentVOs_by_rentno", set);
				req.setAttribute("differDate", differDate);

				if("returnform_noreturn".equals(action)){// 成功轉交
					req.getRequestDispatcher("/backend/rent_ord/returnform_noreturn.jsp").forward(req, res); 
				}else if("returnform_overtime".equals(action)){
					req.getRequestDispatcher("/backend/rent_ord/returnform_overtime.jsp").forward(req, res);
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("exception=================");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/return.jsp");
				failureView.forward(req, res);
			}
		} 
		// return_form_parameter_setting end		
		
		
		
		
		
//lease_form_parameter_setting
				if ("leaseform_available".equals(action) || 
					"leaseform_noshow".equals(action)||
					"leaseform_default".equals(action)) {

					System.out.println("ro lease_form_status_changer in");

					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/***************************
						 * 1.接收請求參數 - 輸入格式的錯誤處理
						 **********************/
						String rentno = req.getParameter("rentno");
						System.out.println("rlocno:" + rentno);

						/*************************** 2.開始查詢資料 *****************************************/
						System.out.println("lease_form_status_changer - started");

						RentOrdService roSvc = new RentOrdService();
						RentOrdVO roQueryVO = roSvc.findByPK(rentno);

						Set<EquipmentVO> set = roSvc.getEquipmentVOsByRentno(rentno);

						MotorService motorSvc = new MotorService();
						MotorVO motorQueryVO = motorSvc.findByPK(roQueryVO.getMotno());

						MemberService memSvc = new MemberService();
						MemberVO memQueryVO = memSvc.getOneMember(roQueryVO.getMemno());

						LocationService locSvc = new LocationService();
						LocationVO slocQueryVO = locSvc.getOneLocation(roQueryVO.getSlocno());
						LocationVO rlocQueryVO = locSvc.getOneLocation(roQueryVO.getRlocno());

						MotorModelService mmSvc = new MotorModelService();
						MotorModelVO mmQueryVO = mmSvc.findByPK(motorQueryVO.getModtype());

						System.out.println("Set<EquipmentVO>.size()"+set.size());
						System.out.println("roQueryVO.getRentno(): " + roQueryVO.getRentno());

						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp");
							failureView.forward(req, res);
							return;// 程式中斷
						}

						/***************************
						 * 3.查詢完成,準備轉交(Send the Success view)
						 *************/ // 資料庫取出的VO物件,存入req
						System.out.println("query-finished");
						req.setAttribute("roQueryVO", roQueryVO);
						req.setAttribute("memQueryVO", memQueryVO);
						req.setAttribute("motorQueryVO", motorQueryVO);
						req.setAttribute("slocQueryVO", slocQueryVO);
						req.setAttribute("rlocQueryVO", rlocQueryVO);
						req.setAttribute("mmQueryVO", mmQueryVO);
						req.setAttribute("get_equipmentVOs_by_rentno", set);

						if("leaseform_available".equals(action)){// 成功轉交
							req.getRequestDispatcher("/backend/rent_ord/leaseform_available.jsp").forward(req, res); 
						}else if("leaseform_noshow".equals(action)){
							req.getRequestDispatcher("/backend/rent_ord/leaseform_nowshow.jsp").forward(req, res);
						}else if("leaseform_default".equals(action)){
							req.getRequestDispatcher("/backend/rent_ord/leaseform_default.jsp").forward(req, res);
						}

						/*************************** 其他可能的錯誤處理 *************************************/
					} catch (Exception e) {
						System.out.println("exception=================");
						errorMsgs.add("無法取得資料:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp");
						failureView.forward(req, res);
					}
				} // lease_form_parameter_setting end
				
				

				
				
				
				
				
// after_noreturn_form & after_overtime_form
				if ("after_noreturn_form".equals(action)||
					"after_overtime_form".equals(action)) {
					//1. 處理租賃單狀態(milend, returndate, fine, rank, 'status', note)
					//2. 車輛狀態(mile, 'status')
					//3. 裝備狀態('status')
					

					System.out.println("ro after_noreturn_form in");

					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/***************************
						 * 1.接收請求參數
						 **********************/
						
						String rentno = req.getParameter("rentno");
						String milendStr = req.getParameter("milend").trim();
						String fineStr = req.getParameter("fine").trim();
						String rank = req.getParameter("rank");
						String motno = req.getParameter("motno");
						String note = req.getParameter("note");
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date date;
						long longTime;

						// 處理日期 returndate
						java.sql.Timestamp returndate = null;
						try {
							date = (java.util.Date) sdf.parse(req.getParameter("returndate"));// String
																								// to
																								// Date
							longTime = date.getTime(); // 取long
							returndate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
							System.out.println(longTime);
						} catch (ParseException e) {
							e.printStackTrace();
							errorMsgs.add("請輸入起始日期!");
						}
						
						Integer milend = null;
						try {
							milend = new Integer(milendStr);
						} catch (Exception e) {
							errorMsgs.add("結束里程請填數字");
						}
						
						
						Integer fine = null;
						try {
							fine = new Integer(fineStr);
						} catch (Exception e) {
							errorMsgs.add("罰金請填數字");
						}
						
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/return.jsp");
							failureView.forward(req, res);
							return;
						}
						
						
						RentOrdService roSvc = new RentOrdService();
				
						/*************************** 2.開始處理 *****************************************/
						
						//處理1.租賃單狀態、2. 車輛狀態
						
						
						if ("after_noreturn_form".equals(action)){
							roSvc.updateRentOrdAfterNoreturn(rentno, milend, returndate, fine, rank, note, action);
						}else if("after_overtime_form".equals(action)){
							roSvc.updateRentOrdAfterOvertime(rentno, milend, returndate, fine, rank, note, action);
						}
						
						roSvc.updateMotorAfterReturn(motno, milend, action);
						

						Enumeration<String> enumber = req.getParameterNames();
						//處理 3. 裝備狀態
						while(enumber.hasMoreElements()){
							String name = (String) enumber.nextElement();
							String values[] = req.getParameterValues(name);
							if(values!=null){
								for(int i=0;i<values.length;i++){
									System.out.println(name+"["+i+"]: "+values[i]);
									
									if(name.length()>4){
										//System.out.println("name.substring(0,5):"+ name.substring(0,5));
										if("emtno".equals(name.substring(0,5))){
											System.out.println("emtno found! value = "+values[i]);
											
											roSvc.updateEmtsAfterReturn(values[i], action);
										}
									}	
								}
							}
						}

						/***************************
						 * 3.處理完成,準備轉交(Send the Success view)
						 *************/ // 資料庫取出的VO物件,存入req

						RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/return.jsp"); // 成功轉交

						successView.forward(req, res);

						/*************************** 其他可能的錯誤處理 *************************************/
					} catch (Exception e) {
						System.out.println("RentOrdServlet exception=================");
						errorMsgs.add("無法取得資料:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/return.jsp");
						failureView.forward(req, res);
					}
				} // after_noreturn_form end				
				
				
		
		
		
//after_nowshow_form
		if ("after_nowshow_form".equals(action)||
			"after_available_form".equals(action)) {
			//1. 處理租賃單狀態、2. 車輛狀態、3. 裝備狀態

			System.out.println("ro after_nowshow_form in");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 **********************/
				
				String rentno = req.getParameter("rentno");
				String motno = req.getParameter("motno");
				String note = req.getParameter("note");
				
				System.out.println("NOTE = "+ note);
				
				RentOrdService roSvc = new RentOrdService();
		
				/*************************** 2.開始處理 *****************************************/
				
				//處理1.租賃單狀態、2. 車輛狀態
				
				if ("after_nowshow_form".equals(action)){
					roSvc.updateMotorStatusAfterNoshow(motno, action);
					roSvc.updateRentOrdStatusAfterNoshow(rentno, note, action);
				}else if("after_available_form".equals(action)){
					roSvc.updateMotorStatusAfterAvailable(motno, action);
					roSvc.updateRentOrdStatusAfterAvailable(rentno, note, action);
				}

				
				
				Enumeration<String> enumber = req.getParameterNames();
				//處理 3. 裝備狀態
				while(enumber.hasMoreElements()){
					String name = (String) enumber.nextElement();
					String values[] = req.getParameterValues(name);
					if(values!=null){
						for(int i=0;i<values.length;i++){
							System.out.println(name+"["+i+"]: "+values[i]);
							
							if(name.length()>4){
								//System.out.println("name.substring(0,5):"+ name.substring(0,5));
								if("emtno".equals(name.substring(0,5))){
									System.out.println("emtno found! value = "+values[i]);
									
									if ("after_nowshow_form".equals(action)){
										roSvc.updateEmtsStatusAfterNoshow(values[i], action);
									}else if("after_available_form".equals(action)){
										roSvc.updateEmtsStatusAfterAvailable(values[i], action);
									}		
								}
							}	
						}
					}
				}

				/***************************
				 * 3.處理完成,準備轉交(Send the Success view)
				 *************/ // 資料庫取出的VO物件,存入req

				RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp"); // 成功轉交

				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println("RentOrdServlet exception=================");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp");
				failureView.forward(req, res);
			}
		} // after_nowshow_form end
		
		

//// after_available_form
//		if ("after_available_form".equals(action)) {
//			//1. 處理租賃單狀態、2. 車輛狀態、3. 裝備狀態
//
//			System.out.println("ro after_available_form in");
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/***************************
//				 * 1.接收請求參數
//				 **********************/
//				
//				String rentno = req.getParameter("rentno");
//				String motno = req.getParameter("motno");
//				String note = req.getParameter("note");
//				
//				RentOrdService roSvc = new RentOrdService();
//		
//				/*************************** 2.開始處理 *****************************************/
//				
//				//處理1.租賃單狀態、2. 車輛狀態
//				roSvc.updateMotorStatusAfterLease(motno, action);
//				roSvc.updateRentOrdStatusAfterLease(rentno, note, action);
//				
//				
//				Enumeration<String> enumber = req.getParameterNames();
//				//處理 3. 裝備狀態
//				while(enumber.hasMoreElements()){
//					String name = (String) enumber.nextElement();
//					String values[] = req.getParameterValues(name);
//					if(values!=null){
//						for(int i=0;i<values.length;i++){
//							System.out.println(name+"["+i+"]: "+values[i]);
//							
//							if(name.length()>4){
//								//System.out.println("name.substring(0,5):"+ name.substring(0,5));
//								if("emtno".equals(name.substring(0,5))){
//									System.out.println("emtno found! value = "+values[i]);
//									
//									roSvc.updateEmtsStatusAfterLease(values[i], action);		
//								}
//							}	
//						}
//					}
//				}
//
//				/***************************
//				 * 3.處理完成,準備轉交(Send the Success view)
//				 *************/ // 資料庫取出的VO物件,存入req
//
//				RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp"); // 成功轉交
//
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				System.out.println("RentOrdServlet exception=================");
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/lease.jsp");
//				failureView.forward(req, res);
//			}
//		} // after_available_form end
		
		
		
		
		
		
		
		
		
		
		

		// query
		if ("query".equals(action) || "lease_ord_form".equals(action)) {

			System.out.println("ro query in");
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String rentno = req.getParameter("rentno");
				System.out.println("rentno:" + rentno);

				if (rentno == null || (rentno.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				RentOrdService roSvc = new RentOrdService();
				RentOrdVO roQueryVO = roSvc.findByPK(rentno);
				if (roQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("roQueryVO", roQueryVO); // 資料庫取出的VO物件,存入req
				System.out.println("roQueryVO.getRentno:" + roQueryVO.getRentno());
				System.out.println("action=" + action);
				if ("query".equals(action)) {
					RequestDispatcher successView = req
							.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp");

					successView.forward(req, res);
				} else if ("lease_ord_form".equals(action)) {
					RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/lease_ord_form.jsp");
					successView.forward(req, res);
				}

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp");
				failureView.forward(req, res);
			}
		} // query end

		// insert
		if ("insert".equals(action)) {
			System.out.println("RentOrdServlet in insert-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String memno = req.getParameter("memno").trim();
				String motno = req.getParameter("motno").trim();
				String slocno = req.getParameter("slocno").trim();
				String rlocno = req.getParameter("rlocno").trim();
				String note = req.getParameter("note").trim();

				String milstartStr = req.getParameter("milstart").trim();

				// 處理日期
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date;
				long longTime;

				// 處理日期 startdate
				java.sql.Timestamp startdate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("startdate"));// String
																						// to
																						// Date
					longTime = date.getTime(); // 取long
					startdate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入起始日期!");
				}

				// 處理日期 enddate
				java.sql.Timestamp enddate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("enddate"));// String
																					// to
																					// Date
					longTime = date.getTime(); // 取long
					enddate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入結束日期!");
				}

				// 處理integer milstart
				Integer milstart = null;
				try {
					milstart = new Integer(milstartStr);
				} catch (NumberFormatException e) {
					errorMsgs.add("起始里程數請寫數字");
				}

				RentOrdVO roVO = new RentOrdVO();
				roVO.setMemno(memno);
				roVO.setMotno(motno);
				roVO.setSlocno(slocno);
				roVO.setRlocno(rlocno);
				roVO.setMilstart(milstart);
				roVO.setStartdate(startdate);
				roVO.setEnddate(enddate);
				roVO.setNote(note);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roVO", roVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				RentOrdService roSvc = new RentOrdService();
				roVO = roSvc.addRentOrd(memno, motno, slocno, rlocno, milstart, startdate, enddate, note);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/backend/rent_ord/backendRentOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main insert catch in");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
				failureView.forward(req, res);
			}
		} // insert 'if' end

		// update
		if ("update".equals(action)) {
			System.out.println("RentOrdServlet in update-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String motno = req.getParameter("motno").trim();
				String slocno = req.getParameter("slocno").trim();
				String rlocno = req.getParameter("rlocno").trim();
				String rank = req.getParameter("rank").trim();
				String status = req.getParameter("status").trim();
				String note = req.getParameter("note").trim();
				String rentno = req.getParameter("rentno").trim();

				// 處理日期
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date;
				long longTime;

				// 處理日期 startdate
				java.sql.Timestamp startdate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("startdate"));// String
																						// to
																						// Date
					longTime = date.getTime(); // 取long
					startdate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入起始日期!");
				}

				// 處理日期 enddate
				java.sql.Timestamp enddate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("enddate"));// String
																					// to
																					// Date
					longTime = date.getTime(); // 取long
					enddate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println(longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					errorMsgs.add("請輸入結束日期!");
				}

				java.sql.Timestamp returndate = null;
				// 處理日期 logic: returndate日期可以暫時空白不填，但是要填的話就要填正確
				String returndateStr = req.getParameter("returndate").trim();
				if (returndateStr != null) {
					try {
						date = (java.util.Date) sdf.parse(returndateStr);// String
																			// to
																			// Date
						longTime = date.getTime(); // 取long
						returndate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
						System.out.println(longTime);
					} catch (ParseException e) {
						e.printStackTrace();
						errorMsgs.add("請輸入正確還車時間!");
					}
				}

				String milstartStr = req.getParameter("milstart").trim();
				String milendStr = req.getParameter("milend").trim();
				String fineStr = req.getParameter("fine").trim();
				String totalStr = req.getParameter("total").trim();

				Integer milstart = null;
				try {
					milstart = new Integer(milstartStr);
				} catch (Exception e) {
					errorMsgs.add("起始里程請填數字");
				}

				Integer milend = null;
				try {
					milend = new Integer(milendStr);
				} catch (Exception e) {
					errorMsgs.add("結束里程請填數字");
				}

				Integer fine = null;
				try {
					fine = new Integer(fineStr);
				} catch (Exception e) {
					errorMsgs.add("罰金請填數字");
				}

				Integer total = null;
				try {
					total = new Integer(totalStr);
				} catch (Exception e) {
					errorMsgs.add("總價請填數字");
				}

				// 合理版本: 去掉 filldate, memno
				// private static final String UPDATE = "UPDATE RENT_ORD set
				// motno=?,"
				// + " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?,
				// enddate=?,"
				// + "returndate=?, fine=?, total=?, rank=?, status=?, note=?
				// where rentno = ?";

				RentOrdVO roVO = new RentOrdVO();
				roVO.setMotno(motno);
				roVO.setSlocno(slocno);
				roVO.setRlocno(rlocno);
				roVO.setMilstart(milstart);
				roVO.setMilend(milend);
				roVO.setStartdate(startdate);
				roVO.setEnddate(enddate);
				roVO.setReturndate(returndate);
				roVO.setFine(fine);
				roVO.setTotal(total);
				roVO.setRank(rank);
				roVO.setStatus(status);
				roVO.setNote(note);
				roVO.setRentno(rentno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roVO", roVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("update start");
				/*************************** 2.開始新增資料 ***************************************/
				RentOrdService roSvc = new RentOrdService();
				roVO = roSvc.updateRentOrd(rentno, motno, slocno, rlocno, milstart, milend, startdate, enddate,
						returndate, fine, total, rank, status, note);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/backend/rent_ord/backendRentOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main update catch in");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
				failureView.forward(req, res);
			}
		} // update end

		// delete
		if ("delete".equals(action)) {
			System.out.println("RentOrdServlet in delete-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String rentno = req.getParameter("rentno");
				System.out.println(rentno);

				if (rentno == null) {
					errorMsgs.add("請選擇待刪車輛資料");
				}

				RentOrdVO roVO = new RentOrdVO();
				roVO.setRentno(rentno);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("delete_!errorMsgs.isEmpty_start");
					req.setAttribute("roVO", roVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("delete start");

				/*************************** 2.開始新增資料 ***************************************/
				RentOrdService roSvc = new RentOrdService();
				roSvc.delete(rentno);

				/***************************
				 * 3.更改完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/backend/rent_ord/backendRentOrd.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交?.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main delete catch in");
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/backendRentOrd.jsp");
				failureView.forward(req, res);
			}
		} // delete end

		// get_for_lease_view
		if ("get_for_lease_view".equals(action)) {
			System.out.println("======================================ro get_for_lease_view in");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String slocno = req.getParameter("slocno");
				System.out.println("slocno:" + slocno);
				if (slocno == null || (slocno.trim()).length() == 0) {
					errorMsgs.add("請輸入選擇地點");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_for_lease_view.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				RentOrdService roSvc = new RentOrdService();
				Set<RentOrdVO> set = roSvc.getForLeaseView(slocno);

				if (set == null) {
					errorMsgs.add("查無資料");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_for_lease_view.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("get_for_lease_view", set); // 資料庫取出的VO物件,存入req

				RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/get_for_lease_view.jsp");

				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/get_for_lease_view.jsp");
				failureView.forward(req, res);
			}
		} // get_for_lease_view end

		// get_for_return_view
		if ("get_for_return_view".equals(action)) {
			System.out.println("ro get_for_return_view in");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String rlocno = req.getParameter("rlocno");
				System.out.println("rlocno:" + rlocno);
				if (rlocno == null || (rlocno.trim()).length() == 0) {
					errorMsgs.add("請輸入選擇地點");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_for_return_view.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				RentOrdService roSvc = new RentOrdService();
				Set<RentOrdVO> set = roSvc.getForReturnView(rlocno);
				if (set == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/backend/rent_ord/get_for_return_view.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("get_for_return_view", set); // 資料庫取出的VO物件,存入req

				RequestDispatcher successView = req.getRequestDispatcher("/backend/rent_ord/get_for_return_view.jsp"); // 成功轉交

				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/backend/rent_ord/get_for_return_view.jsp");
				failureView.forward(req, res);
			}
		} // get_for_return_view end

		
		
		
		
		


	}
}
