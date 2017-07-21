package com.rent_ord.model;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.util.HibernateUtil;

import java.io.IOException;

public class EmtListForRentOrdDAO implements EmtListForRentOrdDAO_interface {

	@Override
	public void insert(EmtListForRentOrdVO EmtListForRentOrdVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			session.saveOrUpdate(EmtListForRentOrdVO);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(String rentno, String emtno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

        //【此時多方(宜)可採用HQL刪除】
			Query query = session.createQuery("delete EmtListForRentOrdVO where rentno = ? and emtno = ?");
			query.setParameter(0, rentno);
			query.setParameter(1, emtno);
			System.out.println("刪除的筆數=" + query.executeUpdate());

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public List<EmtListForRentOrdVO> findByRentno(String rentno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<EmtListForRentOrdVO> eList = null;
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from EmtListForRentOrdVO where rentno = ?");
			query.setParameter(0, rentno);
			eList = query.list();
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return eList;
	}

	@Override
	public List<EmtListForRentOrdVO> findByEmtno(String emtno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<EmtListForRentOrdVO> eList = null;
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from EmtListForRentOrdVO where emtno = ?");
			query.setParameter(0, emtno);
			eList = query.list();
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return eList;
	}	
	


	@Override
	public List<EmtListForRentOrdVO> getAll() {
		List<EmtListForRentOrdVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery("from EmtListForRentOrdVO");
			list = query.list();
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
//		EmtListForRentOrdDAO dao = new EmtListForRentOrdDAO();
		// 新增
//		EmtListForRentOrdVO eVO1 = new EmtListForRentOrdVO();
//		eVO1.setEmtVO(new EquipmentForRentOrdService().getOneEquipment("E000050"));
//		eVO1.setRoVO(new RentOrdService().findByPK("R000676"));
//		dao.insert(eVO1);

		// 刪除
//		dao.delete("R000676", "E000050");
		
		// 查詢 by emtno
//		List<EmtListForRentOrdVO> list = dao.findByEmtno("E000010");
//		System.out.println("list.size: "+list.size());
//		for(EmtListForRentOrdVO evo: list){
//			System.out.println("eVO.emtno: "+evo.getRoVO().getRentno());
//		}
		
		// 查詢 by rentno
//		List<EmtListForRentOrdVO> list = dao.findByRentno("R000629");
//		System.out.println("list.size: "+list.size());
//		for(EmtListForRentOrdVO evo: list){
//			System.out.println("Rentno:"+evo.getRoVO().getRentno()+" eVO.emtno: "+evo.getEmtVO().getEmtno());
//		}		

		// 查詢 get all
//		List<EmtListForRentOrdVO> list = dao.getAll();
//		System.out.println("list.size: "+list.size());
//		for(EmtListForRentOrdVO evo: list){
//			System.out.println("Rentno:"+evo.getRoVO().getRentno()+" eVO.emtno: "+evo.getEmtVO().getEmtno());
//		}
		
	}
}