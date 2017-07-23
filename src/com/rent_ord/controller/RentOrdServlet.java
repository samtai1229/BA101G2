package com.rent_ord.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emt_cate.model.EmtCateService;
import com.emt_list.model.EmtListService;

import com.rent_ord.model.EquipmentForRentOrdVO;
import com.rent_ord.model.MotorForRentOrdVO;
import com.rent_ord.model.RentOrdVO;
import com.rent_ord.model.EquipmentForRentOrdService;
import com.rent_ord.model.MotorForRentOrdService;
import com.rent_ord.model.RentOrdService;

import com.location.model.LocationService;
import com.location.model.LocationVO;
import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;



public class RentOrdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action="";
		if(!"login".equals(req.getParameter("action")))
			action = req.getParameter("action");
		else
			action = (String) req.getAttribute("action");
		
		
		String location = req.getParameter("location");
		System.out.println("我從:"+location+"   而來");
		System.out.println("RentOrdServlet in");
		System.out.println("action: "+action);
		
		String exceptionURL = "/index.jsp";

	if ("redirect_to_login".equals(action)){
		String url ="/Login.jsp";
		String motno = req.getParameter("motno");
		String dayrange = req.getParameter("confirmed_rentday");
		
		HttpSession session = req.getSession();
		
		session.setAttribute("motno", motno);
		session.setAttribute("dayrange", dayrange);		
		session.setAttribute("action", "rental");
		
		req.getRequestDispatcher(url).forward(req, res); // 轉去登入畫面
	}

	

//query_product_info 從我要租車->選定某車款後進入。
		if ("query_product_info".equals(action)) { 
			System.out.println("query_product_info in");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String modtype = req.getParameter("modtype");
				
				//??沒有dayrange


				/***************************2.開始查詢資料*****************************************/
				//找到這modtype接下來兩個月內空檔率最高的車的motno ->motorQueryVO
				//用這VO反推一段有空的時間當做dayrange
				//
				
				//找到3天後日期，再找60天後日期
				java.util.Date du = new java.util.Date();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(du);		
				
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				cal.add(Calendar.DATE, 3);//+3天    
				Date query_start_time = cal.getTime();
				
				cal.add(Calendar.DATE, 3);//+3天    
//				Date tempEnd = cal.getTime();
				
				cal.add(Calendar.DATE, 54);//+60天  
				System.out.println("query_start_time: "+query_start_time);
				Date query_end_time = cal.getTime();
				System.out.println("query_end_time: "+query_end_time);

				
				
				// 處理日期 startdate to SQL
				java.sql.Timestamp startdate;
				startdate = new java.sql.Timestamp(query_start_time.getTime());
				System.out.println("startdate"+startdate);

				// 處理日期 enddate to SQL
				java.sql.Timestamp enddate;
				enddate = new java.sql.Timestamp(query_end_time.getTime());
				System.out.println("enddate"+enddate);
				
				//做一個集滿60天數的list =>dayPlayer 用來取得安全的預設天數
				
				String dayP = "";
				List<String> dayPlayer = new ArrayList<String>();
					dayP = startdate.toString().substring(0, 10);
					
					while(startdate.before(enddate)){
						startdate = plusOneDayMethod(startdate);
						dayP = startdate.toString().substring(0, 10);
						dayPickerMethod(dayP, dayPlayer);
					}
					System.out.println("dayPlayer :"+dayPlayer);
				


				//1.用modtype找到所有的motno
				MotorForRentOrdService motorSvc = new MotorForRentOrdService();
				RentOrdService roSvc = new RentOrdService();
				MotorForRentOrdService mfroSvc = new MotorForRentOrdService();
				
				Set<RentOrdVO> roVOset = new LinkedHashSet<RentOrdVO>();
				Set<RentOrdVO> roVOtemp = new LinkedHashSet<RentOrdVO>();
				
				List<String> motnos = new ArrayList<String>();
				motnos = mfroSvc.getMotnosByModelType(modtype);
				System.out.println("motnos.size(): "+motnos.size());

				//2.挑租單，,做個DAO把後兩個月內會遇到的租單都找出來。 條件為 motno , startday(+3), endday(+60)
				//租單以租用天數累計，選最少的那一台車.

				int minday =65;
				String motno = "";
				for(String motnoTemp: motnos){
					String statusTemp = motorSvc.findByPK(motnoTemp).getStatus();
					
					//新增條件，車輛目前狀態要是租單方才可以選進.(不包含unleasable)
					if("leasable".equals(statusTemp)||"reserved".equals(statusTemp)||"occupied".equals(statusTemp)){
						int totalday =0;
						roVOtemp = roSvc.getRoVOsByDatePrioidAndMotno(startdate, enddate, motnoTemp);
						for(RentOrdVO tempVO :roVOtemp ){
							if(startdate.getTime()>tempVO.getStartdate().getTime()){
								totalday += (int) ((tempVO.getEnddate().getTime() - startdate.getTime())/(1000*60*60*24))+1;
							}
							else if(enddate.getTime()<tempVO.getEnddate().getTime()){
								totalday += (int) ((enddate.getTime() - tempVO.getStartdate().getTime())/(1000*60*60*24))+1;
							}
							else{
								totalday += (int) ((tempVO.getEnddate().getTime() - tempVO.getStartdate().getTime())/(1000*60*60*24))+1;
							}
						}
						if(minday>totalday){
							minday = totalday;
							motno = motnoTemp;
							roVOset = roVOtemp;
							System.out.println("inter room, minday= "+ minday+" totalday: "+totalday+ " motno = "+motno);
							System.out.println("in roVOset.size(): "+roVOset.size());
						}
					}
				}
				System.out.println("out roVOset.size(): "+roVOset.size());
				System.out.println("minday: "+minday);
				System.out.println("motno: "+motno);

				MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(motno);
				Set<RentOrdVO> set = roSvc.getBymotno(motno);
				System.out.println("set: "+set.size());
				
				Timestamp startday;
				Timestamp endday;
				String dayPicker = "";
				List<String> dayPickerList = new ArrayList<String>();
	 			for(RentOrdVO roVO: set){

	// 用 Calindar幫忙處理timestamp格式，加完時間後再丟回timestamp.
					endday = roVO.getEnddate();
					startday = roVO.getStartdate();
					dayPicker = startday.toString().substring(0, 10);
					System.out.println("dayPicker :"+dayPicker);
					dayPickerMethod(dayPicker, dayPickerList);
					
					while(startday.before(endday)){
						
						startday = plusOneDayMethod(startday);
						dayPicker = startday.toString().substring(0, 10);
						
						dayPickerMethod(dayPicker, dayPickerList);
					}
					//System.out.println("dayPicker :"+dayPicker);
				}
	 			
	 			
	 			StringBuilder str= new StringBuilder();
	 			for(String temp: dayPickerList){
	 				System.out.println("dayPickerlist:" + temp);
	 				str.append(temp+" ");
	 			}
	 			dayPicker = str.toString().trim();
	 			System.out.println("dayPicker"+ dayPicker);
	 			
	 			
	 			String defday = "";
	 			
	 			for(String temp2: dayPlayer){
	 				int count = 0;
	 				for(String temp1: dayPickerList){
		 				if(temp2.equals(temp1)){
		 					count++;
		 				}

		 			}
	 				if(count==0){
	 					System.out.println("in count==0, defday= "+temp2);
	 					defday=temp2;
	 					break;
	 				}
	 				
	 			}
	 			System.out.println("1.defday= "+defday);
	 			
	 			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 			Date date = sdf.parse(defday);
	 			System.out.println("2"+date);
	 			
	 			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy H:mm");
	 			defday = sdf2.format(date);
	 			System.out.println("3"+defday);
	 			
	 			System.out.println("==========");

				/***************************3.查詢完成,準備轉交(Send the Success view)*************/

				req.setAttribute("motorQueryVO", motorQueryVO);
				System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getMotno());
				
				req.setAttribute("dayPicker", dayPicker);
				req.setAttribute("defday", defday);
				
				
				String url = "/frontend/rental_form/quick_search_product4.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				System.out.println("RentOrdServlet exception=========j========");
				RequestDispatcher failureView = req
						.getRequestDispatcher(exceptionURL);
				failureView.forward(req, res);
			}
		} 
		// query_product_info end	

	
	
//quick_search_credit_card	
	if ("quick_search_credit_card".equals(action)||
		"quick_search_money_transfer".equals(action)){ 
		
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		System.out.println("quick_search_credit_card in");


		try {
			/***************************1.接收請求參數 **********************/
			
//		    final String INSERT_STMT = "INSERT INTO RENT_ORD"
//			+ " (rentno, memno, motno, slocno, rlocno, startdate, enddate, total "
//			+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//			+ "  ?, ?, ?)";			
			
			HttpSession session = req.getSession();
			//session 有memno
			String memno = (String) session.getAttribute("memno");
			String motno = req.getParameter("motno");
			String slocno = req.getParameter("slocno");
			String rlocno = req.getParameter("rlocno");
			String totalday = req.getParameter("totaldate");
			String emtno_list_str = req.getParameter("emtno_list_str");
			Integer total = Integer.parseInt(req.getParameter("total"));
			String status = "";
			
			System.out.println("end emtno_list_str: "+emtno_list_str);
			
			System.out.println("getParam==> memno: "+memno+" motno: "+motno+" slocno: "+slocno+
					" rlocno: "+rlocno+" emtno_list_str: "+emtno_list_str);
			
			if("quick_search_credit_card".equals(action))
				status = "unoccupied";
			else
				status = "unpaid";
			
			
			// 處理日期
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy H:mm");
			java.util.Date date;
			long longTime;

			String startdateStr = req.getParameter("startdate");
			// 處理日期 startdate
			java.sql.Timestamp startdate = null;
			try {
				date = (java.util.Date) sdf.parse(startdateStr);;// String to Date
				longTime = date.getTime(); // 取long
				startdate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("startdate"+startdate);
			} catch (ParseException e) {
				e.printStackTrace();
				errorMsgs.add("請輸入起始日期!");
			}

			String enddateStr = req.getParameter("enddate");
			// 處理日期 enddate
			java.sql.Timestamp enddate = null;
			try {
				date = (java.util.Date) sdf.parse(enddateStr);// String to Date
				longTime = date.getTime(); // 取long
				enddate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("enddate"+enddate);
			} catch (ParseException e) {
				e.printStackTrace();
				errorMsgs.add("請輸入結束日期!");
			}
			/***************************2.開始查詢資料*****************************************/
			//要處理的有:
			//1.處理emtno_list_str
			//2.新增租賃單
			//3.新增租賃單裝備明細

			//1
			String [] emtnos = {""};
			
			if(emtno_list_str!=""){
				emtnos = emtno_list_str.split(" ");
			}
			
			List<String> emtnoList = java.util.Arrays.asList(emtnos);
			for(String emtno : emtnoList)
				System.out.println("emtno:" + emtno);

			//2.
			RentOrdService roSvc = new RentOrdService();
			roSvc.addRentOrd(memno, motno, slocno, rlocno, startdate, enddate, total, status);
			
			
			
			//3.
			
			//反查RENTNO =>可改用自增主鍵?
			String rentno = roSvc.getRentnoByMemnoAndStartdate(memno, startdate);
			System.out.println("rentno = " + rentno);
			if(emtno_list_str!=""){
				EmtListService elSvc = new EmtListService();
			
				for(String emtno : emtnos){
					elSvc.addEmtList(rentno, emtno);
					System.out.println("rentno:"+rentno+" emtno:"+emtno+" add to emtlist");
				}
			}
			
			RentOrdVO roQueryVO = roSvc.findByPK(rentno);

			/***************************3.查詢完成,準備轉交(Send the Success view)*************/
			
			System.out.println("end totalday"+totalday);
			System.out.println("startdateStr"+startdateStr);
			System.out.println("enddateStr"+enddateStr);

			req.setAttribute("roQueryVO", roQueryVO);
			req.setAttribute("emtno_list_str", emtno_list_str);
			req.setAttribute("startday", startdateStr);
			req.setAttribute("endday", enddateStr);
			req.setAttribute("totalday", totalday);
			req.setAttribute("emtnoList", emtnoList);
			req.setAttribute("slocno", slocno);
			req.setAttribute("rlocno", rlocno);
			req.setAttribute("motno", motno);

			if ("quick_search_credit_card".equals(action)){
				req.setAttribute("action", "quick_search_credit_card");
			}else if("quick_search_money_transfer".equals(action)){
				req.setAttribute("action", "quick_search_money_transfer");
			}
			
//			先回首頁
			req.setAttribute("total", total);
			String url = "/frontend/rental_form/quick_search_product_end.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			System.out.println("RentOrdServlet exception=========k========");
			RequestDispatcher failureView = req
					.getRequestDispatcher(exceptionURL);
			failureView.forward(req, res);
		}
	}	
	

	
	
	
	
//quick_search_product_3	
	if ("quick_search_product_3".equals(action)){ 
		
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		System.out.println("quick_search_product_3 in");


		try {
			/***************************1.接收請求參數 **********************/
			String motno = req.getParameter("motno");
			String startday = req.getParameter("startday");
			String endday = req.getParameter("endday");
			String slocno = req.getParameter("slocno");
			String rlocno = req.getParameter("rlocno");	
			
			Integer totalday = Integer.parseInt(req.getParameter("totalday"));
			Integer ecno1 = Integer.parseInt(req.getParameter("ecno1"));
			Integer ecno2 = Integer.parseInt(req.getParameter("ecno2"));
			Integer ecno3 = Integer.parseInt(req.getParameter("ecno3"));
			Integer ecno4 = Integer.parseInt(req.getParameter("ecno4"));

			
			
			//防js沒檔住:
			if(slocno.isEmpty())
					slocno = "TPE01";
			
			if(rlocno.isEmpty())
					rlocno = "KHH01";			
		
			/***************************2.開始查詢資料*****************************************/
			//要處理的有:
			//1.用選定的motno取得motorVO
			//2.依裝備實際需求數量從DB取出leasable的裝備編號.
			//3.total
			
			int total =0;

			//1
			MotorForRentOrdService motorSvc = new MotorForRentOrdService();
			MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(motno);
			
			MotorModelService mmSvc = new MotorModelService();
			MotorModelVO mmVO = mmSvc.findByPK(motorQueryVO.getModtype());			
			total += mmVO.getRenprice() * totalday;
			
			//2
			EquipmentForRentOrdService efro = new EquipmentForRentOrdService();
			List<EquipmentForRentOrdVO> templist = new ArrayList<EquipmentForRentOrdVO>();

			
			List<EquipmentForRentOrdVO> eVOList = new ArrayList<EquipmentForRentOrdVO>();
			
			List<EquipmentForRentOrdVO> ecno1_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno2_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno3_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno4_List = new ArrayList<EquipmentForRentOrdVO>();
			
			String emtno_list_str = "";
			StringBuilder str= new StringBuilder();
			
			RentOrdService roSvc = new RentOrdService();

			
			
			
			
			//下面處理的日期是要轉成timestamp後給sql指令用的.
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy H:mm");
			java.util.Date date;
			long longTime;

			// 處理日期 start_time
			java.sql.Timestamp start_time2 = null;
			try {
				date = (java.util.Date) sdf2.parse(startday);// String to Date
																					
				longTime = date.getTime(); // 取long
				start_time2 = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("start_time (SQL) : "+ start_time2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			

			// 處理日期 end_time
			java.sql.Timestamp end_time2 = null;
			try {
				date = (java.util.Date) sdf2.parse(endday);// String to Date
																					
				longTime = date.getTime(); // 取long
				end_time2 = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("end_time (SQL) : "+ end_time2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			Set<EquipmentForRentOrdVO> offdutyEquipmentVOSet = new LinkedHashSet<EquipmentForRentOrdVO>();
			List<String> rentnolist = roSvc.getRentnoByRentalPeriod(start_time2, end_time2);
			Set<EquipmentForRentOrdVO> tempSet = new LinkedHashSet<EquipmentForRentOrdVO>();
			
			//找出所有時段內不可再租借的裝備   offdutyEquipmentVOSet
			for(String rentno : rentnolist){
				tempSet = roSvc.getEquipmentVOsByRentno(rentno);
				for(EquipmentForRentOrdVO tempVO : tempSet){
					int count =0;
					for(EquipmentForRentOrdVO offeVO:offdutyEquipmentVOSet){
						if(offeVO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						//System.out.println("count ==0 in, emtno = "+tempVO.getEmtno());
						offdutyEquipmentVOSet.add(tempVO);
					}
				}
			}
			System.out.println("offdutySet.size = "+offdutyEquipmentVOSet.size());
			
		
			templist = efro.getEquipsByEcno("EC01");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno1_List.size()<ecno1){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno1_List.add(tempVO);
						eVOList.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = efro.getEquipsByEcno("EC02");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno2_List.size()<ecno2){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno2_List.add(tempVO);
						eVOList.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = efro.getEquipsByEcno("EC03");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno3_List.size()<ecno3){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno3_List.add(tempVO);
						eVOList.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = efro.getEquipsByEcno("EC04");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno4_List.size()<ecno4){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno4_List.add(tempVO);
						eVOList.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			EmtCateService ecSvc = new EmtCateService();		
			for(EquipmentForRentOrdVO eVO : eVOList){
				total += ecSvc.getOneEmtCate(eVO.getEcno()).getPrice() * totalday;
			}
			
			emtno_list_str = str.toString().trim();
			
			System.out.println("emtno_list_str: "+emtno_list_str);

			/***************************3.查詢完成,準備轉交(Send the Success view)*************/

			req.setAttribute("motorQueryVO", motorQueryVO);
			req.setAttribute("startday", startday);
			req.setAttribute("endday", endday);
			req.setAttribute("totalday", totalday);
			req.setAttribute("slocno", slocno);
			req.setAttribute("rlocno", rlocno);
			req.setAttribute("motno", motno);
			req.setAttribute("ecno1_List", ecno1_List);
			req.setAttribute("ecno2_List", ecno2_List);
			req.setAttribute("ecno3_List", ecno3_List);
			req.setAttribute("ecno4_List", ecno4_List);
			req.setAttribute("ecno1_List_size", ecno1_List.size());
			req.setAttribute("ecno2_List_size", ecno2_List.size());
			req.setAttribute("ecno3_List_size", ecno3_List.size());
			req.setAttribute("ecno4_List_size", ecno4_List.size());
			req.setAttribute("emtno_list_str", emtno_list_str);
			
			req.setAttribute("eVOList", eVOList);			
		
			req.setAttribute("total", total);
			String url = "/frontend/rental_form/quick_search_product3.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			System.out.println("RentOrdServlet exception=========m========");
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(exceptionURL);
			failureView.forward(req, res);
		}
	}
//end quick_search_credit_card	
	
	
//quick_search_product_2	
	if ("quick_search_product_2".equals(action)){ 
		
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		System.out.println("quick_search_product_2 in");
		HttpSession session = req.getSession();
		String motno="";
		String dayrange="";

		try {
			/***************************1.接收請求參數 **********************/
			if(session.getAttribute("motno")!=null&&req.getParameter("motno")==null){
				motno = (String)session.getAttribute("motno");
				dayrange = (String)session.getAttribute("dayrange");
				System.out.println("in session range motno="+motno+", dayrange = "+dayrange);
			}else{
				motno = req.getParameter("motno");
				dayrange = req.getParameter("confirmed_rentday");
				System.out.println("in else range motno="+motno+", dayrange = "+dayrange);
			}
			

			
			/***************************2.開始查詢資料*****************************************/
			//要處理的有:
			//1.用選定的motno取得motorVO
			//2.把dayrange處理成startday / endday 兩個字串，順便算總天數totalday
			//3.在DB裝備庫存內，從每種裝備調出兩筆可租用(leasable)裝備 ==>改成用租單來篩選可用裝備
			//	目前裝備DB有四種裝備，先設四個list來裝，不夠的就顯示數量在下個頁面上。
			

			//1
			MotorForRentOrdService motorSvc = new MotorForRentOrdService();
			MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(motno);
			
			//2
			System.out.println("confirmed_rentday"+ dayrange);
			String tokens[] = dayrange.split(" - ");
			String startday = tokens[0];
			String endday  = tokens[1];
			
			System.out.println("startday : "+startday +", endday: "+ endday);
						
//抓string date來換成Date	再來計算總天數		
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

			// 處理日期, 用來計算總天數用。
			java.util.Date start_time = (java.util.Date) sdf.parse(startday);
			java.util.Date end_time = (java.util.Date) sdf.parse(endday);
			
			long diff = end_time.getTime() - start_time.getTime();
			int totalday = (int) (diff / (1000*60*60*24)) +1;
			System.out.println("totalday :" +totalday+"days=================");
			
			
			
			//3. 下面處理的日期是要轉成timestamp後給sql指令用的.
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy H:mm");
			java.util.Date date;
			long longTime;

			// 處理日期 start_time
			java.sql.Timestamp start_time2 = null;
			try {
				date = (java.util.Date) sdf2.parse(startday);// String to Date
																					
				longTime = date.getTime(); // 取long
				start_time2 = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("start_time (SQL) : "+ start_time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			

			// 處理日期 end_time
			java.sql.Timestamp end_time2 = null;
			try {
				date = (java.util.Date) sdf2.parse(endday);// String to Date
																					
				longTime = date.getTime(); // 取long
				end_time2 = new java.sql.Timestamp(longTime); // 切為SQL專用格式
				System.out.println("end_time (SQL) : "+ end_time);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			
			RentOrdService roSvc = new RentOrdService();
			Set<EquipmentForRentOrdVO> tempSet = new LinkedHashSet<EquipmentForRentOrdVO>();
			
			
			Set<EquipmentForRentOrdVO> offdutyEquipmentVOSet = new LinkedHashSet<EquipmentForRentOrdVO>();
			List<String> rentnolist = roSvc.getRentnoByRentalPeriod(start_time2, end_time2);
			
			//找出所有時段內不可再租借的裝備   offdutyEquipmentVOSet
			for(String rentno : rentnolist){
				tempSet = roSvc.getEquipmentVOsByRentno(rentno);
				for(EquipmentForRentOrdVO tempVO : tempSet){
					int count =0;
					for(EquipmentForRentOrdVO offeVO:offdutyEquipmentVOSet){
						if(offeVO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						//System.out.println("count ==0 in, emtno = "+tempVO.getEmtno());
						offdutyEquipmentVOSet.add(tempVO);
					}
				}
			}
			System.out.println("offdutySet.size = "+offdutyEquipmentVOSet.size());

			EquipmentForRentOrdService eSvc = new EquipmentForRentOrdService();
			List<EquipmentForRentOrdVO> templist = new ArrayList<EquipmentForRentOrdVO>();
			
			List<EquipmentForRentOrdVO> ecno1_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno2_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno3_List = new ArrayList<EquipmentForRentOrdVO>();
			List<EquipmentForRentOrdVO> ecno4_List = new ArrayList<EquipmentForRentOrdVO>();
			StringBuilder str = new StringBuilder();
			String availableEmtnoList = "";
			
		
			templist = eSvc.getEquipsByEcno("EC01");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno1_List.size()<2){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno1_List.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = eSvc.getEquipsByEcno("EC02");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno2_List.size()<2){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno2_List.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = eSvc.getEquipsByEcno("EC03");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno3_List.size()<2){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno3_List.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			templist = eSvc.getEquipsByEcno("EC04");			
			for(EquipmentForRentOrdVO tempVO: templist){
				if(ecno4_List.size()<2){
					int count=0;
					for(EquipmentForRentOrdVO t2VO : offdutyEquipmentVOSet){
						if(t2VO.getEmtno().equals(tempVO.getEmtno())){
							count++;
							break;
						}
					}
					if(count==0){
						ecno4_List.add(tempVO);
						str.append(tempVO.getEmtno()+" ");
					}
				}
			}
			
			availableEmtnoList = str.toString().trim();
			
			System.out.println("availableEmtnoList"+availableEmtnoList);


			/***************************3.查詢完成,準備轉交(Send the Success view)*************/

			req.setAttribute("motorQueryVO", motorQueryVO);
			req.setAttribute("startday", startday);
			req.setAttribute("endday", endday);
			req.setAttribute("totalday", totalday);
			req.setAttribute("ecno1_List_size", ecno1_List.size());
			req.setAttribute("ecno2_List_size", ecno2_List.size());
			req.setAttribute("ecno3_List_size", ecno3_List.size());
			req.setAttribute("ecno4_List_size", ecno4_List.size());
			req.setAttribute("availableEmtnoList", availableEmtnoList);
		
			String url = "/frontend/rental_form/quick_search_product2.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			System.out.println("RentOrdServlet exception=========p========");
			RequestDispatcher failureView = req
					.getRequestDispatcher(exceptionURL);
			failureView.forward(req, res);
		}
	}
	
	



//quick_search_product
	if ("quick_search_product".equals(action)) { 
		System.out.println("quick_search_product in");
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);

		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			String motno = req.getParameter("motno");
			String dayrange = req.getParameter("dayrange");
			String memno = req.getParameter("memno");

			System.out.println("memno"+ memno);
			System.out.println("dayrange:" + dayrange);
			
			/***************************2.開始查詢資料*****************************************/
			MotorForRentOrdService motorSvc = new MotorForRentOrdService();
			RentOrdService roSvc = new RentOrdService();
			
			MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(motno);
			Set<RentOrdVO> set = roSvc.getBymotno(motno);
			
			
			Timestamp startday;
			Timestamp endday;
			String dayPicker = "";
			List<String> dayPickerList = new ArrayList<String>();
 			for(RentOrdVO roVO: set){


// 用 Calindar幫忙處理timestamp格式，加完時間後再丟回timestamp.
				endday = roVO.getEnddate();
				//endday = plusOneDayMethod(endday);
				
				startday = roVO.getStartdate();
				dayPicker = startday.toString().substring(0, 10);
				
				dayPickerMethod(dayPicker, dayPickerList);
				
				while(startday.before(endday)){
					
					startday = plusOneDayMethod(startday);
					dayPicker = startday.toString().substring(0, 10);
					
					dayPickerMethod(dayPicker, dayPickerList);
				}
				//System.out.println("dayPicker :"+dayPicker);
			}
 			
 			
 			StringBuilder str= new StringBuilder();
 			for(String temp: dayPickerList){
 				//System.out.println("dayPickerlist:" + temp);
 				str.append(temp+" ");
 			}
 			dayPicker = str.toString().trim();

			/***************************3.查詢完成,準備轉交(Send the Success view)*************/

			req.setAttribute("motorQueryVO", motorQueryVO);
			System.out.println("motnoVO.motorQueryVO:"+motorQueryVO.getMotno());
			
			req.setAttribute("dayPicker", dayPicker);
			
			
//			HttpSession session = req.getSession();
//			Enumeration<String> enumber = session.getAttributeNames();
//			while(enumber.hasMoreElements()){
//				String name = (String) enumber.nextElement();
//				System.out.println("quick_search_product session name: "+name+", value:"+session.getAttribute(name));
//			}
			
			

			
			String url = "/frontend/rental_form/quick_search_product.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 Emp.jsp
			successView.forward(req, res);

			/***************************其他可能的錯誤處理*************************************/
		} catch (Exception e) {
			System.out.println("RentOrdServlet exception=========r========");
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(exceptionURL);
			failureView.forward(req, res);
		}
	} 
	// quick_search_product end			
		
		
		
		
//quick_search
		if ("quick_search".equals(action)) {
			//取得日期區間內還可以租用的車輛VO

			System.out.println("ro quick_search in");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 **********************/
				
				String dayrange = req.getParameter("dayrange");
				
				System.out.println("dayrange" + dayrange);
				
				String tokens[] = dayrange.split(" - ");
				String start_time_str = tokens[0];
				String end_time_str  = tokens[1];
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy H:mm");
				java.util.Date date;
				long longTime;

				// 處理日期 start_time
				java.sql.Timestamp start_time = null;
				try {
					date = (java.util.Date) sdf.parse(start_time_str);// String to Date
																						
					longTime = date.getTime(); // 取long
					start_time = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println("start_time (SQL) : "+ start_time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				

				// 處理日期 end_time
				java.sql.Timestamp end_time = null;
				try {
					date = (java.util.Date) sdf.parse(end_time_str);// String to Date
																						
					longTime = date.getTime(); // 取long
					end_time = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println("end_time (SQL) : "+ end_time);
				} catch (ParseException e) {
					e.printStackTrace();
				}				
				
		
				/*************************** 2.開始處理 *****************************************/
				System.out.println("ro quick_search start");
				
				RentOrdService roSvc = new RentOrdService();
				MotorForRentOrdService mfroSvc = new MotorForRentOrdService();
				Set<MotorForRentOrdVO> availableMotorVO= new LinkedHashSet<MotorForRentOrdVO>();
				
				List<MotorForRentOrdVO> allMotorList = mfroSvc.getMotorsByRentalSide();
				System.out.println("allMotorList.size()"+allMotorList.size());
				List<MotorForRentOrdVO> notAllowMotorList = roSvc.getMotnoInRentOrdByRentalPeriod(start_time, end_time);
				
				for(MotorForRentOrdVO mVO : allMotorList){
					int count = 0;
					//System.out.println("allMotorVO motno: " + mVO.getMotno());
					for(MotorForRentOrdVO mVO2 : notAllowMotorList){
						//System.out.println("ngMotor: " + ngMotor);
						
						if(mVO2.getMotno().equals(mVO.getMotno())){
							count ++;
							//System.out.println("ngMotor caught! :" + ngMotor);
							//System.out.println("==========================count :"+ count);
							break;
						}
					}
					
					//當mVO不在NG清單時進入if
					if(count==0){
						//System.out.println("mVO.getMotno() IN COUNT AREA :"+ mVO.getMotno());
						int count2 = 0;
						for(MotorForRentOrdVO mVO2 : availableMotorVO){
							
							if(mVO.getModtype().equals(mVO2.getModtype())){
								count2++;
							}
						}//"leasable".equals(statusTemp)
						String statusTemp = mVO.getStatus();//1.同型的車只取1輛 , (新增條件)2.而且那一輛在當下是租車方狀態的車.(unleaseable除外)
						if(count2==0&&!"unleasable".equals(statusTemp)){
							//System.out.println("quick_search add room: mVO.status(): " +mVO.getStatus());
							availableMotorVO.add(mVO);
							//System.out.println("mVO.getByMotno:"+mVO.getMotno());
							//System.out.println("availableMotorVO.add(mVO); mVO.type = "+mVO.getModtype());
						}
					}
				}				
			
				System.out.println("rent_ord count(s) during period(出勤車次):" + notAllowMotorList.size());
				System.out.println("allMotorList count:" + allMotorList.size());
				System.out.println("availableMotorVO count:" + availableMotorVO.size());

				/***************************
				 * 3.處理完成,準備轉交(Send the Success view)
				 *************/ // 資料庫取出的VO物件,存入req
				
				req.setAttribute("quick_search", availableMotorVO);
				req.setAttribute("start_time", start_time_str);
				req.setAttribute("end_time", end_time_str);
				
				
//				HttpSession session = req.getSession();
//				Enumeration<String> enumber = session.getAttributeNames();
//				while(enumber.hasMoreElements()){
//					String name = (String) enumber.nextElement();
//					System.out.println("quick_search session name: "+name+", value:"+session.getAttribute(name));
//				}
				

				RequestDispatcher successView = req.getRequestDispatcher("/frontend/rental_form/quick_search.jsp"); // 成功轉交

				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("RentOrdServlet exception======aaa===========");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				failureView.forward(req, res);
			}
		} 
		// quick_search end	
		
 
		
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

				Set<EquipmentForRentOrdVO> set = roSvc.getEquipmentVOsByRentno(rentno);
				
				String differDate = roSvc.differDateCalculator(rentno);
				
				MotorForRentOrdService motorSvc = new MotorForRentOrdService();
				MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(roQueryVO.getMotorVO().getMotno());

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
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
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

						Set<EquipmentForRentOrdVO> set = roSvc.getEquipmentVOsByRentno(rentno);

						MotorForRentOrdService motorSvc = new MotorForRentOrdService();
						MotorForRentOrdVO motorQueryVO = motorSvc.findByPK(roQueryVO.getMotorVO().getMotno());

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
						RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
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
						String rlocno = req.getParameter("rlocno");
						
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
							RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
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
						
						roSvc.updateMotorAfterReturn(motno, milend, rlocno, action);
						

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
											
											roSvc.updateEmtsAfterReturn(values[i], rlocno, action);
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
						System.out.println("RentOrdServlet exception======b===========");
						errorMsgs.add("無法取得資料:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
						failureView.forward(req, res);
					}
				} // after_noreturn_form end				
				
				
		
		
		
//after_nowshow_form
		if ("after_nowshow_form".equals(action)||
			"after_available_form".equals(action)) {
			//1. 處理租賃單狀態、2. 車輛狀態、3. 裝備狀態

			System.out.println("ro after_nowshow/available_form in");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數
				 **********************/
				
				String rentno = req.getParameter("rentno");
				String motno = req.getParameter("motno");
				String note = req.getParameter("note");
				
				System.out.println("rentno:"+rentno+"motno:" + motno + "NOTE = "+ note);
				
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
				System.out.println("RentOrdServlet exception=========c========");
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
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
//				System.out.println("RentOrdServlet exception=========d========");
//				errorMsgs.add("無法取得資料:" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
//				failureView.forward(req, res);
//			}
//		} // after_available_form end
		
		
		// query 
		if ("query".equals(action) || "lease_ord_form".equals(action)
				||"query_for_update".equals(action)||"query_for_member".equals(action)
				||"query_for_member_frontend".equals(action)||"member_cancel_rentord".equals(action)) {

			System.out.println("ro query in");
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				System.out.println("query in");
				String rentno = req.getParameter("rentno");
				System.out.println("rentno:" + rentno);

				if (rentno == null || (rentno.trim()).length() == 0) {
					errorMsgs.add("請輸入編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				System.out.println("query-started");
				RentOrdService roSvc = new RentOrdService();
				RentOrdVO roQueryVO = roSvc.findByPK(rentno);
				Set<EquipmentForRentOrdVO> set = roSvc.getEquipmentVOsByRentno(rentno);
				
				if (roQueryVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp").forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				System.out.println("query-finished");
				req.setAttribute("roQueryVO", roQueryVO);
				req.setAttribute("get_equipmentVOs_by_rentno", set);
				
				System.out.println("roQueryVO.getRentno:" + roQueryVO.getRentno());
				System.out.println("action=" + action);
				
				if ("query".equals(action)) {
					req.getRequestDispatcher("/backend/rent_ord/get_rent_ord_by_pk.jsp").forward(req, res);
				} else if ("lease_ord_form".equals(action)) {
					req.getRequestDispatcher("/backend/rent_ord/lease_ord_form.jsp").forward(req, res);
				}else if ("query_for_update".equals(action)){
					req.getRequestDispatcher("/backend/rent_ord/rent_ord_update_form.jsp").forward(req, res);
				}else if ("query_for_member".equals(action)){
					req.getRequestDispatcher("/backend/member/rent_ord_query_form.jsp").forward(req, res);
				}else if ("query_for_member_frontend".equals(action)){
					req.getRequestDispatcher("/frontend/member/rent_ord_query_form.jsp").forward(req, res);
				}else if ("member_cancel_rentord".equals(action)){
					req.getRequestDispatcher("/frontend/member/rent_ord_cancel_form.jsp").forward(req, res);
				}
				

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				System.out.println("RentOrdServlet exception=========e========");
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
//				String note = req.getParameter("note").trim();

				String totalStr = req.getParameter("total").trim();
				
				String status = req.getParameter("status");
				if(req.getParameter("status")==null){
					status = "unpaid";
				}

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
						
				

				// 處理integer total
				Integer total = null;
				try {
					total = new Integer(totalStr);
				} catch (NumberFormatException e) {
					errorMsgs.add("total請寫數字");
				}

				RentOrdVO roVO = new RentOrdVO();
				roVO.setMemno(memno);
				
				//roVO.setMotno(motno);
				MotorForRentOrdService mSvc = new MotorForRentOrdService();
				roVO.setMotorVO(mSvc.findByPK(motno));
				
				roVO.setSlocno(slocno);
				roVO.setRlocno(rlocno);
				roVO.setTotal(total);
				roVO.setStartdate(startdate);
				roVO.setEnddate(enddate);
				roVO.setStatus(status);
				//roVO.setFilldate(filldate);
				//讓oracle自動填預設日期
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roVO", roVO); // 含有輸入格式錯誤的VO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/index.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				RentOrdService roSvc = new RentOrdService();
				roVO = roSvc.addRentOrd(memno, motno, slocno, rlocno, startdate, enddate, total, status);

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
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				System.out.println("RentOrdServlet exception=========f========");
				failureView.forward(req, res);
			}
		} // insert 'if' end

		// update
		if ("update".equals(action) ||"update_and_close".equals(action)) {
			System.out.println("RentOrdServlet in update-action");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				
				//(7)String: motno, slocno, rlocno, rank, status, note, rentno 
				//(3)Timestamp: startdate, enddate, returndate
				//(4)Integer: total, fine, milstart, milend
				
				
				//String
				String rentno = "";
				if(!req.getParameter("rentno").trim().isEmpty())
					rentno = req.getParameter("rentno").trim();
				
				String motno = "";
				if(!req.getParameter("motno").trim().isEmpty())
					motno = req.getParameter("motno").trim();
					
				String slocno = "";
				if(!req.getParameter("slocno").trim().isEmpty())	
					slocno = req.getParameter("slocno").trim();
				
				String rlocno = "";
				if(!req.getParameter("rlocno").trim().isEmpty())
					rlocno = req.getParameter("rlocno").trim();
				
				String rank = "";
				if(!req.getParameter("rank").trim().isEmpty())
					rank = req.getParameter("rank").trim();
				
				String status = "";
				if(!req.getParameter("status").trim().isEmpty())
					status = req.getParameter("status").trim();
				
				String note = "";
				if(!req.getParameter("note").trim().isEmpty())
					note = req.getParameter("note").trim();

				// Date
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date;
				long longTime;
				
				RentOrdVO roBackup = new RentOrdService().findByPK(rentno);

				// 處理日期 startdate
				java.sql.Timestamp startdate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("startdate"));
					longTime = date.getTime(); // 取long
					startdate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println("start: "+longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("startdate catch error");
					startdate = roBackup.getStartdate();
				}

				// 處理日期 enddate
				java.sql.Timestamp enddate = null;
				try {
					date = (java.util.Date) sdf.parse(req.getParameter("enddate"));
					longTime = date.getTime(); // 取long
					enddate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
					System.out.println("end: "+longTime);
				} catch (ParseException e) {
					e.printStackTrace();
					System.out.println("enddate catch error");
					enddate = roBackup.getEnddate();
				}

				java.sql.Timestamp returndate = null;
				
				// 處理日期 logic: returndate日期可以暫時空白不填，但是要填的話就要填正確
				String returndateStr = req.getParameter("returndate").trim();
				if (returndateStr != null&& !returndateStr.isEmpty()) {
					System.out.println("in returndateStr if");
					try {
						date = (java.util.Date) sdf.parse(returndateStr);
						longTime = date.getTime(); // 取long
						returndate = new java.sql.Timestamp(longTime); // 切為SQL專用格式
						System.out.println("return: "+longTime);
					} catch (ParseException e) {
						e.printStackTrace();
						System.out.println("returndate catch error");
						returndate = roBackup.getReturndate();
					}
				}
				System.out.println("returndate:"+ returndate);

				//Integer
				String milstartStr = req.getParameter("milstart").trim();
				String milendStr = req.getParameter("milend").trim();
				String fineStr = req.getParameter("fine").trim();
				String totalStr = req.getParameter("total").trim();

				Integer milstart = 0;
				if(!milstartStr.isEmpty()){
					try {
						milstart = new Integer(milstartStr);
					} catch (Exception e) {
						System.out.println("milstart catch error");
						milstart = -1;
					}
				}

				Integer milend = 0;
				if(!milendStr.isEmpty()){
					try {
						milend = new Integer(milendStr);
					} catch (Exception e) {
						System.out.println("milend catch error");
						milend = -1;
					}
				}

				Integer fine = 0;
				if(!fineStr.isEmpty()){
					try {
						fine = new Integer(fineStr);
						System.out.println("fine :" +fine);
						if(fine<0)
							fine = 0;
					} catch (Exception e) {
						System.out.println("fine catch error");
						fine = -1;
					}
				}

				Integer total = 0;
				if(!totalStr.isEmpty()){
					try {
							total = new Integer(totalStr);
							if(total < 0)
								total = 0;
						} catch (Exception e) {
							System.out.println("total catch error");
							total = -1;
						}
				}

				// 合理版本: 去掉 filldate, memno
				// private static final String UPDATE = "UPDATE RENT_ORD set
				// motno=?,"
				// + " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?,
				// enddate=?,"
				// + "returndate=?, fine=?, total=?, rank=?, status=?, note=?
				// where rentno = ?";
				
				//(7)String: motno, slocno, rlocno, rank, status, note, rentno 
				//(3)Timestamp: startdate, enddate, returndate
				//(4)Integer: total, fine, milstart, milend
				
				
				

				RentOrdVO roVO = new RentOrdVO();
					roVO.setRentno(rentno);
					
					roVO.setSlocno(slocno);
					roVO.setRlocno(rlocno);
					roVO.setRank(rank);
					roVO.setStatus(status);
					roVO.setNote(note);
					//roVO.setMotno(motno);
					MotorForRentOrdService mSvc = new MotorForRentOrdService();
					roVO.setMotorVO(mSvc.findByPK(motno));
					
					roVO.setStartdate(startdate);
					roVO.setEnddate(enddate);
					roVO.setReturndate(returndate);
					
					roVO.setMilstart(milstart);
					roVO.setMilend(milend);
					roVO.setFine(fine);
					roVO.setTotal(total);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("roVO", roVO); // 含有輸入格式錯誤的VO物件,也存入req
					System.out.println("RentOrdServlet !errorMsgs.isEmpty() in");
					RequestDispatcher failureView = req.getRequestDispatcher("/backend/index.jsp");
					failureView.forward(req, res);
					return;
				}
				System.out.println("update start");
				/*************************** 2.開始新增資料 ***************************************/
				RentOrdService roSvc = new RentOrdService();
				
				roVO = roSvc.updateRentOrd(rentno, motno, slocno, rlocno, rank, status, note ,
						 startdate, enddate,returndate, milstart, milend, fine, total);	

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url="";
				if("update".equals(action))
					url = "/backend/rent_ord/backendRentOrd.jsp";
				if("update_and_close".equals(action))
					url = "/backend/rent_ord/close.jsp";
				
				req.getRequestDispatcher(url).forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("err main update Exception in");
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				System.out.println("RentOrdServlet exception=========g========");
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
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				System.out.println("RentOrdServlet exception=========h========");
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
				System.out.println("RentOrdServlet exception=========k========");
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
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
				RequestDispatcher failureView = req.getRequestDispatcher(exceptionURL);
				System.out.println("RentOrdServlet exception=========i========");
				failureView.forward(req, res);
			}
		} // get_for_return_view end


	}

	private Timestamp plusOneDayMethod(Timestamp day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);//+1天

        	
		day.setTime(cal.getTime().getTime());
		return day;
		
	}

	private void dayPickerMethod(String dayPicker, List<String> dayPickerList) {

		int count=0;
		
		for(String day: dayPickerList){
			if(day.equals(dayPicker)){
				count++;
			}
		}
		if(count==0){
			dayPickerList.add(dayPicker);
		}
	}
}
