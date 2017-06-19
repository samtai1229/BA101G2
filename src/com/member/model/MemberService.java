package com.member.model;

import java.sql.Timestamp;
import java.util.List;

public class MemberService {

	private MemberDAO_interface dao;
	
	public MemberService(){
		dao = new MemberDAO();
	}
	
	public MemberVO insert(String memname,String sex,Timestamp birth,String mail,String phone,String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,Timestamp credate){
		MemberVO memberVO = new MemberVO();

//		memberVO.setMemno(memno);
		memberVO.setMemname(memname);
		memberVO.setSex(sex);
		memberVO.setBirth(birth);
		memberVO.setMail(mail);
		memberVO.setPhone(phone);
		memberVO.setAddr(addr);
		memberVO.setAcc(acc);
		memberVO.setPwd(pwd);
		memberVO.setIdcard1(idcard1);
		memberVO.setIdcard2(idcard2);
		memberVO.setLicense(license);
		memberVO.setCredate(credate);
		return memberVO;
	}
	
	public MemberVO update(String memno,String memname,String sex,Timestamp birth,String mail,String phone,String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,Timestamp credate){
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemno(memno);
		memberVO.setMemname(memname);
		memberVO.setSex(sex);
		memberVO.setBirth(birth);
		memberVO.setMail(mail);
		memberVO.setPhone(phone);
		memberVO.setAddr(addr);
		memberVO.setAcc(acc);
		memberVO.setPwd(pwd);
		memberVO.setIdcard1(idcard1);
		memberVO.setIdcard2(idcard2);
		memberVO.setLicense(license);
		memberVO.setCredate(credate);
		return memberVO;
	}
	
	public void delete(String memno){
		dao.delete(memno);
	}
	
	public MemberVO getOneMember(String memno){
		return dao.findByPrimaryKey(memno);
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}
}
