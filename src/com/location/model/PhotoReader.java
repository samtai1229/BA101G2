package com.location.model;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@WebServlet("/PhotoReader1")
public class PhotoReader extends HttpServlet {

    Connection con;

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        req.setCharacterEncoding("Big5");
        res.setContentType("image/gif");

        ServletOutputStream out = res.getOutputStream();

        try {

            Statement stmt = con.createStatement();
            String locno = req.getParameter("locno");
            String locno2 = new String(locno.getBytes("ISO-8859-1"), "UTF-8");
            ResultSet rs1 = stmt.executeQuery("select pic from Location where locno = '"
                    + locno2 + "'");
           
            
            
            if (rs1.next()) {
                BufferedInputStream in = new BufferedInputStream(rs1.getBinaryStream("pic"));
                
                byte[] buf = new byte[4 * 1024]; // 4K buffer
                int len;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                in.close();

            } else {
                res.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            rs1.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public void init() throws ServletException {
        try {
            Context ctx = new javax.naming.InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
            con = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}