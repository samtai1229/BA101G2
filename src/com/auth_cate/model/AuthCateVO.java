package com.auth_cate.model;

public class AuthCateVO implements java.io.Serializable{
	private String authno;
	private String descr;
	protected AuthCateVO() {
		super();
	}
	public String getAuthno() {
		return authno;
	}
	public void setAuthno(String authno) {
		this.authno = authno;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
	

}
