package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;
import com.rent_ord.model.MotorVO;
import hibernate.util.HibernateUtil;

public class MotorForRentOrdDAO implements MotorForRentOrdDAO_interface {

	
	@Override
	public MotorVO findByPrimaryKey(String motno) {
	
		MotorVO motorVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
		try {
			session.beginTransaction();
			motorVO = (MotorVO) session.get(MotorVO.class, motno);
			session.getTransaction().commit();
			System.out.println("in findByPK motor");
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return motorVO;
	}

	@Override
	public List<MotorVO> getAll() {
		List<MotorVO> list = new ArrayList<MotorVO>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
		try {
	
			session.beginTransaction();
			
			Query query = session.createQuery("from MotorVO");
			list = query.list();
			session.getTransaction().commit();
	
	
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<MotorVO> getMotorsByRentalSide() {
		List<MotorVO> list = new ArrayList<MotorVO>();

		System.out.println("-------------------------------getMotorsByRentalSide in");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		System.out.println("getMotorsByRentalSide in 2");
		String str = "from MotorVO where status = 'unleasable' or "
				+ " status='leasable' or status='reserved' or status='occupied' order by motno";
	
		try {
	
			session.beginTransaction();
			Query query = session.createQuery(str);
			list = query.list();
			
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<String> getMotnosByAllRentalStatus() {
		List<String> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
	
			session.beginTransaction();
			
			String str = "select motno from MotorVO where status = 'unleasable' or "
					+ " status='leasable' or status='reserved' or status='occupied' order by motno";
			
			Query query = session.createQuery(str);
	
			list = query.list();
			session.getTransaction().commit();
	
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public Set<MotorVO> getMotorsByModelType(String modtype) {
		Set<MotorVO> set = null;
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	
		try {
			session.beginTransaction();
			
			//FROM motor where modtype = ?
			Query query = session.createQuery("from MotorVO where modtype = ?");
			query.setParameter(0, modtype);
			List list = query.list();
			set = new LinkedHashSet<MotorVO>(list);
			
			session.getTransaction().commit();
	
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
		return set;
	}

	@Override
	public List<String> getMotnosByModelType(String modtype) {
		List<String> list = new ArrayList<String>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		
//		private static final String GET_MOTNO_BY_MOTOR_TYPE = "SELECT motno "
//				+ " FROM motor where modtype = ?";
		

		try {
			session.beginTransaction();
			Query query = session.createQuery("select motno from MotorVO where modtype = ?");
			query.setParameter(0, modtype);
			list = query.list();
			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
	}

	@Override
	public Set<MotorVO> getMotorsByLocNo(String locno) {
		Set<MotorVO> set = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery("from MotorVO where locno = ?");
			query.setParameter(0, locno);
			List list = query.list();
			
			set = new LinkedHashSet<MotorVO>(list);
			session.getTransaction().commit();
			

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		Set<MotorVO> set = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			
			session.beginTransaction();
			
			Query query = session.createQuery(" from MotorVO where manudate between ? and ? order by manudate");
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);
			List list = query.list();
			set = new LinkedHashSet<MotorVO>(list);
			
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}
	
	
	public static void main(String[] args) {

		System.out.println("in MotorForRentOrdDAO main method");
		MotorForRentOrdDAO dao = new MotorForRentOrdDAO();
		
//		 MotorVO motorVO3 = dao.findByPrimaryKey("M000088");
//		 System.out.println(motorVO3.getMotno() +",");
//		 System.out.println(motorVO3.getModtype() +",");
//		 System.out.println(motorVO3.getPlateno() +",");
//		 System.out.println(motorVO3.getEngno() +",");
//		 System.out.println(motorVO3.getManudate() +",");
//		 System.out.println(motorVO3.getMile() +",");
//		 System.out.println(motorVO3.getLocno() +",");
//		 System.out.println(motorVO3.getStatus() +",");
//		 System.out.println(motorVO3.getNote() +",");
//		 System.out.println("query ok");

//		 List<MotorVO> list = dao.getAll();
//		 for(MotorVO aMotor : list){
//			 System.out.println("getMotno:" + aMotor.getMotno());
//			 System.out.println("getManudate:" + aMotor.getManudate());
//			 System.out.println("getModtype:" + aMotor.getModtype());
//		 }
//		 System.out.println("=======================================");
		
//		 List<MotorVO> list = dao.getMotorsByRentalSide();
//		 for(MotorVO aMotor : list){
//			 System.out.println("getMotno:" + aMotor.getMotno());
//			 System.out.println("getManudate:" + aMotor.getManudate());
//			 System.out.println("getModtype:" + aMotor.getModtype());
//			 System.out.println("status:"+ aMotor.getStatus());
//		 }
//		 System.out.println("=======================================");		
		

//		 List<String> list = dao.getMotnosByAllRentalStatus();
//		 for(String motno : list){
//			 System.out.println("motno:" + motno);
//		 }
//		 System.out.println("=======================================");


//		 List<String> list = dao.getMotnosByModelType("MM103");
//		 for(String motno : list){
//			 System.out.println("motno:" + motno);
//		 }
//		 System.out.println("=======================================");
		
		
		
//		 Set<MotorVO> set1 = dao.getMotorsByModelType("MM103");
//		 System.out.println("=======================================");
//		 for(MotorVO aMotor : set1){
//			 System.out.println("getMotno:" + aMotor.getMotno());
//			 System.out.println("getManudate:" + aMotor.getManudate());
//			 System.out.println("getModtype:" + aMotor.getModtype());
//		 }
		
//		 Set<MotorVO> set2 = dao.getMotorsByLocNo("TPE01");
//		 System.out.println("=======================================");
//		 for(MotorVO aMotor : set2){
//			 System.out.println("getMotno:" + aMotor.getMotno());
//			 System.out.println("getManudate:" + aMotor.getManudate());
//			 System.out.println("getModtype:" + aMotor.getModtype());
//		 }
		
//		Set<MotorVO> set3 = dao.getMotorsByManuDate(java.sql.Timestamp.valueOf("2017-01-01 00:00:01"),
//				java.sql.Timestamp.valueOf("2017-09-30 23:59:59"));
//		System.out.println("=======================================");
//		 for(MotorVO aMotor : set3){
//		 System.out.println("getMotno:" + aMotor.getMotno());
//		 System.out.println("getManudate:" + aMotor.getManudate());
//		 System.out.println("getModtype:" + aMotor.getModtype());
//	 	}
	}
}
