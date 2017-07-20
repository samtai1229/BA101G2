package com.emt_cate.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.equipment.model.EquipmentVO;

import hibernate.util.HibernateUtil;

public class EmtCateDAO implements EmtCateDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO EMT_CATE (ECNO, TYPE, PIC, PRICE) VALUES ('EC'||lpad(to_char(ecno_seq.NEXTVAL),2,'0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMT_CATE order by ECNO";
	private static final String GET_ONE_STMT = "SELECT * FROM EMT_CATE where ECNO = ?";
	private static final String GET_EMTS_BY_ECNO_STMT = "SELECT * FROM EQUIPMENT where ECNO = ? order by emtno";

	private static final String DELETE = "DELETE FROM EMT_CATE where ECNO = ?";
	private static final String UPDATE = "UPDATE EMT_CATE set type=?, pic=?, price=? where ECNO = ?";

	// 以下為Hibernate用
	private static final String GET_ALL_BY_HIBERNATE = "from EmtCateVO order by ecno";

	@Override
	public void insert(EmtCateVO emtCateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emtCateVO.getType());
			pstmt.setBytes(2, emtCateVO.getPic());
			pstmt.setInt(3, emtCateVO.getPrice());

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
	public void update(EmtCateVO emtCateVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emtCateVO.getType());
			pstmt.setBytes(2, emtCateVO.getPic());
			pstmt.setInt(3, emtCateVO.getPrice());
			pstmt.setString(4, emtCateVO.getEcno());

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
	public void delete(String ecno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ecno);

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
	public EmtCateVO findByPrimaryKey(String ecno) {

		EmtCateVO emtCateVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ecno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtCateVO 也稱為 Domain objects
				emtCateVO = new EmtCateVO();
				emtCateVO.setEcno(rs.getString("ecno"));
				emtCateVO.setType(rs.getString("type"));
				emtCateVO.setPic(rs.getBytes("pic"));
				emtCateVO.setPrice(rs.getInt("price"));
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
		return emtCateVO;
	}

	@Override
	public List<EmtCateVO> getAll() {
		List<EmtCateVO> list = new ArrayList<EmtCateVO>();
		EmtCateVO emtCateVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emtCateVO 也稱為 Domain objects
				emtCateVO = new EmtCateVO();
				emtCateVO.setEcno(rs.getString("ecno"));
				emtCateVO.setType(rs.getString("type"));
				emtCateVO.setPrice(rs.getInt("price"));
				list.add(emtCateVO); // Store the row in the list
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

	@Override
	public Set<EquipmentVO> getEmtsByEcno(String ecno) {
		Set<EquipmentVO> set = new LinkedHashSet<EquipmentVO>();
		EquipmentVO eqptVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_EMTS_BY_ECNO_STMT);
			pstmt.setString(1, ecno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eqptVO = new EquipmentVO();
				eqptVO.setEmtno(rs.getString("emtno"));
				eqptVO.setEcno(rs.getString("ecno"));
				eqptVO.setLocno(rs.getString("locno"));
				eqptVO.setPurchdate(rs.getTimestamp("purchdate"));
				eqptVO.setStatus(rs.getString("status"));
				eqptVO.setNote(rs.getString("note"));
				set.add(eqptVO); // Store the row in the vector
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

	// 以下為Hibernate
	@Override
	public List<EmtCateVO> getAllByHib() {
		List<EmtCateVO> list = null;
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
}