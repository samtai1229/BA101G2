package com.news.controller;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NewsDBGifReader extends HttpServlet{

	Connection con;

	  public void doGet(HttpServletRequest req, HttpServletResponse res)
	                               throws ServletException, IOException {
	     
	    req.setCharacterEncoding("UTF-8");
	    res.setContentType("image/png");
	    ServletOutputStream out = res.getOutputStream();
	    
	    try {
	      
	      Statement stmt = con.createStatement();
	      String newsno  = req.getParameter("newsno");
	      String newsno2 = new String(newsno.getBytes("ISO-8859-1"), "UTF-8");  
	      ResultSet rs = stmt.executeQuery(
	    		  "select pic from news where newsno = '"+newsno2+"'");

	      if (rs.next()) {
	        BufferedInputStream in =
	          new BufferedInputStream(rs.getBinaryStream("pic"));
	        byte[] buf = new byte[4 * 1024];  // 4K buffer
	        int len;
	        while ((len = in.read(buf)) != -1) {
	          out.write(buf, 0, len);
	        }
	        in.close();
	      } else {
	          res.sendError(HttpServletResponse.SC_NOT_FOUND);
	      }
	      rs.close();
	      stmt.close();
	    } catch(Exception e) {
	        System.out.println(e);
	    }
	  }
	  public void init() throws ServletException {
	    try {
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	      con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "ba101g2", "ba101g2");
	    }
	    catch (ClassNotFoundException e) {
	      throw new UnavailableException("Couldn't load JdbcOdbcDriver");
	    }
	    catch (SQLException e) {
	      throw new UnavailableException("Couldn't get db connection");
	    }
	  }
	  
	  public void destroy() {
			try {
				if (con != null) con.close();
			} catch (SQLException e) {
				  System.out.println(e);
			}
		}
		
	}

