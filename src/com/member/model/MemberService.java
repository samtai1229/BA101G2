package com.member.model;

import java.sql.Timestamp;
import java.util.List;

import com.rent_ord.model.*;

public class MemberService {

	private MemberDAO_interface dao;
	
	public MemberService(){
		dao = new MemberDAO();
	}
	
	public MemberVO insert(String memname,String sex,Timestamp birth,String mail,String phone,String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license){
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
//		memberVO.setCredate(credate);
		dao.insert(memberVO);
		return memberVO;
	}
	                            //memno, memname, sex, birth, mail, phone, addr, acc, pwd, idcard1, idcard2, license
	public MemberVO update(String memno,String memname,String sex,Timestamp birth,String mail,String phone,String addr,String acc,String pwd,byte[] idcard1,byte[] idcard2,byte[] license,String status,Timestamp credate){
//		 memname = ?, sex = ?, birth = ?, mail = ?, phone = ?, addr = ?,"
//					+ " acc = ? , pwd = ?,idcard1=? ,idcard2=? ,license=? , status = ? where memno = ?"	
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
		memberVO.setStatus(status);
		memberVO.setCredate(credate);
		dao.update(memberVO);
		return memberVO;
	}
	
	public void delete(String memno){
		dao.delete(memno);
	}
	
	public MemberVO getOneMember(String memno){
		return dao.findByPrimaryKey(memno);
	}
	
	public MemberVO getOneMemberByAccAndPwd(String acc,String pwd){
		return dao.findByPrimaryKeyByAccAndPwd(acc,pwd);
	}
	public MemberVO getOneMemberByAcc(String acc){
		return dao.findByAcc(acc);
	}
	public boolean checkAccIsExisted(String acc){
		if(dao.findByAcc(acc)!=null)
		{
			if(dao.findByAcc(acc).getAcc().equals(acc))
				return true;
		}
		return false;
	}
	
	public List<MemberVO> getAll(){
		return dao.getAll();
	}

	public MemberVO getOneMemberById(String memid) {
		
		return dao.findByPrimaryKeyById(memid);
	}
	
	public void updateStatus(String memno, String status){
		dao.updateStatus(memno, status);
	}
	

}
