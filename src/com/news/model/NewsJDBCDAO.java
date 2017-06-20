package com.news.model;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.adminis.model.AdminisVO;
import com.mes_board.model.MesBoardVO;

public class NewsJDBCDAO implements NewsDAO_interface{

	
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "servlet";
		String passwd = "123456";
		
		private static final String INSERT_STMT = "INSERT INTO NEWS(newsno,admno,cont,pic,title,status) VALUES('N'||lpad(to_char(newsno_seq.NEXTVAL),3,'0'),?,?, ?, ?, ?)";
		private static final String GET_ALL_STMT ="SELECT * FROM news order by newsno";
		private static final String GET_ONE_STMT ="SELECT * FROM news where newsno = ?";
		private static final String DELETE ="DELETE FROM news where newsno = ?";
		private static final String UPDATE ="UPDATE news set admno=?, cont=?, pic=?, title=?, status=? where newsno = ?";


	@Override
	public void insert(NewsVO newsvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, newsvo.getAdmno());
			pstmt.setString(2, newsvo.getCont());
			pstmt.setBytes(3, newsvo.getPic());
			pstmt.setString(4, newsvo.getTitle());
			pstmt.setString(5, newsvo.getStatus());
			
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void update(NewsVO Newsvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, Newsvo.getAdmno());
			pstmt.setString(2, Newsvo.getCont());
			pstmt.setBytes(3, Newsvo.getPic());
			pstmt.setString(4, Newsvo.getTitle());
			pstmt.setString(5, Newsvo.getStatus());
			pstmt.setString(6, Newsvo.getNewsno());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void delete(String newsno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, newsno);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public NewsVO findByPrimaryKey(String newsno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO newsVO = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, newsno);
			rs = pstmt.executeQuery();
			rs.next();
			newsVO = new NewsVO();
			newsVO.setNewsno(rs.getString("newsno"));
			newsVO.setAdmno(rs.getString("admno"));
			newsVO.setDate(rs.getTimestamp("date"));
			newsVO.setCont(rs.getString("cont"));
			newsVO.setPic(rs.getBytes("pic"));
			newsVO.setTitle(rs.getString("title"));
			newsVO.setStatus(rs.getString("status"));
			

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO =null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getString("newsno"));
				newsVO.setAdmno(rs.getString("admno"));
				newsVO.setDate(rs.getTimestamp("date"));
				newsVO.setCont(rs.getString("cont"));
				newsVO.setPic(rs.getBytes("pic"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setStatus(rs.getString("status"));
				//newsVO.setShowdate(new SimpleDateFormat("yyyy/MM/dd").format(rs.getTimestamp("date")));
				list.add(newsVO);}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
public static void main(String[] args) throws IOException {
//		
		NewsJDBCDAO news = new NewsJDBCDAO();
		
//		NewsVO newsvo = new NewsVO();
//		newsvo.setAdmno("A000008");
//		newsvo.setCont("陽岱鋼效力於日本職棒東京讀賣巨人隊，今天在東京巨蛋主場將迎戰軟銀鷹隊，他在賽前練習後接受來自台灣的媒體聯訪時，作以上表示");
//		newsvo.setPic(null);
//		newsvo.setTitle("ball");
//		newsvo.setStatus("normal");
//		news.insert(newsvo);
//		newsvo.setAdmno("A000008");
//		newsvo.setCont("陽岱鋼效力於日本職棒東京讀賣巨人隊，今天在東京巨蛋主場將迎戰軟銀鷹隊，他在賽前練習後接受來自台灣的媒體聯訪時，作以上表示");
//		newsvo.setPic(null);
//		newsvo.setTitle("ball");
//		newsvo.setStatus("normal");
//		news.insert(newsvo);
//		newsvo.setAdmno("A000008");
//		newsvo.setCont("陽岱鋼效力於日本職棒東京讀賣巨人隊，今天在東京巨蛋主場將迎戰軟銀鷹隊，他在賽前練習後接受來自台灣的媒體聯訪時，作以上表示");
//		newsvo.setPic(null);
//		newsvo.setTitle("ball");
//		newsvo.setStatus("normal");
//		System.out.println("ok");
//		news.insert(newsvo);
//		
//		NewsVO newsvo1 = new NewsVO();
//		newsvo1.setNewsno("N000001");
//		newsvo1.setAdmno("A000008");
//		newsvo1.setCont("陽岱鋼效力於日本職棒東京讀賣巨人隊");
//		newsvo1.setPic(null);
//		newsvo1.setTitle("ball");
//		newsvo1.setStatus("normal");
//		news.update(newsvo1);
//		System.out.println("ok");
		
//		news.delete("N000002");
//		System.out.println("ok");
		
		NewsVO newsvo2 = news.findByPrimaryKey("N000003");
		System.out.println(newsvo2.getNewsno() + ",");
		System.out.println(newsvo2.getAdmno() + ",");
		System.out.println(newsvo2.getDate() + ",");
		System.out.println(newsvo2.getCont() + ",");
		System.out.println(newsvo2.getPic() + ",");
		System.out.println(newsvo2.getTitle() + ",");
		System.out.println(newsvo2.getStatus() + ",");
		System.out.println("OK");
		System.out.println("====================================================");
		
		List<NewsVO> list = news.getAll();
		for (NewsVO all : list) {
			printMethod(all);
		}

	}

	private static void printMethod(NewsVO newsvo2) {

		System.out.println(newsvo2.getNewsno() + ",");
		System.out.println(newsvo2.getAdmno() + ",");
		System.out.println(newsvo2.getDate() + ",");
		System.out.println(newsvo2.getCont() + ",");
		System.out.println(newsvo2.getPic() + ",");
		System.out.println(newsvo2.getTitle() + ",");
		System.out.println(newsvo2.getStatus() + ",");
		System.out.println("OK");
}
		
//for(int i=1;i<7;i++){
//			newsvo.setPic("MMH00"+i);
//			newsvo.setAdmno("A000008");
//			newsvo.setDate(new Timestamp(System.currentTimeMillis()));
//			newsvo.setCont("�y���ӳ�");
//			newsvo.setTitle("ball");
//			newsvo.setStatus("normal");
//			System.out.println(i);
//			byte[] pic;
//			try {
//				pic = getPictureByteArray("WebContent/WEB-INF/images/"+i+".png");
//				newsvo.setPic(pic);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			news.insert(newsvo);
//			
//		}	
//
//
//	}
//		
	

//public static byte[] getPictureByteArray(String path) throws IOException {
//	File file = new File(path);
//	FileInputStream fis = new FileInputStream(file);
//	ByteArrayOutputStream baos = new ByteArrayOutputStream();
//	byte[] buffer = new byte[8192];
//	int i;
//	while ((i = fis.read(buffer)) != -1) {
//		baos.write(buffer, 0, i);
//	}
//	baos.close();
//	fis.close();
//
//	return baos.toByteArray();
}
