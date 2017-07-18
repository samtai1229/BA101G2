package com.emt_disp_list.model;


public class EmtDispListVO implements java.io.Serializable{
	private static final long serialVersionUID = 5391361785448194239L;
	private String edno;
	private String emtno;
	
	public EmtDispListVO() {
		super();
	}

	public String getEdno() {
		return edno;
	}

	public void setEdno(String edno) {
		this.edno = edno;
	}

	public String getEmtno() {
		return emtno;
	}

	public void setEmtno(String emtno) {
		this.emtno = emtno;
	}
	
	
}
