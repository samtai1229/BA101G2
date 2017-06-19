package com.member.model;

import java.sql.Date;
import java.sql.Timestamp;

public class MemberVO implements java.io.Serializable {
	private String memno;
	private String memname;
	private String sex;
	private Timestamp birth;
//use birthshow get birth's value;
	private String showbirth;
	private String mail;
	private String phone;
	private String addr;
	private String acc;
	private String pwd;
	private byte[] idcard1;
	private byte[] idcard2;
	private byte[] license;
	private Timestamp credate;
//use credateshow get credate's value;
	private String showcredate;
	private String status;

	public MemberVO() {
		super();
	}

	public String getMemno() {
		return memno;
	}

	public void setMemno(String memno) {
		this.memno = memno;
	}

	public String getMemname() {
		return memname;
	}

	public void setMemname(String memname) {
		this.memname = memname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
//----showbirth
	public String getShowbirth() {
		return showbirth;
	}

	public void setShowbirth(String showbirth) {
		this.showbirth = showbirth;
	}
//----showbirth
	public Timestamp getBirth() {
		return birth;
	}

	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public byte[] getIdcard1() {
		return idcard1;
	}

	public void setIdcard1(byte[] idcard1) {
		this.idcard1 = idcard1;
	}

	public byte[] getIdcard2() {
		return idcard2;
	}

	public void setIdcard2(byte[] idcard2) {
		this.idcard2 = idcard2;
	}

	public byte[] getLicense() {
		return license;
	}

	public void setLicense(byte[] license) {
		this.license = license;
	}

	public Timestamp getCredate() {
		return credate;
	}

	public void setCredate(Timestamp credate) {
		this.credate = credate;
	}

//-----showcredate
	public String getShowcredate() {
		return showcredate;
	}
	
	public void setShowcredate(String showcredate) {
		this.showcredate = showcredate;
	}
//-----showcredate	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}