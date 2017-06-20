package com.mes_board.model;

import java.sql.Timestamp;
import java.util.List;

public class MesBoardService {
	
	private MesBoardDAO_interface dao;
	
	public MesBoardService() {
		dao = new MesBoardDAO();
	}
	
	public MesBoardVO addMesBoard(String memno, Timestamp date, String cont,byte[] pic, String status) {

		MesBoardVO mesboardVO = new MesBoardVO();

		mesboardVO.setMemno(memno);
		mesboardVO.setDate(date);
		mesboardVO.setCont(cont);
		mesboardVO.setPic(pic);
		mesboardVO.setStatus(status);
		dao.insert(mesboardVO);

		return mesboardVO;
	}
	
	public MesBoardVO updateMesBoard(String mesno,String memno, Timestamp date, String cont,byte[] pic, String status) {

		MesBoardVO mesboardVO = new MesBoardVO();

		mesboardVO.setMesno(mesno);
		mesboardVO.setMemno(memno);
		mesboardVO.setDate(date);
		mesboardVO.setCont(cont);
		mesboardVO.setPic(pic);
		mesboardVO.setStatus(status);
		dao.update(mesboardVO);

		return mesboardVO;
	}
	public void deleteMesBoard(String mesno) {
		dao.delete(mesno);
	}

	public MesBoardVO getOneMesBoard(String mesno) {
		return dao.findByPrimaryKey(mesno);
	}

	public List<MesBoardVO> getAll() {
		return dao.getAll();
	}
}
