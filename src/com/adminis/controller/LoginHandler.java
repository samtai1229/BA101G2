package com.adminis.controller;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminis.model.AdminisService;
import com.adminis.model.AdminisVO;

import javax.servlet.annotation.WebServlet;

//@WebServlet("/loginhandler")
public class LoginHandler extends HttpServlet {

	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	AdminisVO adminisVO = new AdminisVO();
	protected AdminisVO allowUser(String account, String password) {
		AdminisService as = new AdminisService();
		adminisVO = as.getOneAccount(account);
		
		 if(adminisVO == null){
			 
			 return null;
			 
		 }else {
		 		if(adminisVO.getAcc().equals(account) && adminisVO.getPw().equals(password)){
		 			
		 			return adminisVO;
		 		}else{
		 			return null;
		 		}
		 }
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		   
		    String account = req.getParameter("account");
		    String password = req.getParameter("password");
		    
		    // 【檢查該帳號 , 密碼是否有效】
		    if (allowUser(account, password) == null) {          //【帳號 , 密碼無效時】
		      out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
		      out.println("<BODY>你的帳號 , 密碼無效!<BR>");
		      out.println("請按此重新登入 <A HREF="+req.getContextPath()+"/backend/BackendLogin.jsp>重新登入</A>");
		      out.println("</BODY></HTML>");
		    }else {                        
//【帳號 , 密碼有效時, 才做以下工作】
		      HttpSession session = req.getSession();//*工作1: 才在session內做已經登入過的標識
		      session.setAttribute("adminisVO", adminisVO);
		       try {                                                        
		         String location = (String) session.getAttribute("location");
			    	System.out.println("location"+location);

		         if (location != null) {
		           session.removeAttribute("location");   //*工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
		           res.sendRedirect(req.getContextPath()+"/backend/index.jsp");  
		           return;
		         }
		       }catch (Exception ignored) { }
//		    	RequestDispatcher abc=req.getRequestDispatcher("/backend/index.jsp");
//		    	abc.forward(req, res);
		      res.sendRedirect(req.getContextPath()+"/backend/index.jsp");  //*工作3: (-->如無來源網頁:則重導至login_success.jsp)
		    }
		  

//		if (action1.equals("logout")) {
//	    	System.out.println("5");
//
//			HttpSession session = req.getSession();
//			session.removeAttribute("account");
//			res.sendRedirect("/BA101G2/backend/BackendLogin.jsp");
//
//		}

	}
}
