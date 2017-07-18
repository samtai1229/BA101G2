package com.spots.model;

import java.io.Serializable;

public class SpotsVO implements Serializable {

	private String spno;
	private String locno;
	private Float splong;
	private Float splat;
	private String spname;

	public SpotsVO(){}

	public SpotsVO(String spots_no, String loc_no, Float spots_long, Float spots_lat, String spots_name){
		this.spno = spots_no;
		this.locno = loc_no;
		this.splong = spots_long;
		this.splat = spots_lat;
		this.spname = spots_name;
	}

	public String getSpno() {
		return spno;
	}

	public void setSpno(String spots_no) {
		this.spno = spots_no;
	}

	public String getLocno() {
		return locno;
	}

	public void setLocno(String loc_no) {
		this.locno = loc_no;
	}

	public Float getSplong() {
		return splong;
	}

	public void setSplong(Float spots_long) {
		this.splong = spots_long;
	}

	public Float getSplat() {
		return splat;
	}

	public void setSplat(Float spots_lat) {
		this.splat = spots_lat;
	}

	public String getSpname() {
		return spname;
	}

	public void setSpname(String spots_name) {
		this.spname = spots_name;
	}

}