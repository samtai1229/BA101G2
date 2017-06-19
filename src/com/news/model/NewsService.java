package com.news.model;

import java.sql.Timestamp;
import java.util.List;

public class NewsService {
	
	private NewsDAO_interface dao;
	
	public NewsService() {
		dao = new NewsDAO();
	}
	
	public NewsVO addEmp(String admno, Timestamp date, String cont,byte[] pic, String title, String status) {

		NewsVO newsVO = new NewsVO();

		newsVO.setAdmno(admno);
		newsVO.setDate(date);
		newsVO.setCont(cont);
		newsVO.setPic(pic);
		newsVO.setTitle(title);
		newsVO.setStatus(status);
		dao.insert(newsVO);

		return newsVO;
	}
	
	public NewsVO updateNews(String newsno,String admno, Timestamp date, String cont,byte[] pic, String title, String status) {

		NewsVO newsVO = new NewsVO();
		newsVO.setNewsno(newsno);
		newsVO.setAdmno(admno);
		newsVO.setDate(date);
		newsVO.setCont(cont);
		newsVO.setPic(pic);
		newsVO.setTitle(title);
		newsVO.setStatus(status);
		dao.update(newsVO);

		return newsVO;
	}
	
	public void deleteNews(String newsno) {
		dao.delete(newsno);
	}

	public NewsVO getOneEmp(String newsno) {
		return dao.findByPrimaryKey(newsno);
	}

	public List<NewsVO> getAll() {
		return dao.getAll();
	}
}

