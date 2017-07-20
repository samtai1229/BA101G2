package com.motor_dispatch.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.motor.model.MotorVO;
import com.motor_disp_list.model.MotorDispListVO;
import com.motor_model.model.MotorModelVO;
import com.rent_ord.model.RentOrdVO;

import hibernate.util.HibernateUtil;

public class MotorDispatchDAO implements MotorDispatchDAO_interface {
//	 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/G2DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO MOTOR_DISPATCH" 
			+ " (mdno, locno) VALUES ('MD'||LPAD(TO_CHAR(mdno_seq.NEXTVAL), 6,'0'), ?)";

	private static final String UPDATE = "UPDATE MOTOR_DISPATCH set locno=?,"
			+ " filldate=?, closeddate=?, prog=? where mdno = ?";

	private static final String DELETE = "DELETE FROM MOTOR_DISPATCH where mdno = ?";

	private static final String GET_ONE = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where mdno = ?";

	private static final String GET_ALL = "SELECT mdno, locno, filldate," 
			+ "  closeddate, prog FROM MOTOR_DISPATCH";

	private static final String GET_BY_LOC = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where locno = ?";

	private static final String GET_BY_PROG = "SELECT mdno, locno, filldate,"
			+ "  closeddate, prog FROM MOTOR_DISPATCH where prog = ?";
	
	private static final String GET_DISPATCHABLE_DATE = 
	"select motno from rent_ord where motno = ? and (sysdate not between STARTDATE and ENDDATE) and (sysdate+1 not between STARTDATE and ENDDATE)and (sysdate+2 not between STARTDATE and ENDDATE)";
	//以下為hibernate用
	private static final String GET_ALL_STMT = "from MotorDispatchVO order by mdno desc";
	private static final String GET_BY_LOCNO = "from MotorDispatchVO where locno = ? order by mdno desc";
	private static final String CANCEL = "update MotorDispatchVO set prog = 'canceled', closeddate = systimestamp where mdno = ?";
	
	
	@Override
	public void insert(MotorDispatchVO mdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			// mdno, locno

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mdVO.getLocno());

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
	public void update(MotorDispatchVO mdVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mdVO.getLocno());
			pstmt.setTimestamp(2, mdVO.getFilldate());
			pstmt.setTimestamp(3, mdVO.getCloseddate());
			pstmt.setString(4, mdVO.getProg());
			pstmt.setString(5, mdVO.getMdno());

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
	public void delete(String mdno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, mdno);
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
	public MotorDispatchVO findByPrimaryKey(String mdno) {

		MotorDispatchVO mdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, mdno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
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
		return mdVO;
	}

	private void setAttirbute(MotorDispatchVO mdVO, ResultSet rs) {
		try {

			mdVO.setMdno(rs.getString("mdno"));
			mdVO.setLocno(rs.getString("locno"));
			mdVO.setFilldate(rs.getTimestamp("filldate"));
			mdVO.setCloseddate(rs.getTimestamp("closeddate"));
			mdVO.setProg(rs.getString("prog"));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MotorDispatchVO> getAll() {
		List<MotorDispatchVO> list = new ArrayList<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				list.add(mdVO); // Store the row in the list
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
	public Set<MotorDispatchVO> getMotorDispatchsByLoc(String locno) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_LOC);
			pstmt.setString(1, locno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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
	public Set<MotorDispatchVO> getMotorDispatchsByProg(String prog) {
		Set<MotorDispatchVO> set = new LinkedHashSet<MotorDispatchVO>();
		MotorDispatchVO mdVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_PROG);
			pstmt.setString(1, prog);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mdVO = new MotorDispatchVO();
				setAttirbute(mdVO, rs); // 拉出來寫成一個方法
				set.add(mdVO); // Store the row in the vector
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
	public RentOrdVO checkDispatchableMotors(String motno) {

		RentOrdVO rentOrdVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_DISPATCHABLE_DATE);

			pstmt.setString(1, motno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 也稱為 Domain objects
				rentOrdVO = new RentOrdVO();
//				rentOrdVO.setMotno(rs.getString("motno"));
				rentOrdVO.getMotorVO().setMotno(rs.getString("motno"));//上線版本!
				
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
		return rentOrdVO;
	}

	//以下為hibernate用
	@Override
	public void insertByHib(MotorDispatchVO mdVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			
			session.beginTransaction();
			session.saveOrUpdate(mdVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.println("insertByHib fail fail");
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void updateByHib(MotorDispatchVO mdVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(mdVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void deleteByHib(String mdno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			MotorDispatchVO mdVO = (MotorDispatchVO) session.get(MotorDispatchVO.class, mdno);
			session.delete(mdVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public MotorDispatchVO findByPkByHib(String mdno) {
		MotorDispatchVO mdVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			mdVO = (MotorDispatchVO) session.get(MotorDispatchVO.class, mdno);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return mdVO;
	}

	@Override
	public List<MotorDispatchVO> getAllByHib() {
		List<MotorDispatchVO> list = null;
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
	public Set<MotorDispListVO> getMdListByMdnoByHib(String mdno) {		
		Set<MotorDispListVO> set = findByPkByHib(mdno).getMotorDispLists();
		return set;
	}
	
	@Override
	public List<MotorDispatchVO> getByLocnoByHib(String locno){
		List<MotorDispatchVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_BY_LOCNO);
			query.setParameter(0, locno);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
	
	@Override
	public void cancelByHib(String mdno){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(CANCEL);
			query.setParameter(0, mdno);
			query.executeUpdate();
			
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}
	
	public static void main(String[] args) {
	
//	MotorDispatchDAO dao = new MotorDispatchDAO();
//	MotorDispatchVO mdVO = new MotorDispatchVO(); 
//	MotorModelVO mmVO = new MotorModelVO(); 
//	Set<MotorDispListVO> set = new HashSet<MotorDispListVO>();
//	
//	MotorDispListVO mdListVO = new MotorDispListVO();
//	int amount = 2;
//	for(int i = 1; i <= amount; i++){
//		mdVO.setLocno("TPE01");
//		mmVO.setModtype("MM101");
//		mdListVO.setMotno(String.valueOf(i));
//	}
//	mdListVO.setMotorDispatchVO(mdVO);
//	mdListVO.setMotorModelVO(mmVO);
//	set.add(mdListVO);
//	
//	mdVO.setMotorDispLists(set);
//	dao.insertByHib(mdVO);
//	
//	
//	
//	List<MotorDispatchVO> list2 = dao.getAllByHib();
//	for (MotorDispatchVO aDept : list2) {
//		System.out.print(aDept.getMdno() + ",");
//		System.out.print(aDept.getLocno() + ",");
//		System.out.print(aDept.getFilldate());
//		System.out.print(aDept.getCloseddate());
//		System.out.print(aDept.getProg());
//		System.out.println("\n-----------------");
//		
//	}
//	
	}

	
}
