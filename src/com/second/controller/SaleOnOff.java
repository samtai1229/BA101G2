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
	   String who = req.getParameter("who");
	   String who2 = new String(who.getBytes("ISO-8859-1"),"UTF-8");
	   System.out.println(who2);

	   System.out.println("我是~~~~~"+who );
	   String requestURL = req.getParameter("requestURL");
	   System.out.println("我來自"+requestURL );
	   if("listMotorByStatus".equals(action))
	   {
		   String status = req.getParameter("status");
		   System.out.println("狀態為:"+status);
		   req.setAttribute("status", status);
		  // req.setAttribute("who", who);
		    req.getSession().setAttribute("who", who2);
		   RequestDispatcher successView = req.getRequestDispatcher("/backend/second_order/SaleOnOff.jsp");
		   successView.forward(req, res);
	   }
	   
	 
	}

}
