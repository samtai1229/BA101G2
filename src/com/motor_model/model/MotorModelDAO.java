package com.motor_model.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.Session;

import com.motor.model.MotorVO;

import hibernate.util.HibernateUtil;

public class MotorModelDAO implements MotorModelDAO_interface {
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

	private static final String INSERT_STMT = "INSERT INTO MOTOR_MODEL"
			+ " (modtype, brand, displacement, name, renprice, saleprice, motpic, intro"
			+ ") VALUES ('MM'||LPAD(TO_CHAR(modtype_seq.NEXTVAL), 3,'0'), ?, ?, ?, ?, ?,?,?)";

	private static final String UPDATE = "UPDATE MOTOR_MODEL set brand=?,"
			+ " displacement=?, name=?, renprice=?, saleprice=?, motpic=?, intro=? where modtype = ?";

	private static final String DELETE = "DELETE FROM MOTOR_MODEL where modtype = ?";

	private static final String GET_ONE = "SELECT modtype, brand, displacement,"
			+ "  name, renprice, saleprice, motpic, intro FROM MOTOR_MODEL where modtype = ?";

	private static final String GET_ALL = "SELECT modtype, brand, displacement,"
			+ "  name, renprice, saleprice, motpic, intro FROM MOTOR_MODEL";
	
	private static final String FUZZY_SEARCH = 
			"SELECT * FROM MOTOR_MODEL where MODTYPE LIKE ? or BRAND LIKE ? or DISPLACEMENT LIKE ? or NAME LIKE ? or RENPRICE LIKE ? or SALEPRICE LIKE ? ORDER BY MODTYPE";

	//以下為Hibernate用
		private static final String GET_ALL_STMT = "from MotorModelVO order by MODTYPE";
		private static final String FUZZY_SEARCH_BY_HIBERNATE ="FROM MotorModelVO where MOTNO LIKE ? or MODTYPE LIKE ? or PLATENO LIKE ? or ENGNO LIKE ? or MANUDATE LIKE ? or MILE LIKE ? or LOCNO LIKE ? or STATUS LIKE ? or NOTE LIKE ? or NAME LIKE ? or DISPLACEMENT LIKE ? or RENPRICE LIKE ? or SALEPRICE LIKE ? or BRAND LIKE ? ORDER BY MOTNO"; 
//				"SELECT m.motno, m.modtype, d.name, d.displacement, d.renprice, d.brand m.plateno, m.engno, m.manudate, m.mile, m.locno, d.saleprice,m.status, m.note FROM MOTOR as m join motor_model as d ON m.modtype = d.modtype where m.MOTNO LIKE ? or m.MODTYPE LIKE ? or m.PLATENO LIKE ? or m.ENGNO LIKE ? or m.MANUDATE LIKE ? or m.MILE LIKE ? or m.LOCNO LIKE ? or m.STATUS LIKE ? or m.NOTE LIKE ? or d.NAME LIKE ? or d.DISPLACEMENT LIKE ? or d.RENPRICE LIKE ? or d.SALEPRICE LIKE ? or d.BRAND LIKE ? ORDER BY m.MOTNO";
		

	@Override
	public void insert(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			pstmt.setBytes(6, mmVO.getMotpic());
			pstmt.setString(7, mmVO.getIntro());


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
	public void update(MotorModelVO mmVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, mmVO.getBrand());
			pstmt.setInt(2, mmVO.getDisplacement());
			pstmt.setString(3, mmVO.getName());
			pstmt.setInt(4, mmVO.getRenprice());
			pstmt.setInt(5, mmVO.getSaleprice());
			pstmt.setBytes(6, mmVO.getMotpic());
			pstmt.setString(7, mmVO.getIntro());
			pstmt.setString(8, mmVO.getModtype());


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
	public void delete(String modtype) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstmt.executeUpdate()之前
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, modtype);
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
	public MotorModelVO findByPrimaryKey(String modtype) {

		MotorModelVO mmVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			// modtype, brand, displacement, name, renprice, saleprice, motpic

			pstmt.setString(1, modtype);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs);
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
		return mmVO;
	}

	@Override
	public List<MotorModelVO> getAll() {
		List<MotorModelVO> list = new ArrayList<MotorModelVO>();
		MotorModelVO mmVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mmVO = new MotorModelVO();
				setAttribute(mmVO, rs); // 拉出來寫成一個方法
				list.add(mmVO); // Store the row in the list

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
	public List<MotorModelVO> fuzzyGetAll(String fuzzyValue) {
		
		List<MotorModelVO> list = new ArrayList<MotorModelVO>();
		MotorModelVO mmVO = null;
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
			rs = pstmt.executeQuery();
			
System.out.println("mmDAO: "+"'%" + fuzzyValue + "%'");		
				
			while (rs.next()) {
				mmVO = new MotorModelVO();
				mmVO.setModtype(rs.getString("modtype"));
				mmVO.setBrand(rs.getString("brand"));
				mmVO.setDisplacement(rs.getInt("displacement"));
				mmVO.setName(rs.getString("name"));						
				mmVO.setRenprice(rs.getInt("renprice"));			
				mmVO.setSaleprice(rs.getInt("saleprice"));
				list.add(mmVO); 
			}

		} catch (SQLException se) {
			throw new RuntimeException("mmDAO : A database error occured. " + se.getMessage());
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
System.out.println("mmDAOlist: " + list);
		return list;
	}

	private void setAttribute(MotorModelVO mmVO, ResultSet rs) {
		try {
			mmVO.setModtype(rs.getString("modtype"));
			mmVO.setBrand(rs.getString("brand"));
			mmVO.setDisplacement(rs.getInt("displacement"));
			mmVO.setName(rs.getString("name"));
			mmVO.setRenprice(rs.getInt("renprice"));
			mmVO.setSaleprice(rs.getInt("saleprice"));
			mmVO.setMotpic(rs.getBytes("motpic"));
			mmVO.setIntro(rs.getString("intro"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void readPicture(byte[] motpic) {
		try {
			// 不給放在根目錄，一定要有資料夾???
			FileOutputStream fos = new FileOutputStream("C://temp//0001.gif");
			fos.write(motpic);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 使用byte[]方式
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
	
	//以下為Hibernate用
	@Override
	public List<MotorModelVO> fuzzySearchByHib(String fuzzyValue) {
		List<MotorModelVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();

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
			query.setParameter(13, "%"+fuzzyValue+"%");
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<MotorModelVO> getAllByHib() {
		List<MotorModelVO> list = null;
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

}
