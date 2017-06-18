package com.spots.model;

import java.util.List;

public class SpotService {
	
	private SpotsDAO_interface dao ;
   public SpotService()
	{
		dao = new SpotsDAO();
	}
	
	public SpotsVO addOneSpot(String spno, String spname, String locno, float splong,float splat )
	{
		SpotsVO spot = new SpotsVO();
		spot.setLocno(locno);
		spot.setSplat(splat);
		spot.setSplong(splong);
		spot.setSpname(spname);
		spot.setSpno(spno);
	  	dao.insert(spot);
	  	return spot;
	}
	
	public SpotsVO updateOneSpot(String spno, String spname, String locno, float splong,float splat )
	{
		SpotsVO spot = new SpotsVO();
		spot.setLocno(locno);
		spot.setSplat(splat);
		spot.setSplong(splong);
		spot.setSpname(spname);
		spot.setSpno(spno);
	  	dao.update(spot);
	  	return spot;
	}
	
	public void deleteOneSpot(String spno)
	{
		dao.delete(spno);
	}
	
	public List<SpotsVO> getAll()
	{
		return dao.getAll();
	}
	
	public SpotsVO findByPK(String spno)
	{
		return dao.findByPrimaryKey(spno);
	}
	

}
