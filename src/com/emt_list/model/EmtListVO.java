package com.emt_list.model;

public class EmtListVO implements java.io.Serializable{
	private static final long serialVersionUID = 8571284603115208145L;
	private String rentno;
	private String emtno;
	
	public EmtListVO() {
		super();
	}

	public String getRentno() {
		return rentno;
	}

	public void setRentno(String rentno) {
		this.rentno = rentno;
	}

	public String getEmtno() {
		return emtno;
	}

	public void setEmtno(String emtno) {
		this.emtno = emtno;
	}
}
