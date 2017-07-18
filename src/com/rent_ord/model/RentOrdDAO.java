package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.rent_ord.model.EquipmentVO;
import com.rent_ord.model.MotorVO;

import hibernate.util.HibernateUtil;

public class RentOrdDAO implements RentOrdDAO_interface {

    final String INSERT_STMT = "INSERT INTO RENT_ORD"
	+ " (rentno, memno, motno, slocno, rlocno, startdate, enddate, total, status "
	+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
	+ "  ?, ?, ?,?)";
	
	private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
			+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
			+ " returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";

	private static final String DELETE = "DELETE FROM RENT_ORD where rentno = ?";

	private static final String selectFactor = "SELECT rentno, memno, motno, slocno, rlocno,"
			+ " milstart, milend,  filldate, startdate, enddate, returndate, fine, total, " 
			+ " rank, status, note ";	
	
	private static final String GET_ALL = " select * FROM RENT_ORD";

	private static final String GET_ONE = selectFactor 
			+ " FROM RENT_ORD where rentno = ?";

	private static final String GET_BY_START_LOC_NO = " from RentOrdVO where slocno = ?  order by rentno desc";

	private static final String GET_BY_RETURN_LOC_NO = "from RentOrdVO where rlocno = ?  order by rentno desc";

	private static final String GET_BY_STATUS = " from RentOrdVO where status = ?";

	private static final String GET_BY_STARTTIME_PEROID =
			 " from RentOrdVO  where startdate  between ? and ? order by startdate";

	private static final String GET_BY_ENDTIME_PEROID = 
			 " from RentOrdVO  where enddate"
			+ " between ? and ? order by enddate";
	
	private static final String GET_BY_MOTNO = " from RentOrdVO where motno = ?";
	
	private static final String GET_FOR_LEASE_VIEW = 
			"from RentOrdVO where slocno=? "
			+" and (status = 'unpaid' or status = 'unoccupied' or"
			+" status = 'noshow' or status = 'available' or status = 'canceled')"
			+" order by DECODE(status,'noshow',1,'available',2), status, startdate";
	
	private static final String GET_FOR_RETURN_VIEW = 
			"from RentOrdVO where rlocno=? "
			+" and (status = 'noreturn' or status = 'overtime')"
			+"order by DECODE(status,'overtime',1,'noreturn',2), enddate";
	
	
	
	private static final String GET_EMTNOs_BY_RENTNO_IN_EMT_LIST= 
			" SELECT emtVO FROM EmtListVO where rentno = ? ";
	
	private static final String GET_EMPVOs_BY_EMTNOs_IN_EQUIPMENT=
			" from EquipmentVO where emtno = ? ";

	//DIFFER_DATE_CALCULATOR
		private static final String DIFFER_DATE_CALCULATOR = 
			"select to_char(SYSDATE - TO_DATE(to_char(ENDDATE, 'yyyy/mm/dd'),'yyyy/mm/dd')) DIFFDAY "
			+" from RENT_ORD where rentno = ? ";
	
	//RENT_ORD from-status-changer: for available	
	private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_OCCUPIED = 
			"UPDATE EQUIPMENT set status='occupied' where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_OCCUPIED = 
			"UPDATE MOTOR set status='occupied' where motno = ? ";
	private static final String UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_NORETURN = 
			"UPDATE RENT_ORD set status='noreturn', note = ? where rentno = ? ";
	
	
//RENT_ORD from-status-changer: for noshow
	private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
			"UPDATE EQUIPMENT set status='unleasable' where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
			"UPDATE MOTOR set status='unleasable' where motno = ? ";
	private static final String UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_ABNORMALCLOSED = 
			"UPDATE RENT_ORD set status='abnormalclosed', note = ? where rentno = ? ";
	

	//RENT_ORD from-status-changer: for noreturn/overtime
	//1. 處理租賃單狀態(milend, returndate, fine, rank, 'status', note)
	//2. 車輛狀態(mile, 'status')
	//3. 裝備狀態('status')
	private static final String UPDATE_RENT_ORD_FROM_NORETURN_TO_CLOSED = 
			"UPDATE RENT_ORD set milend = ?, returndate = ?,"
			+" fine = ?, status='closed', rank = ?, note = ? where rentno = ? ";
	private static final String UPDATE_RENT_ORD_FROM_OVERTIME_TO_ABNORMALCLOSED = 
			"UPDATE RentOrdVO set milend = ?, returndate = ?,"
			+" fine = ?, status='abnormalclosed', rank = ?, note = ? where rentno = ? ";
	private static final String UPDATE_EMT_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
			"UPDATE EQUIPMENT set status='unleasable', locno = ? where emtno = ? ";
	private static final String UPDATE_MOTOR_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
			"UPDATE MOTOR set mile = ?, status='unleasable', locno = ? where motno = ? ";
	
	
	//依使用者輸入的時段來找符合的車輛並回傳車輛編號.
	private static final String  GET_MOTORs_BY_DATE_RANGE = 
			  "SELECT motorVO from RentOrdVO where ( startdate > ? and startdate <? )"
			  +" or  ( enddate > ? and enddate < ? ) order by startdate";
	
	private static final String GET_RENTNO_BY_MEMNO_AND_STARTDATE = 
			"SELECT rentno from RentOrdVO where memno =? and startdate = ?";
	
	private static final String GET_RENTNO_BY_PY_DATE_RANGE = 
			"SELECT rentno FROM RentOrdVO where ( startdate > ? and startdate <? )"
			  +" or  ( enddate > ? and enddate < ? ) order by startdate";
	
	private static final String GET_EMTNO_FROM_EMT_LIST_BY_RENTNO = 
			"SELECT emtVO FROM EmtListVO where rentno = ? ";
	
	private static final String GET_roVOs_BY_STARTDAY_ENDDAY_MONTO=
			"from RentOrdVO where  (( startdate > ? and startdate <? )"
			  +" or  ( enddate > ? and enddate < ? )) and MOTNO = ? order by startdate";
	
	private static final String UPDATE_STATUS_BY_RENTNO = 
			"UPDATE RENT_ORD set status = ? where rentno = ? ";
	

	@Override
	public void updateStatusByRentno(String status, String rentno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			RentOrdVO roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			roVO.setStatus(status);
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public Set<RentOrdVO> getRoVOsByDatePrioidAndMotno(Timestamp start_time, Timestamp end_time, String motno) {
		Set<RentOrdVO> set =null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_roVOs_BY_STARTDAY_ENDDAY_MONTO);
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);
			query.setParameter(2, start_time);
			query.setParameter(3, end_time);
			query.setParameter(4, motno);
			
			List list = query.list();
			
			set = new LinkedHashSet<RentOrdVO>(list);
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public List<EquipmentVO> getEmtnoByRentno(String rentno) {
		List<EquipmentVO> list = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			
			Query query = session.createQuery(GET_EMTNO_FROM_EMT_LIST_BY_RENTNO);
			query.setParameter(0, rentno);
			list = query.list();
			
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<String> getRentnoByRentalPeriod(Timestamp start_time, Timestamp end_time) {
		List<String> list = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		System.out.println("start_time in RentOrdDAO : " + start_time);
		System.out.println("end_time in RentOrdDAO : " + end_time);
		
		resetDayMethod(start_time, false); //時分歸零
		resetDayMethod(end_time, true);
		
		System.out.println("start_time reset : " + start_time);
		System.out.println("end_time reset : " + end_time);

		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_RENTNO_BY_PY_DATE_RANGE);
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);			
			query.setParameter(2, start_time);			
			query.setParameter(3, end_time);			
			list = query.list();

			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}


	@Override
	public String getRentnoByMemnoAndStartdate(String memno, Timestamp start_time) {
		String rentno="";

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_RENTNO_BY_MEMNO_AND_STARTDATE);
			
			query.setParameter(0, memno);
			query.setParameter(1, start_time);
			
			if(query.list().size()!=0){
				rentno = (String) query.list().get(0);
			}else{
				rentno = "nodata";
			}

			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return rentno;
	}


	@Override
	public Set<RentOrdVO> getRentalOrdersBymotno(String motno) {
		Set<RentOrdVO> set = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_MOTNO);
			query.setParameter(0, motno);
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}


	@Override
	public List<MotorVO> getMotnoInRentOrdByRentalPeriod(Timestamp start_time, Timestamp end_time) {

		List<MotorVO> list = null;
		
		System.out.println("start_time in RentOrdDAO : " + start_time);
		System.out.println("end_time in RentOrdDAO : " + end_time);
		
		resetDayMethod(start_time, false); //時分歸零
		resetDayMethod(end_time, true);
		
		System.out.println("start_time reset : " + start_time);
		System.out.println("end_time reset : " + end_time);


		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_MOTORs_BY_DATE_RANGE);
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);
			query.setParameter(2, start_time);
			query.setParameter(3, end_time);

			list = query.list();
			System.out.println("after query.list");

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}


	private Timestamp resetDayMethod(Timestamp day, boolean isEndDay) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		if(isEndDay){
			cal.add(Calendar.DATE, 2);//+2天
		}else{
			cal.add(Calendar.DAY_OF_MONTH, -2);//-2天
		}

		day.setTime(cal.getTime().getTime());
		return day;
	}


	@Override
	public void updateRentOrdAfterOvertime(String rentno, Integer milend, Timestamp returndate, Integer fine,
			String rank, String note, String action) {
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

			RentOrdVO roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			roVO.setMilend(milend);
			roVO.setReturndate(returndate);
			roVO.setFine(fine);
			roVO.setRank(rank);
			roVO.setNote(note);
			roVO.setStatus("abnormalclosed");
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateRentOrdAfterNoreturn(String rentno, Integer milend, Timestamp returndate, Integer fine, String rank,
			String note, String action) {
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			RentOrdVO roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			roVO.setMilend(milend);
			roVO.setReturndate(returndate);
			roVO.setFine(fine);
			roVO.setRank(rank);
			roVO.setNote(note);
			roVO.setStatus("closed");
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	//overload for noshow
	@Override
	public void updateRentOrdStatusAfterAvailable(String rentno, String note, String action) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			RentOrdVO roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			roVO.setStatus("noreturn");
			roVO.setNote(note);

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateRentOrdStatusAfterNoshow(String rentno, String note, String action) {
		
//		private static final String UPDATE_RENT_ORD_STATUS_FROM_UNOCCUPIED_TO_ABNORMALCLOSED = 
//				"UPDATE RENT_ORD set status='abnormalclosed', note = ? where rentno = ? ";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			RentOrdVO roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			roVO.setStatus("abnormalclosed");
			roVO.setNote(note);

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateMotorStatusAfterNoshow(String motno, String action) {
		
//		private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
//				"UPDATE MOTOR set status='unleasable' where motno = ? ";
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			MotorVO motorVO = (MotorVO) session.get(MotorVO.class, motno);
			motorVO.setStatus("unleasable");

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateMotorAfterReturn(String motno, Integer mile, String rlocno, String action) {
		
//		private static final String UPDATE_MOTOR_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
//				"UPDATE MOTOR set mile = ?, status='unleasable', locno = ? where motno = ? ";

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			MotorVO motorVO = (MotorVO) session.get(MotorVO.class, motno);
			motorVO.setStatus("unleasable");
			motorVO.setMile(mile);
			motorVO.setLocno(rlocno);

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateMotorStatusAfterAvailable(String motno, String action) {
	
//		private static final String UPDATE_MOTOR_STATUS_FROM_RESERVE_TO_OCCUPIED = 
//				"UPDATE MOTOR set status='occupied' where motno = ? ";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			MotorVO motorVO = (MotorVO) session.get(MotorVO.class, motno);
			motorVO.setStatus("occupied");

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateEmtsAfterReturn(String emtno, String rlocno, String action) {
		
//		private static final String UPDATE_EMT_STATUS_FROM_OCCUPIED_TO_UNLEASABLE = 
//				"UPDATE EQUIPMENT set status='unleasable', locno = ? where emtno = ? ";
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			EquipmentVO emtVO = (EquipmentVO) session.get(EquipmentVO.class, emtno);
			emtVO.setLocno(rlocno);
			emtVO.setStatus("unleasable");

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateEmtsStatusAfterNoshow(String emtno, String action) {
//		private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_UNLEASABLE = 
//				"UPDATE EQUIPMENT set status='unleasable' where emtno = ? ";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			EquipmentVO emtVO = (EquipmentVO) session.get(EquipmentVO.class, emtno);
			emtVO.setStatus("unleasable");

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void updateEmtsStatusAfterAvailable(String emtno, String action) {
//		private static final String UPDATE_EMT_STATUS_FROM_RESERVE_TO_OCCUPIED = 
//				"UPDATE EQUIPMENT set status='occupied' where emtno = ? ";
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			
			EquipmentVO emtVO = (EquipmentVO) session.get(EquipmentVO.class, emtno);
			emtVO.setStatus("occupied");

			session.getTransaction().commit();
			
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}


	@Override
	public void insert(RentOrdVO roVO) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {

//		    final String INSERT_STMT = "INSERT INTO RENT_ORD"
//		    		+ " (rentno, memno, motno, slocno, rlocno, startdate, enddate, total, status "
//		    		+ " ) VALUES ('R'||LPAD(TO_CHAR(rentno_seq.NEXTVAL), 6,'0'), ?, ?, ?, ?,"
//		    		+ "  ?, ?, ?,?)";
			
			session.beginTransaction();
			
			Query query = session.createSQLQuery(INSERT_STMT);
			query.setParameter(0, roVO.getMemno());
			query.setParameter(1, roVO.getMotorVO().getMotno());
			query.setParameter(2, roVO.getSlocno());
			query.setParameter(3, roVO.getRlocno());
			query.setParameter(4, roVO.getStartdate());
			query.setParameter(5, roVO.getEnddate());
			query.setParameter(6, roVO.getTotal());
			query.setParameter(7, roVO.getStatus());
			query.executeUpdate();

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void update(RentOrdVO roVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			
			System.out.println("update RentOrdVO in");
//			private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
//					+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
//					+ " returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";
			session.beginTransaction();
			
			RentOrdVO exeVO = (RentOrdVO) session.get(RentOrdVO.class, roVO.getRentno());
			
				
				exeVO.setRank(roVO.getRank());
				exeVO.setNote(roVO.getNote());
				exeVO.setStatus(roVO.getStatus());
				exeVO.setSlocno(roVO.getSlocno());
				exeVO.setRlocno(roVO.getRlocno());

				
				exeVO.setStartdate(roVO.getStartdate());
				exeVO.setEnddate(roVO.getEnddate());
				exeVO.setReturndate(roVO.getReturndate());
				
				if(roVO.getFine()!=-1){
					exeVO.setFine(roVO.getFine());
					System.out.println("roVO.getFine() "+ roVO.getFine());
				}
					
				if(roVO.getTotal()!=-1){
					exeVO.setTotal(roVO.getTotal());
					System.out.println("roVO.getTotal() "+ roVO.getTotal());
				}
					
				if((roVO.getMilend()!=-1&&roVO.getMilend()>=roVO.getMilstart())){
					exeVO.setMilend(roVO.getMilend());
					System.out.println("roVO.getMilend() "+ roVO.getMilend());
				}
					
				if(roVO.getMilstart()!=-1&&roVO.getMilstart()<=roVO.getMilend()){
					exeVO.setMilstart(roVO.getMilstart());
					System.out.println("roVO.getFine() "+roVO.getFine());
				}
					
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.println("RuntimeException");
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void delete(String rentno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("delete RentOrdVO where rentno=?");
			query.setParameter(0, rentno);
			System.out.println("刪除的筆數=" + query.executeUpdate());
			session.getTransaction().commit();
			System.out.println("in RentOrdDAO delete end");
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public List<RentOrdVO> getAll() {
		List<RentOrdVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from RentOrdVO order by rentno desc");
			list = query.list();
			session.getTransaction().commit();

		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public RentOrdVO findByPrimaryKey(String rentno) {
		RentOrdVO roVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			roVO = (RentOrdVO) session.get(RentOrdVO.class, rentno);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return roVO;
	}

	@Override
	public Set<EquipmentVO> getEquipmentVOsByRentno(String rentno) {

		Set<EquipmentVO> set = null;
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			
			Query query = session.createQuery(GET_EMTNOs_BY_RENTNO_IN_EMT_LIST);
//			private static final String GET_EMTNOs_BY_RENTNO_IN_EMT_LIST= 
//					" SELECT emtno FROM EmtListVO where rentno = ? ";
			
			query.setParameter(0, rentno);
			
			List list = query.list();
			set = new LinkedHashSet<EquipmentVO>(list);
			session.getTransaction().commit();
			
//聯合映射前:
//				for(String emtno: list){
//				    	private static final String GET_EMPVOs_BY_EMTNOs_IN_EQUIPMENT=
//							" from EQUIPMENT where emtno = ? ";
//						query = session.createQuery(GET_EMPVOs_BY_EMTNOs_IN_EQUIPMENT);
//						query.setParameter(0, emtno);
//						
//						List<EquipmentVO> list2 = query.list();
//						
//						for(EquipmentVO eVO: list2){
//							set.add(eVO);
//						}
//					}	
				
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByStatus(String status) {
		Set<RentOrdVO> set = null;
		
		//private static final String GET_BY_STATUS = " from RentOrdVO where status = ?";

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Configuration config = new Configuration().configure();
//		SessionFactory sessionFactory = config.buildSessionFactory();
//		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_STATUS);
			query.setParameter(0, status);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByRentLoc(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();

//		private static final String GET_BY_START_LOC_NO =  
//		"from RentOrdVO where slocno = ?  order by rentno desc";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_START_LOC_NO);
			query.setParameter(0, slocno);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByReturnLoc(String rlocno) {
		Set<RentOrdVO> set = null;
		
//		private static final String GET_BY_RETURN_LOC_NO = 
//		"from RentOrdVO where rlocno = ?  order by rentno desc";
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_RETURN_LOC_NO);
			query.setParameter(0, rlocno);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByStartDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = null;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

//		private static final String GET_BY_STARTTIME_PEROID =
//				 " from RentOrdVO  where startdate  between ? and ? order by startdate";
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_STARTTIME_PEROID);
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersByEndDatePrioid(Timestamp start_time, Timestamp end_time) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
//		private static final String GET_BY_ENDTIME_PEROID = 
//				 " from RentOrdVO  where enddate"
//				+ " between ? and ? order by enddate";
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_ENDTIME_PEROID);
			query.setParameter(0, start_time);
			query.setParameter(1, end_time);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersForLeaseView(String slocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();		
		
//		private static final String GET_FOR_LEASE_VIEW = 
//				"from RentOrdVO where slocno=? "
//				+" and (status = 'unpaid' or status = 'unoccupied' or"
//				+" status = 'noshow' or status = 'available' or status = 'canceled')"
//				+" order by DECODE(status,'noshow',1,'available',2), status, startdate";
	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_FOR_LEASE_VIEW);
			query.setParameter(0, slocno);
			
			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);

			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}

	@Override
	public Set<RentOrdVO> getRentalOrdersForReturnView(String rlocno) {
		Set<RentOrdVO> set = new LinkedHashSet<RentOrdVO>();
		
//		private static final String GET_FOR_RETURN_VIEW = 
//				"from RentOrdVO where rlocno=? "
//				+" and (status = 'noreturn' or status = 'overtime')"
//				+"order by DECODE(status,'overtime',1,'noreturn',2), enddate";
		
Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_FOR_RETURN_VIEW);
			query.setParameter(0, rlocno);

			List list = query.list();
			set = new LinkedHashSet<RentOrdVO>(list);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return set;
	}
	
	@Override
	public String differDateCalculator(String rentno) {
	
			String differDate = null;
			
//			private static final String DIFFER_DATE_CALCULATOR = 
//				"select to_char(SYSDATE - TO_DATE(to_char(ENDDATE, 'yyyy/mm/dd'),'yyyy/mm/dd')) DIFFDAY "
//				+" from RentOrdVO where rentno = ? ";
	
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			System.out.println("differDateCalculator in");
			try {
				session.beginTransaction();
				Query query = session.createSQLQuery(DIFFER_DATE_CALCULATOR);
				query.setParameter(0, rentno);
	
				System.out.println("query.list().size()"+query.list().size());

				Integer num = (int) Math.ceil(Double.parseDouble((String) query.list().get(0)));
				differDate = num.toString();
				
				System.out.println("differDate : "+differDate);
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return differDate;
		}

	
	
	public static void main(String[] args) {

		RentOrdDAO dao = new RentOrdDAO();
		
//		updateStatusByRentno
//		dao.updateStatusByRentno("unoccupied", "R000640");
		
		//getRoVOsByDatePrioidAndMotno(Timestamp start_time, Timestamp end_time, String motno)
//		Set<RentOrdVO> set = dao.getRoVOsByDatePrioidAndMotno(java.sql.Timestamp.valueOf("2017-07-02 10:10:10"),
//				java.sql.Timestamp.valueOf("2017-09-09 10:10:10"), "M000001");
//			for(RentOrdVO roVO: set){
//				System.out.println("roVO.getRentno(): "+ roVO.getRentno()+" motno:"+ roVO.getMotorVO().getMotno());
//			}
			
			
		//getEmtnoByRentno
//			List<EquipmentVO> list = dao.getEmtnoByRentno("R000610");
//			for(EquipmentVO eVO:list)
//				System.out.println("emtno: "+ eVO.getEmtno());
			
			
//		//getRentnoByRentalPeriod
//			List<String> list = dao.getRentnoByRentalPeriod(java.sql.Timestamp.valueOf("2017-07-02 10:10:10"),
//					java.sql.Timestamp.valueOf("2017-09-09 10:10:10"));
//			for(String rentno :list)
//				System.out.println("rentno: "+ rentno);	
//			System.out.println("=================================");
//		
//		//getRentnoByMemnoAndStartdate
//		String str = dao.getRentnoByMemnoAndStartdate("MEM000002",
//				java.sql.Timestamp.valueOf("2017-07-18 23:00:00"));
//		System.out.println("rentno: "+str);
//		System.out.println("=================================");
//		//getRentalOrdersBymotno
//		Set<RentOrdVO> set = dao.getRentalOrdersBymotno("M000001");
//		for(RentOrdVO roVO: set){
//			System.out.print("roVO rentno :"+roVO.getRentno());
//			System.out.print("roVO motno :"+roVO.getMotorVO().getMotno());
//			System.out.println("roVO startday :"+roVO.getStartdate());
//		}
//		System.out.println("=================================");
		
		//getMotnoInRentOrdByRentalPeriod
//			List<MotorVO> list = dao.getMotnoInRentOrdByRentalPeriod(java.sql.Timestamp.valueOf("2017-08-02 10:10:10"),
//					java.sql.Timestamp.valueOf("2017-09-09 10:10:10"));
//			for(MotorVO mVO :list)
//				System.out.println("motno: "+ mVO.getMotno());	
			

		//updateRentOrdAfterOvertime
//			dao.updateRentOrdAfterOvertime("R000640", 1000, 
//					java.sql.Timestamp.valueOf("2017-09-09 10:10:10"),
//					100, "2", "test", "update");
			
		//updateRentOrdAfterNoreturn
//			dao.updateRentOrdAfterNoreturn("R000628", 1000, java.sql.Timestamp.valueOf("2017-09-10 10:10:10"), 
//					100, "3", "test", "update2");
			
		//updateRentOrdStatusAfterAvailable
//			dao.updateRentOrdStatusAfterAvailable("R000635", "ttava", "wow");
			
		//updateRentOrdStatusAfterNoshow
//			dao.updateRentOrdStatusAfterNoshow("R000634", "ttava", "wow2");
			
		//updateMotorStatusAfterNoshow
//			dao.updateMotorStatusAfterNoshow("M000010", "action");
			
		//updateMotorAfterReturn
//			dao.updateMotorAfterReturn("M000010", 2000, "TPE01", "action");
			
		//updateMotorStatusAfterAvailable
//			dao.updateMotorStatusAfterAvailable("M000010", "action");
			
		//updateEmtsAfterReturn
//			dao.updateEmtsAfterReturn("E000011", "TPE01", "action");
			
		//updateEmtsStatusAfterNoshow
//			dao.updateEmtsStatusAfterNoshow("E000011", "action");
			
		//updateEmtsStatusAfterAvailable
//			dao.updateEmtsStatusAfterAvailable("E000011", "action");
		

		//insert
//		RentOrdVO roVO = new RentOrdVO();
//		roVO.setMemno("MEM000002");
//		roVO.setMotorVO(new MotorForRentOrdService().findByPK("M000101"));
//		roVO.setSlocno("TXG01");
//		roVO.setRlocno("TXG01");
//		roVO.setStartdate(java.sql.Timestamp.valueOf("2017-09-13 10:10:10"));
//		roVO.setEnddate(java.sql.Timestamp.valueOf("2017-09-14 10:10:10"));
//		roVO.setTotal(5000);
//		roVO.setStatus("unpaid");
//		dao.insert(roVO);

		
//		private static final String UPDATE = "UPDATE RENT_ORD set  motno=?,"
//		+ " slocno=?, rlocno=?, milstart=?, milend=?, startdate=?, enddate=?,"
//		+ " returndate=?, fine=?, total=?, rank=?, status=?, note=? where rentno = ?";
		//update
//		  RentOrdVO roVO2 = new RentOrdVO(); 
//		  roVO2.setMemno("memno2");
//		  roVO2.setMotorVO(new MotorForRentOrdService().findByPK("M000101")); 
//		  roVO2.setSlocno("slocno2");
//		  roVO2.setRlocno("rlocno2"); 
//		  roVO2.setMilstart(211111);
//		  roVO2.setMilend(322222);
//		  //roVO2.setFilldate(java.sql.Timestamp.valueOf("2017-06-03 10:10:10"));
//		  roVO2.setStartdate(java.sql.Timestamp.valueOf("2017-08-06 10:10:10"));
//		  roVO2.setEnddate(java.sql.Timestamp.valueOf("2017-08-09 10:10:10"));
//		  roVO2.setReturndate(java.sql.Timestamp.valueOf("2017-08-10 10:10:10")); 
//		  roVO2.setFine(15000); 
//		  roVO2.setTotal(220000); 
//		  roVO2.setRank("5");
//		  roVO2.setStatus("unoccupied"); 
//		  roVO2.setNote("test3");
//		  roVO2.setRentno("R000639"); 
//		  dao.update(roVO2);	

		//delete
//		dao.delete("R000639");
		
		//getAll
//		List<RentOrdVO> list = dao.getAll();
//		for(RentOrdVO roVO:list){
//			System.out.print(roVO.getRentno()+", ");
//			System.out.print(roVO.getMotorVO().getMotno()+", ");
//			System.out.println(roVO.getSlocno());
//		}
//		System.out.println(list.size());
		
		//findbyPK
//		RentOrdVO roVO =dao.findByPrimaryKey("R000638");
//		System.out.println("rentno: "+ roVO.getRentno()+", monto"+ roVO.getMotorVO().getMotno());
		
		//getEquipmentVOsByRentno
//		Set<EquipmentVO> set = dao.getEquipmentVOsByRentno("R000573");
//		for(EquipmentVO eVO:set)
//			System.out.println(eVO.getEmtno()+", "+eVO.getLocno());
		
		//getRentalOrdersByStatus(String status)
//		Set<RentOrdVO> set = dao.getRentalOrdersByStatus("overtime");
//		for(RentOrdVO roVO :set)
//			System.out.println(roVO.getRentno()+" "+roVO.getMotorVO().getMotno()+" "+roVO.getSlocno()+" "+roVO.getStatus());
		
		//getRentalOrdersByRentLoc
//		Set<RentOrdVO> set = dao.getRentalOrdersByRentLoc("TPE01");
//		for(RentOrdVO roVO :set)
//			System.out.println(roVO.getRentno()+" "+roVO.getMotorVO().getMotno()+" "+roVO.getSlocno()+" "+roVO.getStatus());		
		
		//getRentalOrdersByReturnLoc
//		Set<RentOrdVO> set = dao.getRentalOrdersByReturnLoc("TPE01");
//		for(RentOrdVO roVO :set)
//			System.out.println(roVO.getRentno()+" "+roVO.getMotorVO().getMotno()+" "+roVO.getRlocno()+" "+roVO.getStatus());			
		
		//getRentalOrdersByStartDatePrioid
//		Set<RentOrdVO> set = dao.getRentalOrdersByStartDatePrioid(java.sql.Timestamp.valueOf("2017-07-10 10:10:10"),
//				java.sql.Timestamp.valueOf("2017-08-10 10:10:10"));
//		for(RentOrdVO roVO :set)
//		System.out.println(roVO.getRentno()+" "+roVO.getStartdate()+" "+roVO.getRlocno()+" "+roVO.getStatus());	
		
		
//		getRentalOrdersByEndDatePrioid
//		Set<RentOrdVO> set = dao.getRentalOrdersByEndDatePrioid(java.sql.Timestamp.valueOf("2017-07-10 10:10:10"),
//				java.sql.Timestamp.valueOf("2017-08-10 10:10:10"));
//		for(RentOrdVO roVO :set)
//		System.out.println(roVO.getRentno()+" "+roVO.getEnddate()+" "+roVO.getRlocno()+" "+roVO.getStatus());
		
		//getRentalOrdersForLeaseView
//		Set<RentOrdVO> set = dao.getRentalOrdersForLeaseView("TPE01");
//		for(RentOrdVO roVO :set)
//		System.out.println(roVO.getRentno()+" "+roVO.getEnddate()+" "+roVO.getSlocno()+" "+roVO.getStatus());		
		
		//getRentalOrdersForReturnView
//		Set<RentOrdVO> set = dao.getRentalOrdersForReturnView("TPE01");
//		for(RentOrdVO roVO :set)
//		System.out.println(roVO.getRentno()+" "+roVO.getEnddate()+" "+roVO.getRlocno()+" "+roVO.getStatus());		
		
		//differDateCalculator
//		String str = dao.differDateCalculator("R000631");
//		System.out.println("diffday" + str);
	}
}
