package com.immediate_push.model;

import java.util.List;


public interface ImmediatePushDAO_interface {
	public void insert(ImmediatePushVO immediatepushvo);
	public void update(ImmediatePushVO immediatepushvo);
	public void delete(String ipno);
	public ImmediatePushVO findByPrimaryKey(String ipno);
	public List<ImmediatePushVO> getAll();

}
