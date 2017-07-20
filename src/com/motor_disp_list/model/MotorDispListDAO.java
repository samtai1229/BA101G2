package com.motor_disp_list.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.motor_dispatch.model.MotorDispatchVO;
import com.motor_model.model.MotorModelVO;

import hibernate.util.HibernateUtil;

public class MotorDispListDAO implements MotorDispListDAO_interface {
	
	private static final String GET_ONE_BY_MODO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where mdno = ?";

	private static final String GET_ONE_BY_MOTNO = "SELECT mdno, motno " 
			+ "  FROM MOTOR_DISP_LIST where motno = ?";
	//以下hiberante
	private static final String DELETE = "delete MotorDispListVO where mdno = ?";
	
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
	
	@Override
	public void deleteByHib(String mdno){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(DELETE);
			query.setParameter(0, mdno);
			query.executeUpdate();
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	
	public static void main(String[] args) {
		
		MotorDispListDAO dao = new MotorDispListDAO();
		MotorDispatchVO mdVO = new MotorDispatchVO(); 
		MotorModelVO mmVO = new MotorModelVO(); 
		MotorDispListService mdlSvc = new MotorDispListService();
		mdlSvc.deleteByHib("MD000023");
		System.out.println("GGG");
		
		
//		Set<MotorDispListVO> set = new HashSet<MotorDispListVO>();
	//	
//		MotorDispListVO mdListVO = new MotorDispListVO();
//		int amount = 2;
//		for(int i = 1; i <= amount; i++){
//			mdVO.setLocno("TPE01");
//			mmVO.setModtype("MM101");
//			mdListVO.setMotno(String.valueOf(i));
//		}
//		mdListVO.setMotorDispatchVO(mdVO);
//		mdListVO.setMotorModelVO(mmVO);
//		set.add(mdListVO);
	//	
//		mdVO.setMotorDispLists(set);
//		dao.insertByHib(mdVO);
	//	
	//	
	//	
//		List<MotorDispatchVO> list2 = dao.getAllByHib();
//		for (MotorDispatchVO aDept : list2) {
//			System.out.print(aDept.getMdno() + ",");
//			System.out.print(aDept.getLocno() + ",");
//			System.out.print(aDept.getFilldate());
//			System.out.print(aDept.getCloseddate());
//			System.out.print(aDept.getProg());
//			System.out.println("\n-----------------");
//			
//		}
	//	
		}

}
