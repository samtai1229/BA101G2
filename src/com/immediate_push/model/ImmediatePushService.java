package com.immediate_push.model;

import java.util.List;


public class ImmediatePushService {
	
	private	ImmediatePushDAO_interface dao;
	
	public ImmediatePushService() {
		dao = new ImmediatePushDAO();
	}
	
	public ImmediatePushVO addImmediatePush(String admno, String ipcont, String pushno) {

		ImmediatePushVO immediatepushVO = new ImmediatePushVO();

		immediatepushVO.setAdmno(admno);
		immediatepushVO.setIpcont(ipcont);
		immediatepushVO.setPushno(pushno);
		dao.insert(immediatepushVO);

		return immediatepushVO;
	}
	
	public ImmediatePushVO updateImmediatePush(String ipno,String admno, String ipcont, String pushno) {

		ImmediatePushVO immediatepushVO = new ImmediatePushVO();

		immediatepushVO.setIpno(ipno);
		immediatepushVO.setAdmno(admno);
		immediatepushVO.setIpcont(ipcont);
		immediatepushVO.setPushno(pushno);
		dao.update(immediatepushVO);

		return immediatepushVO;
	}

	public void deleteImmediatePush(String ipno) {
		dao.delete(ipno);
	}

	public ImmediatePushVO getOneImmediatePush(String ipno) {
		return dao.findByPrimaryKey(ipno);
	}

	public List<ImmediatePushVO> getAll() {
		return dao.getAll();
	}
}

