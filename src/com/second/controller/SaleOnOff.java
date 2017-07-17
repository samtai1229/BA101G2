package com.second.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.motor.model.MotorService;
import com.motor_model.model.MotorModelService;
import com.motor_model.model.MotorModelVO;


public class SaleOnOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	   System.out.println("進來了");
	   String action = req.getParameter("action");
	   System.out.println("我要走"+action );
	   String requestURL = req.getParameter("requestURL");
	   System.out.println("我來自"+requestURL );
	   if("listMotorByStatus".equals(action))
	   {
		   String status = req.getParameter("status");
		   req.setAttribute("status", status);
		   RequestDispatcher successView = req.getRequestDispatcher("/backend/second_order/SaleOnOff.jsp");
		   successView.forward(req, res);
	   }
	   
	   if("I_WANT_IT".equals(action))
	   {
		   String memVO = (String) req.getSession().getAttribute("memVO");
//		   if(memVO==null)
//		   {
//			   RequestDispatcher failureView = req.getRequestDispatcher("/Login.jsp");
//			   req.setAttribute("loca", arg1);
//			   failureView.forward(req, res);
//			   return;
//		   }
		   String motno = req.getParameter("motno");
		   MotorService motorSvc = new MotorService();
		   MotorModelService mmSvc = new MotorModelService();
		   MotorModelVO mmVO =  mmSvc.findByPK(motorSvc.findByPK(motno).getModtype());
		   req.setAttribute("mmVO", mmVO);
		   req.setAttribute("motorVO", motorSvc.findByPK(motno));
		   RequestDispatcher successView = req.getRequestDispatcher("/frontend/second_order/listOneSecond.jsp");
		   successView.forward(req, res);
		   return;
	   }
	}

}
