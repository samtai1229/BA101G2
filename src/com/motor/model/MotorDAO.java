package com.motor.model;
/*
Hibernate is providing a factory.getCurrentSession() method for retrieving the current session. A
new session is opened for the first time of calling this method, and closed when the transaction is
finished, no matter commit or rollback. But what does it mean by the “current session”? We need to
tell Hibernate that it should be the session bound with the current thread.

<hibernate-configuration>
<session-factory>
...
<property name="current_session_context_class">thread</property>
...
</session-factory>
</hibernate-configuration>

*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import hibernate.util.HibernateUtil;

import com.motor_model.model.MotorModelVO;

public class MotorDAO implements MotorDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO MOTOR (motno, modtype, plateno,"
			+ " engno, manudate, mile, note, locno"
			+ ") VALUES ('M'||LPAD(TO_CHAR(MOTNO_SEQ.NEXTVAL), 6,'0'), ?, ?, ?, ?, ?, ?, 'TPE00')";

	private static final String UPDATE = "UPDATE MOTOR set modtype=?, plateno=?,"
			+ " engno=?, manudate=?, mile=?, locno=?," + "status=?, note=? where motno = ?";

	private static final String DELETE = "DELETE FROM motor where motno = ?";
	private static final String GET_ALL = "SELECT motno, modtype, plateno,"
			+ " engno, manudate, mile, locno, status, note FROM motor order by motno";

	private static final String GET_ONE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where motno = ?";

	private static final String GET_BY_MOTOR_TYPE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where modtype = ?";

	private static final String GET_BY_LOC_NO = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where locno = ?";

	private static final String GET_BY_MANUDATE = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor  where manudate" + " between ? and ? order by manudate";
	
	private static final String FUZZY_SEARCH = 
		    "SELECT m.motno, m.modtype, d.name, d.displacement, d.renprice, d.brand, m.plateno, m.engno, m.manudate, m.mile, m.locno, d.saleprice,m.status, m.note FROM MOTOR m join motor_model d ON m.modtype = d.modtype where m.MOTNO LIKE ? or m.MODTYPE LIKE ? or m.PLATENO LIKE ? or m.ENGNO LIKE ? or m.MANUDATE LIKE ? or m.MILE LIKE ? or m.LOCNO LIKE ? or m.STATUS LIKE ? or m.NOTE LIKE ? or d.NAME LIKE ? or d.DISPLACEMENT LIKE ? or d.RENPRICE LIKE ? or d.SALEPRICE LIKE ? or d.BRAND LIKE ? ORDER BY m.MOTNO";
//			"SELECT motno, modtype, plateno,"
//			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
//			+ " mile, locno, status, note FROM MOTOR where MOTNO LIKE ? or MODTYPE LIKE ? or PLATENO LIKE ? or ENGNO LIKE ? or MANUDATE LIKE ? or MILE LIKE ? or LOCNO LIKE ? or STATUS LIKE ? or NOTE LIKE ?  ORDER BY MOTNO";

//	private static final String GET_ALL_STATUS="slelect distinct status from motor";
	
	private static final String GET_MODTYPE_BY_LOCNO = "SELECT distinct modtype FROM MOTOR where locno <> ?";
	
	private static final String GET_BY_MODTYP_AND_LOCNO = "SELECT motno, modtype, plateno,"
			+ " engno, to_char(manudate,'yyyy-mm-dd hh:mm:ss') manudate,"
			+ " mile, locno, status, note FROM motor where modtype = ? and locno <> ? and status in ('leasable','unleasable')";
	
//以下為Hibernate用
	private static final String GET_ALL_STMT = "from MotorVO order by MOTNO";
	private static final String FUZZY_SEARCH_BY_HIBERNATE ="FROM MotorVO where motno LIKE ? or motorModelVO.modtype LIKE ? or plateno LIKE ? or engno LIKE ?  or mile LIKE ? or locno LIKE ? or status LIKE ? or note LIKE ? or motorModelVO.name LIKE ? or motorModelVO.displacement LIKE ? or motorModelVO.renprice LIKE ? or motorModelVO.saleprice LIKE ? or motorModelVO.brand LIKE ? ORDER BY motno"; 
//			
	private static final String GET_BY_MODTYPE_BY_HIBERNATE = "from MotorVO where modtype = ? order by MOTNO";
	private static final String UPDATE_STATUS_BY_HIBERNATE = "update MotorVO set status = ? where motno = ?";
	
	@Override
	public void insert(MotorVO motorVO) {
		
		System.out.println("MotorDAO insert in");
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, motorVO.getModtype());
			pstmt.setString(2, motorVO.getPlateno());
			pstmt.setString(3, motorVO.getEngno());
			pstmt.setTimestamp(4, motorVO.getManudate());
			pstmt.setInt(5, motorVO.getMile());
			pstmt.setString(6, motorVO.getNote());

			pstmt.executeUpdate();

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
	public void update(MotorVO motorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, motorVO.getModtype());
			pstmt.setString(2, motorVO.getPlateno());
			pstmt.setString(3, motorVO.getEngno());
			pstmt.setTimestamp(4, motorVO.getManudate());
			pstmt.setInt(5, motorVO.getMile());
			pstmt.setString(6, motorVO.getLocno());
			pstmt.setString(7, motorVO.getStatus());
			pstmt.setString(8, motorVO.getNote());
			pstmt.setString(9, motorVO.getMotno());

			pstmt.executeUpdate();

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
	public void delete(String motno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, motno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public MotorVO findByPrimaryKey(String motno) {

		MotorVO motorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
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
		return motorVO;
	}

	@Override
	public List<MotorVO> getAll() {
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				list.add(motorVO); // Store the row in the list
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	@Override
	public Set<MotorVO> getMotorsByModelType(String modtype) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MOTOR_TYPE);
			pstmt.setString(1, modtype);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return set;
	}

	@Override
	public Set<MotorVO> getMotorsByLocNo(String locno) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_LOC_NO);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return set;
	}

	@Override
	public Set<MotorVO> getMotorsByManuDate(Timestamp start_time, Timestamp end_time) {
		Set<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MANUDATE);
			pstmt.setTimestamp(1, start_time);
			pstmt.setTimestamp(2, end_time);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				set.add(motorVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return set;
	}

	private void setAttirbute(MotorVO motorVO, ResultSet rs) {
		try {
			motorVO.setMotno(rs.getString("motno"));
			motorVO.setModtype(rs.getString("modtype"));
			motorVO.setPlateno(rs.getString("plateno"));
			motorVO.setEngno(rs.getString("engno"));			
//			String str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getTimestamp("manudate"));			
//			motorVO.setManudate(Timestamp.valueOf(str));			
			motorVO.setManudate(rs.getTimestamp("manudate"));			
			motorVO.setMile(rs.getInt("mile"));
			motorVO.setLocno(rs.getString("locno"));
			motorVO.setStatus(rs.getString("status"));
			motorVO.setNote(rs.getString("note"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MotorVO> fuzzyGetAll(String fuzzyValue) {
		
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FUZZY_SEARCH);

			pstmt.setString(1, "%"+fuzzyValue+"%");
			pstmt.setString(2, "%"+fuzzyValue+"%");
			pstmt.setString(3, "%"+fuzzyValue+"%");
			pstmt.setString(4, "%"+fuzzyValue+"%");
			pstmt.setString(5, "%"+fuzzyValue+"%");
			pstmt.setString(6, "%"+fuzzyValue+"%");
			pstmt.setString(7, "%"+fuzzyValue+"%");
			pstmt.setString(8, "%"+fuzzyValue+"%");
			pstmt.setString(9, "%"+fuzzyValue+"%");
			pstmt.setString(10, "%"+fuzzyValue+"%");
			pstmt.setString(11, "%"+fuzzyValue+"%");
			pstmt.setString(12, "%"+fuzzyValue+"%");
			pstmt.setString(13, "%"+fuzzyValue+"%");
			pstmt.setString(14, "%"+fuzzyValue+"%");
		
System.out.println("motorDAO: "+"'%" + fuzzyValue + "%'");	
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				motorVO = new MotorVO();

				motorVO.setMotno(rs.getString("motno"));
				motorVO.setModtype(rs.getString("modtype"));
				motorVO.setPlateno(rs.getString("plateno"));
				motorVO.setEngno(rs.getString("engno"));						
				motorVO.setManudate(rs.getTimestamp("manudate"));			
				motorVO.setMile(rs.getInt("mile"));
				motorVO.setLocno(rs.getString("locno"));
				motorVO.setStatus(rs.getString("status"));
				motorVO.setNote(rs.getString("note"));
				motorVO.setBrand(rs.getString("brand"));
				motorVO.setDisplacement(rs.getInt("displacement"));
				motorVO.setName(rs.getString("name"));
				motorVO.setRenprice(rs.getInt("renprice"));
				motorVO.setSaleprice(rs.getInt("saleprice"));
				list.add(motorVO); 
				
System.out.println("motorLsitString: "+list.toString());
				
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
System.out.println("motorDAOlist: " + list);
		return list;
	}

	@Override
	public HashSet<MotorVO> getModtypeByLocNo(String locno) {
		HashSet<MotorVO> set = new LinkedHashSet<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MODTYPE_BY_LOCNO);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				motorVO.setModtype(rs.getString("modtype"));
				set.add(motorVO); // Store the row in the vector
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return set;
	}
	
	@Override
	public List<MotorVO> getMotorsByModtypeAndLocno(String modtype, String locno) {
		List<MotorVO> list = new ArrayList<MotorVO>();
		MotorVO motorVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_MODTYP_AND_LOCNO);
			pstmt.setString(1, modtype);
			pstmt.setString(2, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				motorVO = new MotorVO();
				setAttirbute(motorVO, rs); // 拉出來寫成一個方法
				list.add(motorVO); // Store the row in the vector
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void insertByHib(MotorVO motorVO) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();//取得session的最佳寫法
		try {
			session.beginTransaction();
			
			session.saveOrUpdate(motorVO);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		//session及sessionFactory不用、也不能close()
	}

	@Override
	public void updateByHib(MotorVO motorVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(motorVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void deleteByHib(String motno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

//        【此時多方(宜)可採用HQL刪除】
			Query query = session.createQuery("delete MotorVO where motno=?");
			query.setParameter(0, motno);
			System.out.println("刪除的筆數=" + query.executeUpdate());

//        【或此時多方(也)可採用去除關聯關係後，再刪除的方式】
//			MotorVO motorVO = new MotorVO();
//			motorVO.setMotno(motno);
//			session.delete(motorVO);

//        【此時多方不可(不宜)採用cascade聯級刪除】
//        【多方emp2.hbm.xml如果設為 cascade="all"或 cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除】
//			MotorVO motorVO = (MotorVO) session.get(MotorVO.class, motno);
//			session.delete(motorVO);

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	
	@Override
	public MotorVO findByPkByHib(String motno) {
		MotorVO motorVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			motorVO = (MotorVO) session.get(MotorVO.class, motno);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return motorVO;
	}
	
	@Override
	public List<MotorVO> getAllByHib() {
		List<MotorVO> list = null;
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
	
	@Override
	public List<MotorVO> fuzzySearchByHib(String fuzzyValue) {
		List<MotorVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		Transaction tx = session.beginTransaction();
		try {
			session.beginTransaction();
//			Criteria query = session.createCriteria(MotorVO.class);
//			Criteria query2 = session.createCriteria(MotorModelVO.class);
//			query.add( Restrictions.like("motno", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("modtype", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("plateno", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("engno", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("manudate", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("mile", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("locno", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("status", "%"+fuzzyValue+"%"));
//			query.add( Restrictions.like("note", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("brand", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("displacement", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("name", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("renprice", "%"+fuzzyValue+"%"));
//			query2.add( Restrictions.like("saleprice", "%"+fuzzyValue+"%"));
			
			Query query = session.createQuery(FUZZY_SEARCH_BY_HIBERNATE);
			
			query.setParameter(0, "%"+fuzzyValue+"%");
			query.setParameter(1, "%"+fuzzyValue+"%");
			query.setParameter(2, "%"+fuzzyValue+"%");
			query.setParameter(3, "%"+fuzzyValue+"%");
			query.setParameter(4, "%"+fuzzyValue+"%");
			query.setParameter(5, "%"+fuzzyValue+"%");
			query.setParameter(6, "%"+fuzzyValue+"%");
			query.setParameter(7, "%"+fuzzyValue+"%");
			query.setParameter(8, "%"+fuzzyValue+"%");
			query.setParameter(9, "%"+fuzzyValue+"%");
			query.setParameter(10, "%"+fuzzyValue+"%");
			query.setParameter(11, "%"+fuzzyValue+"%");
			query.setParameter(12, "%"+fuzzyValue+"%");
//			query.setParameter(13, "%"+fuzzyValue+"%");
			list = query.list();
//			tx.commit();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
//			if (tx != null)
//				tx.rollback();
//			throw ex;
			session.getTransaction().rollback();
			throw ex;
		} 
		return list;
	}
	
	@Override
	public List<MotorVO> getMotorsByModtypeByHib(String modtype) {
		List<MotorVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_MODTYPE_BY_HIBERNATE);
			query.setParameter(0, modtype);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public void updateStatusByHib(String motno, String status){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(UPDATE_STATUS_BY_HIBERNATE);
			query.setParameter(0, status);
			query.setParameter(1, motno);
			int updateCount = query.executeUpdate();
			System.out.println("updateStatusByHib: " + updateCount);
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
		
	
	
	public static void main(String[] args) {

		MotorDAO dao = new MotorDAO();
		dao.fuzzySearchByHib("MM101");
		//● 新增
//		MotorModelVO mmVO = new MotorModelVO(); // 部門POJO
//		mmVO.setModtype("MM102");

//		MotorVO empVO1 = new MotorVO();
//		empVO1.setPlateno("ABC123");
//		empVO1.setEngno("DF123456");
//		empVO1.setMile(1000);
//		empVO1.setLocno("TPE01");
//		empVO1.setManudate(java.sql.Timestamp.valueOf("2016-01-01 10:10:10"));
//		empVO1.setMotorModelVO(mmVO);
//		dao.insertByHib(empVO1);



		//● 修改
//		MotorVO mVO = new MotorVO();
//		mVO.setMotno("M000053");
//		mVO.setPlateno("BDC321");
//		mVO.setEngno("DF654321");
//		mVO.setMile(2111);
//		mVO.setLocno("TPE00");
//		mVO.setManudate(java.sql.Timestamp.valueOf("2016-01-01 10:10:10"));
//		mVO.setMotorModelVO(mmVO);
//		dao.updateByHib(mVO);



		//● 刪除(小心cascade - 多方emp2.hbm.xml如果設為 cascade="all"或
		// cascade="delete"將會刪除所有相關資料-包括所屬部門與同部門的其它員工將會一併被刪除)
//		dao.deleteByHib("M000053");



		//● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		EmpVO empVO3 = dao.findByPrimaryKey(7001);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getComm() + ",");
//		// 注意以下三行的寫法 (優!)
//		System.out.print(empVO3.getDeptVO().getDeptno() + ",");
//		System.out.print(empVO3.getDeptVO().getDname() + ",");
//		System.out.print(empVO3.getDeptVO().getLoc());
//		System.out.println("\n---------------------");



		//● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
		List<MotorVO> list = dao.getMotorsByModtypeByHib("MM101");
		for (MotorVO aEmp : list) {
			System.out.print(aEmp.getMotno() + ",");
//			System.out.print(aEmp.getModtype() + ",");
			System.out.print(aEmp.getPlateno() + ",");
			System.out.print(aEmp.getEngno() + ",");
			System.out.print(aEmp.getManudate() + ",");
			System.out.print(aEmp.getMile() + ",");
			System.out.print(aEmp.getLocno() + ",");
			System.out.print(aEmp.getStatus() + ",");
			System.out.print(aEmp.getNote() + ",");
			// 注意以下三行的寫法 (優!)
			System.out.print(aEmp.getMotorModelVO().getModtype() + ",");
			System.out.print(aEmp.getMotorModelVO().getRenprice() + ",");
//			System.out.print(aEmp.getDeptVO().getDname() + ",");
//			System.out.print(aEmp.getDeptVO().getLoc());
			System.out.println();
		}
	}
}
