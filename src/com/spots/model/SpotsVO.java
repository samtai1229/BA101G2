package com.spots.model;

import java.io.Serializable;

public class SpotsVO implements Serializable {

	private String spotsno;
	private String locno;
	private Float spotslong;
	private Float spotslat;
	private String spotsname;

	public SpotsVO(){}

	public SpotsVO(String spots_no, String loc_no, Float spots_long, Float spots_lat, String spots_name){
		this.spotsno = spots_no;
		this.locno = loc_no;
		this.spotslong = spots_long;
		this.spotslat = spots_lat;
		this.spotsname = spots_name;
	}

	public String getSpotsNo() {
		return spotsno;
	}

	public void setSpotsNo(String spots_no) {
		this.spotsno = spots_no;
	}

	public String getLocNo() {
		return locno;
	}

	public void setLocNo(String loc_no) {
		this.locno = loc_no;
	}

	public Float getSpotsLong() {
		return spotslong;
	}

	public void setSpotsLong(Float spots_long) {
		this.spotslong = spots_long;
	}

	public Float getSpotsLat() {
		return spotslat;
	}

	public void setSpotsLat(Float spots_lat) {
		this.spotslat = spots_lat;
	}

	public String getSpotsName() {
		return spotsname;
	}

	public void setSpotsName(String spots_name) {
		this.spotsname = spots_name;
	}

}