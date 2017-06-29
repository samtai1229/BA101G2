package com.news.model;

import java.sql.Timestamp;

public class NewsVO implements java.io.Serializable{
	private String newsno;
	private String admno;
	private Timestamp newsdate;
	private String cont;
	private byte[] pic;
	private String title;
	private String status;

	public NewsVO() {
		super();
	}
	
	public String getNewsno() {
		return newsno;
	}
	public void setNewsno(String newsno) {
		this.newsno = newsno;
	}
	public String getAdmno() {
		return admno;
	}
	public void setAdmno(String admno) {
		this.admno = admno;
	}
	public Timestamp getNewsdate() {
		return newsdate;
	}
	public void setNewsdate(Timestamp newsdate) {
		this.newsdate = newsdate;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public byte[] getPic() {
		return pic;
	}

	public void setPic(byte[] pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
