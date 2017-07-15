package com.second.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SaleOnOff extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	   String action = req.getParameter("action");
	   if("listMotorByStatus".equals(action))
	   {
		   String status = req.getParameter("status");
		   req.setAttribute("status", status);
		   RequestDispatcher successView = req.getRequestDispatcher("/backend/second_order/SaleOnOff.jsp");
		   successView.forward(req, res);
	   }
	}

}
