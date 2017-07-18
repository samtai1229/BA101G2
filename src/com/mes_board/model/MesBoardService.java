package com.mes_board.model;

import java.sql.Timestamp;
import java.util.List;

public class MesBoardService {
	
	private MesBoardDAO_interface dao;
	
	public MesBoardService() {
		dao = new MesBoardDAO();
	}
	
	public MesBoardVO addMesBoard(String memno, String cont,byte[] pic, String status) {

		MesBoardVO mesboardVO = new MesBoardVO();

		mesboardVO.setMemno(memno);
		mesboardVO.setCont(cont);
		mesboardVO.setPic(pic);
		mesboardVO.setStatus(status);
		dao.insert(mesboardVO);

		return mesboardVO;
	}
	
	public MesBoardVO updateMesBoard(String mesno,String memno,  String cont,byte[] pic, String status) {
System.out.println("SERVICE");
		MesBoardVO mesboardVO = new MesBoardVO();

		mesboardVO.setMesno(mesno);
		System.out.println("mesno"+mesno);
		mesboardVO.setMemno(memno);
		System.out.println("memno"+memno);
		mesboardVO.setCont(cont);
		System.out.println("cont"+cont);
		mesboardVO.setPic(pic);
		System.out.println("pic"+pic);
		mesboardVO.setStatus(status);
		System.out.println("status"+status);
		dao.update(mesboardVO);
System.out.println("SERVVICE mesboardVO"+mesboardVO);
		return mesboardVO;
	}
	public void deleteMesBoard(String mesno) {
		dao.delete(mesno);
	}

	public MesBoardVO getOneMesBoard(String mesno) {
		System.out.println("!!!!!!!!!");
		return dao.findByPrimaryKey(mesno);
	}

	public List<MesBoardVO> getAll() {
		return dao.getAll();
	}
}
