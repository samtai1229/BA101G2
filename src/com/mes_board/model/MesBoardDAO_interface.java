package com.mes_board.model;

import java.util.List;


public interface MesBoardDAO_interface {
	public void insert(MesBoardVO mesboardvo);
	public void update(MesBoardVO mesboardvo);
	public void delete(String mesno);
	public MesBoardVO findByPrimaryKey(String mesno);
	public List<MesBoardVO> getAll();

}
