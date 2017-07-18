package com.motor_disp_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;

import com.motor_dispatch.model.MotorDispatchVO;
import com.motor_model.model.MotorModelVO;

import hibernate.util.HibernateUtil;

public class MotorDispListDAO implements MotorDispListDAO_interface {
	
	private static final String GET_ONE_BY_MODO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where mdno = ?";

	private static final String GET_ONE_BY_MOTNO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where motno = ?";
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public MotorDispListVO findByPrimaryKeyDispatchNo(String mdno) {

		MotorDispListVO mdlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MODO);

			pstmt.setString(1, mdno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdlVO = new MotorDispListVO();
				setAttirbute(mdlVO, rs); // 拉出來寫成一個方法
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mdlVO;
	}

	@Override
	public MotorDispListVO findByPrimaryKeyMotorNo(String motno) {

		MotorDispListVO mdlVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_MOTNO);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdlVO = new MotorDispListVO();
				setAttirbute(mdlVO, rs); // 拉出來寫成一個方法
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mdlVO;
	}

	private void setAttirbute(MotorDispListVO mdlVO, ResultSet rs) {
		try {
			mdlVO.setMdno(rs.getString("mdno"));
			mdlVO.setMotno(rs.getString("motno"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//以下為Hibernate用
	
	@Override
	public void insertByHib(MotorDispListVO mdListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(mdListVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void updateByHib(MotorDispListVO mdListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(mdListVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

}
