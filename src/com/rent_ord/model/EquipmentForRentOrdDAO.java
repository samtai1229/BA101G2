package com.rent_ord.model;
import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.rent_ord.model.EquipmentForRentOrdVO;

import hibernate.util.HibernateUtil;

public class EquipmentForRentOrdDAO implements EquipmentForRentOrdDAO_interface {

	private static final String GET_ALL_STMT = " from EquipmentForRentOrdVO order by emtno";
	private static final String GET_EQUIPs_BY_ECNO =  "from EquipmentForRentOrdVO where ecno = ? ";

	@Override
	public List<EquipmentForRentOrdVO> getEquipsByEcno(String ecno) {
		List<EquipmentForRentOrdVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			Query query = session.createQuery(GET_EQUIPs_BY_ECNO);
			query.setParameter(0, ecno);

			list = query.list();
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public EquipmentForRentOrdVO findByPrimaryKey(String emtno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		EquipmentForRentOrdVO eVO;
		try {
			session.beginTransaction();
			
			eVO = (EquipmentForRentOrdVO) session.get(EquipmentForRentOrdVO.class, emtno);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return eVO;
	}

	@Override
	public List<EquipmentForRentOrdVO> getAll() {
		List<EquipmentForRentOrdVO> list = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {

			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		EquipmentForRentOrdDAO dao = new EquipmentForRentOrdDAO();

		// 查詢
//		EquipmentForRentOrdVO equipmentVO3 = dao.findByPrimaryKey("E000010");
//		System.out.print(equipmentVO3.getEmtno() + ",");
//		System.out.print(equipmentVO3.getEcno() + ",");
//		System.out.print(equipmentVO3.getLocno() + ",");
//		System.out.print(equipmentVO3.getPurchdate() + ",");
//		System.out.print(equipmentVO3.getStatus() + ",");
//		System.out.print(equipmentVO3.getNote());
//		System.out.println("---------------------");

		// 查詢
//		 List<EquipmentForRentOrdVO> list = dao.getEquipsByEcno("EC02");
//		 for (EquipmentForRentOrdVO aEquipment : list) {
//			 System.out.print(aEquipment.getEmtno() + ",");
//			 System.out.print(aEquipment.getEcno() + ",");
//			 System.out.print(aEquipment.getLocno() + ",");
//			 System.out.print(aEquipment.getPurchdate() + ",");
//			 System.out.print(aEquipment.getStatus() + ",");
//			 System.out.print(aEquipment.getNote());
//			 System.out.println();
//		 }
//		 System.out.println("list.size: " +list.size());		
		 
			// 查全部
//		 List<EquipmentForRentOrdVO> list = dao.getAll();
//		 for (EquipmentForRentOrdVO aEquipment : list) {
//			 System.out.print(aEquipment.getEmtno() + ",");
//			 System.out.print(aEquipment.getEcno() + ",");
//			 System.out.print(aEquipment.getLocno() + ",");
//			 System.out.print(aEquipment.getPurchdate() + ",");
//			 System.out.print(aEquipment.getStatus() + ",");
//			 System.out.print(aEquipment.getNote());
//			 System.out.println();
//		 }	
//		 System.out.println("list.size: " +list.size());


	}	
	
	
	
	
	
	
	
	
	
	
	
	
}
