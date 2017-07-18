package com.mes_board.model;

import java.sql.Timestamp;

public class MesBoardVO implements java.io.Serializable{
	private String mesno;
	private String memno;
	private Timestamp mesdate;
	private String cont;
	private byte[] pic;
	private String status;
	public MesBoardVO() {
		super();
	}
	public String getMesno() {
		return mesno;
	}
	public void setMesno(String mesno) {
		this.mesno = mesno;
	}
	public String getMemno() {
		return memno;
	}
	public void setMemno(String memno) {
		this.memno = memno;
	}
	public Timestamp getMesdate() {
		return mesdate;
	}
	public void setTimestamp(Timestamp mesdate) {
		this.mesdate = mesdate;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
