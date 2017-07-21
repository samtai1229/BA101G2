package com.rent_ord.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class RentOrdVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String rentno;
	private String memno;
	private String slocno;
	private String rlocno;
	private Integer milstart;
	private Integer milend;
	private Timestamp filldate;
	private Timestamp startdate;
	private Timestamp enddate;
	private Timestamp returndate;
	private Integer fine;
	private Integer total;
	private String rank;
	private String status;
	private String note;
	private MotorForRentOrdVO motorVO;
	private Set<EmtListForRentOrdVO> ro_emtlists = new HashSet<EmtListForRentOrdVO>();
	

	public RentOrdVO() {
		super();
	}


	public String getRentno() {
		return rentno;
	}


	public void setRentno(String rentno) {
		this.rentno = rentno;
	}


	public String getMemno() {
		return memno;
	}


	public void setMemno(String memno) {
		this.memno = memno;
	}


	public String getSlocno() {
		return slocno;
	}


	public void setSlocno(String slocno) {
		this.slocno = slocno;
	}


	public String getRlocno() {
		return rlocno;
	}


	public void setRlocno(String rlocno) {
		this.rlocno = rlocno;
	}


	public Integer getMilstart() {
		return milstart;
	}


	public void setMilstart(Integer milstart) {
		this.milstart = milstart;
	}


	public Integer getMilend() {
		return milend;
	}


	public void setMilend(Integer milend) {
		this.milend = milend;
	}


	public Timestamp getFilldate() {
		return filldate;
	}


	public void setFilldate(Timestamp filldate) {
		this.filldate = filldate;
	}


	public Timestamp getStartdate() {
		return startdate;
	}


	public void setStartdate(Timestamp startdate) {
		this.startdate = startdate;
	}


	public Timestamp getEnddate() {
		return enddate;
	}


	public void setEnddate(Timestamp enddate) {
		this.enddate = enddate;
	}


	public Timestamp getReturndate() {
		return returndate;
	}


	public void setReturndate(Timestamp returndate) {
		this.returndate = returndate;
	}


	public Integer getFine() {
		return fine;
	}


	public void setFine(Integer fine) {
		this.fine = fine;
	}


	public Integer getTotal() {
		return total;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public String getRank() {
		return rank;
	}


	public void setRank(String rank) {
		this.rank = rank;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public MotorForRentOrdVO getMotorVO() {
		return motorVO;
	}


	public void setMotorVO(MotorForRentOrdVO motorVO) {
		this.motorVO = motorVO;
	}


	public Set<EmtListForRentOrdVO> getRo_emtlists() {
		return ro_emtlists;
	}


	public void setRo_emtlists(Set<EmtListForRentOrdVO> ro_emtlists) {
		this.ro_emtlists = ro_emtlists;
	}


	
}
