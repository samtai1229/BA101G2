package com.emt_disp_list.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.emt_dispatch.model.EmtDispatchVO;
import com.emt_cate.model.EmtCateVO;

import hibernate.util.HibernateUtil;

import java.io.IOException;
import java.sql.*;

public class EmtDispListDAO implements EmtDispListDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO EMT_DISP_LIST (EDNO, EMTNO) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMT_DISP_LIST order by EDNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EMT_DISP_LIST where EDNO = ?";
	private static final String DELETE = "DELETE FROM EMT_DISP_LIST where EDNO = ? and EMTNO = ?";
	private static final String UPDATE = "UPDATE EMT_DISP_LIST set EDNO = ?, EMTNO = ? where EDNO = ? and EMTNO = ?";

	// 以下hiberante
	private static final String DELETE_BY_HIBERNATE = "delete EmtDispListVO where edno = ?";

	@Override
	public void insert(EmtDispListVO emtDispListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtDispListVO.getEdno());
			pstmt.setString(2, emtDispListVO.getEmtno());

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
	public void update(EmtDispListVO emtDispListVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emtDispListVO.getEdno());
			pstmt.setString(2, emtDispListVO.getEmtno());
			pstmt.setString(3, emtDispListVO.getEdno());
			pstmt.setString(4, emtDispListVO.getEmtno());

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
	public void delete(String edno, String emtno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, edno);
			pstmt.setString(2, emtno);

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
	public EmtDispListVO findByPrimaryKey(String edno, String emtno) {

		EmtDispListVO emtDispListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, edno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtDispListVO 也稱為 Domain objects
				emtDispListVO = new EmtDispListVO();
				emtDispListVO.setEdno(rs.getString("edno"));
				emtDispListVO.setEmtno(rs.getString("emtno"));
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
		return emtDispListVO;
	}

	@Override
	public List<EmtDispListVO> getAll() {
		List<EmtDispListVO> list = new ArrayList<EmtDispListVO>();
		EmtDispListVO emtDispListVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtDispListVO 也稱為 Domain objects
				emtDispListVO = new EmtDispListVO();
				emtDispListVO.setEdno(rs.getString("edno"));
				emtDispListVO.setEmtno(rs.getString("emtno"));
				list.add(emtDispListVO); // Store the row in the list
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

	
	// 以下為Hibernate用

	@Override
	public void insertByHib(EmtDispListVO edListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(edListVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void updateByHib(EmtDispListVO edListVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(edListVO);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	@Override
	public void deleteByHib(String edno) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			Query query = session.createQuery(DELETE);
			query.setParameter(0, edno);
			query.executeUpdate();

			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
	}

	public static void main(String[] args) {

		EmtDispListDAO dao = new EmtDispListDAO();
		EmtDispatchVO edVO = new EmtDispatchVO();
		EmtCateVO ecVO = new EmtCateVO();
		EmtDispListService edlSvc = new EmtDispListService();
		edlSvc.deleteByHib("MD000023");
		System.out.println("GGG");

		// Set<EmtDispListVO> set = new HashSet<EmtDispListVO>();
		//
		// EmtDispListVO edListVO = new EmtDispListVO();
		// int amount = 2;
		// for(int i = 1; i <= amount; i++){
		// edVO.setLocno("TPE01");
		// ecVO.setModtype("MM101");
		// edListVO.setMotno(String.valueOf(i));
		// }
		// edListVO.setEmtDispatchVO(edVO);
		// edListVO.setEmtModelVO(ecVO);
		// set.add(edListVO);
		//
		// edVO.setEmtDispLists(set);
		// dao.insertByHib(edVO);
		//
		//
		//
		// List<EmtDispatchVO> list2 = dao.getAllByHib();
		// for (EmtDispatchVO aDept : list2) {
		// System.out.print(aDept.getMdno() + ",");
		// System.out.print(aDept.getLocno() + ",");
		// System.out.print(aDept.getFilldate());
		// System.out.print(aDept.getCloseddate());
		// System.out.print(aDept.getProg());
		// System.out.println("\n-----------------");
		//
		// }
		//
	}

}
