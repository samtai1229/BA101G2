package com.news.model;

import java.util.List;

public interface NewsDAO_interface {
	public void insert(NewsVO newsvo);
	public void update(NewsVO Newsvo);
	public void delete(String newsno);
	public NewsVO findByPrimaryKey(String newsno);
	public List<NewsVO> getAll();
	public List<NewsVO> getAllnormal();

}
