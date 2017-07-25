package com.equipment.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;


import hibernate.util.HibernateUtil;

public class EquipmentDAO implements EquipmentDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO EQUIPMENT (EMTNO, ECNO, LOCNO, PURCHDATE, NOTE) VALUES ('E'||lpad(to_char(ecno_seq.NEXTVAL),6,'0'), ?, 'TPE00', ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EQUIPMENT order by EMTNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EQUIPMENT where EMTNO = ?";
	private static final String DELETE = "DELETE FROM EQUIPMENT where EMTNO = ?";
	private static final String UPDATE = "UPDATE EQUIPMENT set ECNO=?, LOCNO=?, PURCHDATE=?, STATUS=?, NOTE=? where EMTNO = ?";
	
	//以下為Hibernate用
		private static final String GET_ALL_BY_HIBERNATE = "from EquipmentVO order by ecno";
		private static final String GET_BY_ECNO_BY_HIBERNATE = "from EquipmentVO where ecno = ? order by emtno";
		private static final String UPDATE_STATUS_BY_HIBERNATE = "update EquipmentVO set status = ? where emtno = ?";
		private static final String GET_ECNO_BY_LOCNO = "SELECT distinct emtCateVO.ecno FROM EquipmentVO where locationVO.locno <> ? order by ecno";
		private static final String GET_BY_ECNO_AND_LOCNO = "FROM EquipmentVO where emtCateVO.ecno = ? and locationVO.locno <> ? and status in ('leasable','unleasable')";
		private static final String GET_BY_LOCNO = "FROM EquipmentVO where locationVO.locno = ?";
		
	@Override
	public void insert(EquipmentVO emtVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtVO.getEcno());
			pstmt.setTimestamp(2, emtVO.getPurchdate());
			pstmt.setString(3, emtVO.getNote());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public void update(EquipmentVO emtVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, emtVO.getEcno());
			pstmt.setString(2, emtVO.getLocno());
			pstmt.setTimestamp(3, emtVO.getPurchdate());
			pstmt.setString(4, emtVO.getStatus());
			pstmt.setString(5, emtVO.getNote());
			pstmt.setString(6, emtVO.getEmtno());
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public void delete(String emtno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emtno);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public EquipmentVO findByPrimaryKey(String emtno) {

		EquipmentVO emtVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, emtno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtVO 也稱為 Domain objects
				emtVO = new EquipmentVO();
				emtVO.setEmtno(rs.getString("emtno"));
				emtVO.setEcno(rs.getString("ecno"));
				emtVO.setLocno(rs.getString("locno"));
				emtVO.setPurchdate(rs.getTimestamp("purchdate"));
				emtVO.setStatus(rs.getString("status"));
				emtVO.setNote(rs.getString("note"));
			}

			// Handle any SQL errors
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
		return emtVO;
	}

	@Override
	public List<EquipmentVO> getAll() {
		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO emtVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtVO 也稱為 Domain objects
				emtVO = new EquipmentVO();
				emtVO.setEmtno(rs.getString("emtno"));
				emtVO.setEcno(rs.getString("ecno"));
				emtVO.setLocno(rs.getString("locno"));
				emtVO.setPurchdate(rs.getTimestamp("purchdate"));
				emtVO.setStatus(rs.getString("status"));
				emtVO.setNote(rs.getString("note"));
				list.add(emtVO); // Store the row in the list
			}

			// Handle any SQL errors
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
		return list;
	}

	//以下為hibernate用
		@Override
		public void insertByHib(EquipmentVO emtVO) {

			Session session = HibernateUtil.getSessionFactory().getCurrentSession();//取得session的最佳寫法
			try {
				session.beginTransaction();
				
				session.saveOrUpdate(emtVO);
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			//session及sessionFactory不用、也不能close()
		}

		@Override
		public void updateByHib(EquipmentVO emtVO) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(emtVO);
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		}

		@Override
		public void deleteByHib(String emtno) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();

//	        【此時多方(宜)可採用HQL刪除】
				Query query = session.createQuery("delete EquipmentVO where emtno=?");
				query.setParameter(0, emtno);
				System.out.println("刪除的筆數=" + query.executeUpdate());

//	        【或此時多方(也)可採用去除關聯關係後，再刪除的方式】
//				EquipmentVO emtVO = new EquipmentVO();
//				emtVO.setMotno(emtno);
//				session.delete(emtVO);

//	        【此時多方不可(不宜)採用cascade聯級刪除】
//	        【多方emp2.hbm.xml如果設為 cascade="all"或 cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除】
//				EquipmentVO emtVO = (EquipmentVO) session.get(EquipmentVO.class, emtno);
//				session.delete(emtVO);

				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		}
		
		@Override
		public EquipmentVO findByPkByHib(String emtno) {
			EquipmentVO emtVO = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				emtVO = (EquipmentVO) session.get(EquipmentVO.class, emtno);
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return emtVO;
		}
		
		@Override
		public List<EquipmentVO> getAllByHib() {
			List<EquipmentVO> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_ALL_BY_HIBERNATE);
				list = query.list();
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return list;
		}
		
		
		@Override
		public List<EquipmentVO> getEmtsByEcnoByHib(String ecno) {
			List<EquipmentVO> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_BY_ECNO_BY_HIBERNATE);
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
		public void updateStatusByHib(String emtno, String status){
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(UPDATE_STATUS_BY_HIBERNATE);
				query.setParameter(0, status);
				query.setParameter(1, emtno);
				int updateCount = query.executeUpdate();
				System.out.println("updateStatusByHib: " + updateCount);
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		}
		
		@Override
		public List<String> getEcnoByLocnoByHib(String locno) {
			List<String> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_ECNO_BY_LOCNO);
				query.setParameter(0, locno);
				list =  query.list();
				
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return list;
		}
		
		@Override
		public List<EquipmentVO> getEmtsByEcnoAndLocnoByHib(String ecno, String locno) {
			List<EquipmentVO> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_BY_ECNO_AND_LOCNO);
				query.setParameter(0, ecno);
				query.setParameter(1, locno);
				list =  query.list();
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return list;
		}
		
		@Override
		public List<EquipmentVO> getEmtsByLocnoByHib(String locno) {
			List<EquipmentVO> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_BY_LOCNO);
				query.setParameter(0, locno);
				list =  query.list();
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return list;
		}
		
		
		public static void main(String[] args) {

			EquipmentDAO dao = new EquipmentDAO();
			List<String> list = dao.getEcnoByLocnoByHib("TPE00");
			for (String aEmt : list) {
				System.out.println(aEmt);
				
				//String xx = aEmp.getEmtCateVO().getEcno();
//			System.out.print(aEmp.getEmtno() + ",");
		//	System.out.print(xx + ",");
//			System.out.print(aEmp.getPlateno() + ",");
//			System.out.print(aEmp.getEngno() + ",");
//			System.out.print(aEmp.getManudate() + ",");
//			System.out.print(aEmp.getMile() + ",");
//			System.out.print(aEmp.getLocno() + ",");
//			System.out.print(aEmp.getStatus() + ",");
//			System.out.print(aEmp.getNote() + ",");
//			 注意以下三行的寫法 (優!)
//			System.out.print(aEmp.getEquipmentModelVO().getModtype() + ",");
//			System.out.print(aEmp.getEquipmentModelVO().getRenprice() + ",");
//			System.out.print(aEmp.getDeptVO().getDname() + ",");
//			System.out.print(aEmp.getDeptVO().getLoc());
//			System.out.println();
		}
//			dao.updateStatusByHib("M000001", "dispatching");
			//● 新增
//			EquipmentModelVO mmVO = new EquipmentModelVO(); // 部門POJO
//			mmVO.setModtype("MM102");

//			EquipmentVO empVO1 = new EquipmentVO();
//			empVO1.setPlateno("ABC123");
//			empVO1.setEngno("DF123456");
//			empVO1.setMile(1000);
//			empVO1.setLocno("TPE01");
//			empVO1.setManudate(java.sql.Timestamp.valueOf("2016-01-01 10:10:10"));
//			empVO1.setEquipmentModelVO(mmVO);
//			dao.insertByHib(empVO1);



			//● 修改
//			EquipmentVO mVO = new EquipmentVO();
//			mVO.setMotno("M000053");
//			mVO.setPlateno("BDC321");
//			mVO.setEngno("DF654321");
//			mVO.setMile(2111);
//			mVO.setLocno("TPE00");
//			mVO.setManudate(java.sql.Timestamp.valueOf("2016-01-01 10:10:10"));
//			mVO.setEquipmentModelVO(mmVO);
//			dao.updateByHib(mVO);



			//● 刪除(小心cascade - 多方emp2.hbm.xml如果設為 cascade="all"或
			// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
//			dao.deleteByHib("M000053");



			//● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
//			EmpVO empVO3 = dao.findByPrimaryKey(7001);
//			System.out.print(empVO3.getEmpno() + ",");
//			System.out.print(empVO3.getEname() + ",");
//			System.out.print(empVO3.getJob() + ",");
//			System.out.print(empVO3.getHiredate() + ",");
//			System.out.print(empVO3.getSal() + ",");
//			System.out.print(empVO3.getComm() + ",");
//			// 注意以下三行的寫法 (優!)
//			System.out.print(empVO3.getDeptVO().getDeptno() + ",");
//			System.out.print(empVO3.getDeptVO().getDname() + ",");
//			System.out.print(empVO3.getDeptVO().getLoc());
//			System.out.println("\n---------------------");



			//● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
//			List<EquipmentVO> list = dao.getAllByHib();
//			for (EquipmentVO aEmp : list) {
//				System.out.print(aEmp.getEmtno() + ",");
//				System.out.print(aEmp.getEcno() + ",");
//				System.out.print(aEmp.getPlateno() + ",");
//				System.out.print(aEmp.getEngno() + ",");
//				System.out.print(aEmp.getManudate() + ",");
//				System.out.print(aEmp.getMile() + ",");
//				System.out.print(aEmp.getLocno() + ",");
//				System.out.print(aEmp.getStatus() + ",");
//				System.out.print(aEmp.getNote() + ",");
				// 注意以下三行的寫法 (優!)
//				System.out.print(aEmp.getEquipmentModelVO().getModtype() + ",");
//				System.out.print(aEmp.getEquipmentModelVO().getRenprice() + ",");
//				System.out.print(aEmp.getDeptVO().getDname() + ",");
//				System.out.print(aEmp.getDeptVO().getLoc());
//				System.out.println();
//			}
		}
}
	
