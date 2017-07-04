package com.rent_ord.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.rent_ord.model.RentOrdService;
import com.rent_ord.model.RentOrdVO;

public class RentOrdScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Timer timer = new Timer();

	public void destroy() {
		super.destroy(); // entirely optional

		System.out.println("destory on=======RentOrd Schedule");
		timer.cancel();

	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain");
		PrintWriter out = res.getWriter();
		out.print("");
		System.out.println("doGet on=======RentOrd Schedule");
	}

	public void init() throws ServletException {

		System.out.println("init on=======RentOrd Schedule");

		TimerTask task = new TimerTask() {
			int count = 0;

			public void run() {
				System.out.println("This is Task" + count);
				System.out.println("工作排定的時間 = " + new Date(scheduledExecutionTime()));
				System.out.println("工作執行的時間 = " + new Date() + "\n");
				count++;
				
				// 處理訂單狀態dd
				RentOrdService roSvc = new RentOrdService();
				Calendar currentResetCal = resetTimeMethod(Calendar.getInstance());//reset current cal
				
				Calendar tempcal =Calendar.getInstance();
				Date tempday  = new java.util.Date();
				Timestamp currenttimestamp = new Timestamp(System.currentTimeMillis());
				
				// 1.所有待繳費租賃單:'nupaid':
				// a.填單日兩天內未匯款的，第三天自動改為取消'canceled'. (匯款部分人工處理?)
				// b.至起始日還未匯款者改為取消canceled
				Set<RentOrdVO> nupaidVOs = roSvc.getByStatus("unpaid");
				System.out.println("nupaidVO.size: "+nupaidVOs.size());
				
				for(RentOrdVO tempVO: nupaidVOs){
					if(((tempday.getTime() - tempVO.getFilldate().getTime())> 2*24*60*60*1000)||
							tempVO.getStartdate().before(currenttimestamp)){
						
						System.out.print("tempVO.rentno "+tempVO.getRentno()+"in nupaidVOs ");
						System.out.println("tempVO.status = "+tempVO.getStatus());
						System.out.print(tempVO.getRentno()+" startday = "+ tempVO.getStartdate());
						System.out.println(tempVO.getRentno()+" filleate = "+ tempVO.getFilldate());
						System.out.println("currenttimestamp: " + currenttimestamp);
						roSvc.updateStatusByRentno("canceled", tempVO.getRentno());
						System.out.println("update!");
						System.out.println("======nupaidVOs======");
					}
				}
				
				// 2.未交車:'unoccupied':
				// a.至起始日時改為'available'
				Set<RentOrdVO> unoccupiedVOs = roSvc.getByStatus("unoccupied");
				System.out.println("unoccupiedVO.size: "+unoccupiedVOs.size());
				
				for(RentOrdVO tempVO: unoccupiedVOs){
					long tempLong = tempVO.getStartdate().getTime();
					tempcal.setTimeInMillis(tempLong);
					tempcal = resetTimeMethod(tempcal);
					
					//System.out.println("unoccupied rentno: "+ tempVO.getRentno() +" tempcal:" +tempcal.getTime());
					if(!currentResetCal.before(tempcal)){
						System.out.println("tempVO.getRentno():" + tempVO.getRentno() +" Startdate "+tempVO.getStartdate());
						roSvc.updateStatusByRentno("available", tempVO.getRentno());
						System.out.println("unoccupied-update");
						
					}
				}				
				
				
				
				// 3.可交車:'available'
				// a.超過起始日改為'noshow'
				Set<RentOrdVO> availableVOs = roSvc.getByStatus("available");
				System.out.println("availableVO.size: "+availableVOs.size());
				
				for(RentOrdVO tempVO: availableVOs){
					long tempLong = tempVO.getStartdate().getTime();
					tempcal.setTimeInMillis(tempLong);
					tempcal = resetTimeMethod(tempcal);
					
					//System.out.println("available rentno: "+ tempVO.getRentno() +" tempcal:" +tempcal.getTime());
					if(currentResetCal.after(tempcal)){
						System.out.println("tempVO.getRentno():" + tempVO.getRentno() +" currentResetCal "+currentResetCal.getTime());
						roSvc.updateStatusByRentno("noshow", tempVO.getRentno());
						System.out.println("noshow-update");
						
					}
				}	
				
				// 4.未還車:'noreturn'
				// a.超過結束日改為'overtime'
				Set<RentOrdVO> noreturnVOs = roSvc.getByStatus("noreturn");
				System.out.println("noreturnVO.size: "+noreturnVOs.size());	
				

				for(RentOrdVO tempVO: noreturnVOs){
					long tempLong = tempVO.getEnddate().getTime();//enddate
					tempcal.setTimeInMillis(tempLong);
					tempcal = resetTimeMethod(tempcal);
					
					//System.out.println("noreturn rentno: "+ tempVO.getRentno() +" tempcal:" +tempcal.getTime());
					if(currentResetCal.after(tempcal)){
						System.out.println("tempVO.getRentno():" + tempVO.getRentno() +" currentResetCal "+currentResetCal.getTime());
						roSvc.updateStatusByRentno("overtime", tempVO.getRentno());
						System.out.println("noreturn-update");
						
					}
				}	
				
				

			}
		};

		Calendar cal = Calendar.getInstance();
		System.out.println("cal2.getTime(): (current)" + cal.getTime());
		cal = resetTimeMethod(cal);

		timer.scheduleAtFixedRate(task, cal.getTime(), 6 * 60 * 60 * 1000);
		System.out.println("=======RentOrd Schedule Project on duty=========");

	}

	private Calendar resetTimeMethod(Calendar calTemp) {
		calTemp.set(Calendar.HOUR_OF_DAY, 0);
		calTemp.set(Calendar.MINUTE, 0);
		calTemp.set(Calendar.SECOND, 0);
		calTemp.set(Calendar.MILLISECOND, 0);
		//System.out.println("calTemp.getTime(): (reset)" + calTemp.getTime());
		return calTemp;
	}
}
