package com.location.model;

import java.util.List;

import com.member.model.MemberVO;

public class LocationService {

	private LocationDAO_interface dao;
	
	public LocationService(){
		dao= new LocationDAO();
	}
	public LocationVO insert(String locno, String locname, String tel, String addr,byte[] pic,float lon,float lat/*,String status*/){
		LocationVO locationVO = new LocationVO();
		locationVO.setLocno(locno);
		locationVO.setLocname(locname);
		locationVO.setTel(tel);
		locationVO.setAddr(addr);
		locationVO.setPic(pic);
		locationVO.setLon(lon);
		locationVO.setLat(lat);
//		locationVO.setStatus(status);
		dao.insert(locationVO);
		return locationVO;
	}
	
	public LocationVO update(String locno,String locname,String tel,String addr,byte[] pic,float lon,float lat,String status){
		LocationVO locationVO = new LocationVO();
		locationVO.setLocno(locno);
		locationVO.setLocname(locname);
		locationVO.setTel(tel);
		locationVO.setAddr(addr);
		locationVO.setPic(pic);
		locationVO.setLon(lon);
		locationVO.setLat(lat);
		locationVO.setStatus(status);
		dao.update(locationVO);
		return locationVO;
	}
	
	public void delete(String locno){
		dao.delete(locno);
	}
	
	public LocationVO getOneLocation(String locno){
		return dao.findByPrimaryKey(locno);
	}
	
	public List<LocationVO> getAll(){
		return dao.getAll();
	}
	
	public List<LocationVO> getAllStatusOpen(){
		return dao.getAllStatusOpen();
	}
}
