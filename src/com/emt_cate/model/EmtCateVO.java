package com.emt_cate.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.equipment.model.EquipmentVO;


public class EmtCateVO implements Serializable{
	private static final long serialVersionUID = -7712850071813258442L;
	private String ecno;
	private String type;
	private byte[] pic;
	private Integer price;
	//以下為hibernate用
	private Set<EquipmentVO> emts = new HashSet<EquipmentVO>();
		
		
	public EmtCateVO() {
		super();
	}
	
	public String getEcno() {
		return ecno;
	}
	public void setEcno(String ecno) {
		this.ecno = ecno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getPic() {
		return pic;
	}
	public void setPic(byte[] pic) {
		this.pic = pic;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	public Set<EquipmentVO> getEmts() {
		return emts;
	}

	public void setEmts(Set<EquipmentVO> emts) {
		this.emts = emts;
	}
}
