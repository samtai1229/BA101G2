package com.rent_ord.model;

import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.util.HibernateUtil;

import java.io.IOException;

public class EmtListForRentOrdDAO implements EmtListForRentOrdDAO_interface {

	@Override
	public void insert(EmtListVO emtListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			session.saveOrUpdate(emtListVO);
			
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
			Query query = session.createQuery("delete EmtListVO where rentno = ? and emtno = ?");
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
	public List<EmtListVO> findByRentno(String rentno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<EmtListVO> eList = null;
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from EmtListVO where rentno = ?");
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
	public List<EmtListVO> findByEmtno(String emtno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<EmtListVO> eList = null;
		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from EmtListVO where emtno = ?");
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
	public List<EmtListVO> getAll() {
		List<EmtListVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery("from EmtListVO");
			list = query.list();
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		EmtListForRentOrdDAO dao = new EmtListForRentOrdDAO();

		// 新增
//		EmtListVO eVO1 = new EmtListVO();
//		eVO1.setEmtVO(new EquipmentForRentOrdService().getOneEquipment("E000050"));
//		eVO1.setRoVO(new RentOrdService().findByPK("R000637"));
//		dao.insert(eVO1);

		// 刪除
		//dao.delete("R000460", "E000001");
		
		// 查詢 by emtno
//		List<EmtListVO> list = dao.findByEmtno("E000010");
//		System.out.println("list.size: "+list.size());
//		for(EmtListVO evo: list){
//			System.out.println("eVO.emtno: "+evo.getRoVO().getRentno());
//		}
		
		// 查詢 by rentno
//		List<EmtListVO> list = dao.findByRentno("R000629");
//		System.out.println("list.size: "+list.size());
//		for(EmtListVO evo: list){
//			System.out.println("Rentno:"+evo.getRoVO().getRentno()+" eVO.emtno: "+evo.getEmtVO().getEmtno());
//		}		

		// 查詢 get all
//		List<EmtListVO> list = dao.getAll();
//		System.out.println("list.size: "+list.size());
//		for(EmtListVO evo: list){
//			System.out.println("Rentno:"+evo.getRoVO().getRentno()+" eVO.emtno: "+evo.getEmtVO().getEmtno());
//		}		

	}
}