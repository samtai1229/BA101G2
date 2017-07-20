package com.emt_dispatch.model;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.emt_disp_list.model.EmtDispListVO;
import com.emt_dispatch.model.EmtDispatchVO;

import hibernate.util.HibernateUtil;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class EmtDispatchDAO implements EmtDispatchDAO_interface{
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
		private static final String INSERT_STMT = 
			"INSERT INTO EMT_DISPATCH (EDNO, LOCNO, DEMANDDATE, CLOSEDDATE, PROG) VALUES ('ED'||lpad(to_char(edno_seq.NEXTVAL),6,'0'), ?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT * FROM EMT_DISPATCH order by EDNO";
		private static final String GET_ONE_STMT = 
			"SELECT * FROM EMT_DISPATCH where EDNO = ?";
		private static final String DELETE = 
			"DELETE FROM EMT_DISPATCH where EDNO = ?";
		private static final String UPDATE = 
			"UPDATE EMT_DISPATCH set LOCNO=?, DEMANDDATE=?, CLOSEDDATE=?, PROG=? where EDNO = ?";
		//以下為hibernate用
		private static final String GET_ALL_STMT_BY_HIBERNATE = "from EmtDispatchVO order by edno desc";
		private static final String GET_BY_LOCNO = "from EmtDispatchVO where locno = ? order by edno";
		private static final String CANCEL = "update EmtDispatchVO set prog = 'canceled', closeddate = systimestamp where edno = ?";
		
		@Override
		public void insert(EmtDispatchVO emtDispatchVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, emtDispatchVO.getLocno());
				pstmt.setTimestamp(2, emtDispatchVO.getDemanddate());
				pstmt.setTimestamp(3, emtDispatchVO.getCloseddate());
				pstmt.setString(4, emtDispatchVO.getProg());
				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public void update(EmtDispatchVO emtDispatchVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, emtDispatchVO.getLocno());
				pstmt.setTimestamp(2, emtDispatchVO.getDemanddate());
				pstmt.setTimestamp(3, emtDispatchVO.getCloseddate());
				pstmt.setString(4, emtDispatchVO.getProg());
				pstmt.setString(5, emtDispatchVO.getEdno());
				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public void delete(String edno) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, edno);

				pstmt.executeUpdate();

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
		public EmtDispatchVO findByPrimaryKey(String edno) {

			EmtDispatchVO emtDispatchVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, edno);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// emtDispatchVO 也稱為 Domain objects
					emtDispatchVO = new EmtDispatchVO();
					emtDispatchVO.setEdno(rs.getString("edno"));
					emtDispatchVO.setLocno(rs.getString("locno"));
					emtDispatchVO.setDemanddate(rs.getTimestamp("demanddate"));
					emtDispatchVO.setCloseddate(rs.getTimestamp("closeddate"));
					emtDispatchVO.setProg(rs.getString("prog"));
				}

				// Handle any SQL errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
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
			return emtDispatchVO;
		}

		@Override
		public List<EmtDispatchVO> getAll() {
			List<EmtDispatchVO> list = new ArrayList<EmtDispatchVO>();
			EmtDispatchVO emtDispatchVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// emtDispatchVO 也稱為 Domain objects
					emtDispatchVO = new EmtDispatchVO();
					emtDispatchVO.setEdno(rs.getString("edno"));
					emtDispatchVO.setLocno(rs.getString("locno"));
					emtDispatchVO.setDemanddate(rs.getTimestamp("demanddate"));
					emtDispatchVO.setCloseddate(rs.getTimestamp("closeddate"));
					emtDispatchVO.setProg(rs.getString("prog"));
					list.add(emtDispatchVO); // Store the row in the list
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
		public void insertByHib(EmtDispatchVO edVO) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(edVO);
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				System.out.println("insertByHib fail");
				session.getTransaction().rollback();
				throw ex;
			}
		}

		@Override
		public void updateByHib(EmtDispatchVO edVO) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				session.saveOrUpdate(edVO);
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
				EmtDispatchVO edVO = (EmtDispatchVO) session.get(EmtDispatchVO.class, edno);
				session.delete(edVO);
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		}

		@Override
		public EmtDispatchVO findByPkByHib(String edno) {
			EmtDispatchVO edVO = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				edVO = (EmtDispatchVO) session.get(EmtDispatchVO.class, edno);
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return edVO;
		}

		@Override
		public List<EmtDispatchVO> getAllByHib() {
			List<EmtDispatchVO> list = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(GET_ALL_STMT_BY_HIBERNATE);
				list = query.list();
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
			return list;
		}

		@Override
		public Set<EmtDispListVO> getEdListByEdnoByHib(String edno) {		
			Set<EmtDispListVO> set = findByPkByHib(edno).getEmtDispLists();
			return set;
		}
		
		@Override
		public List<EmtDispatchVO> getByLocnoByHib(String locno){
			List<EmtDispatchVO> list = null;
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
		public void cancelByHib(String edno){
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				Query query = session.createQuery(CANCEL);
				query.setParameter(0, edno);
				query.executeUpdate();
				
				session.getTransaction().commit();
			} catch (RuntimeException ex) {
				session.getTransaction().rollback();
				throw ex;
			}
		}
		
		
		public static void main(String[] args){


//			EmtDispatchDAO dao = new EmtDispatchDAO();
//			EmtDispatchVO edVO = new EmtDispatchVO(); 
//			EmtCateVO mmVO = new EmtCateVO(); 
//			Set<EmtDispListVO> set = new HashSet<EmtDispListVO>();
		//	
//			EmtDispListVO mdListVO = new EmtDispListVO();
//			int amount = 2;
//			for(int i = 1; i <= amount; i++){
//				edVO.setLocno("TPE01");
//				mmVO.setModtype("MM101");
//				mdListVO.setMotno(String.valueOf(i));
//			}
//			mdListVO.setEmtDispatchVO(edVO);
//			mdListVO.setEmtCateVO(mmVO);
//			set.add(mdListVO);
		//	
//			edVO.setEmtDispLists(set);
//			dao.insertByHib(edVO);
		//	
		//	
		//	
//			List<EmtDispatchVO> list2 = dao.getAllByHib();
//			for (EmtDispatchVO aDept : list2) {
//				System.out.print(aDept.getMdno() + ",");
//				System.out.print(aDept.getLocno() + ",");
//				System.out.print(aDept.getFilldate());
//				System.out.print(aDept.getCloseddate());
//				System.out.print(aDept.getProg());
//				System.out.println("\n-----------------");
//				
//			}
		//	
		}
	}