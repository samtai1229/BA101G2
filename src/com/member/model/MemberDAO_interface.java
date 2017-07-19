package com.member.model;

import java.util.List;

public interface MemberDAO_interface {
	public void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public void delete(String memno);
    public MemberVO findByPrimaryKey(String memno);
    public List<MemberVO> getAll();
	MemberVO findByPrimaryKeyByAccAndPwd(String acc, String pwd);
	public MemberVO findByAcc(String acc);
	MemberVO findByPrimaryKeyById(String memid);
	public void updateStatus(String memno, String status);

}
