package com.immediate_push.model;

import java.util.List;


public class ImmediatePushService {
	
	private	ImmediatePushDAO_interface dao;
	
	public ImmediatePushService() {
		dao = new ImmediatePushDAO();
	}
	
	public ImmediatePushVO addEmp(String admno, String ipcont, String pushno) {

		ImmediatePushVO immediatepushVO = new ImmediatePushVO();

		immediatepushVO.setAdmno(admno);
		immediatepushVO.setIpcont(ipcont);
		immediatepushVO.setPushno(pushno);
		dao.insert(immediatepushVO);

		return immediatepushVO;
	}
	
	public ImmediatePushVO updateEmp(String ipno,String admno, String ipcont, String pushno) {

		ImmediatePushVO immediatepushVO = new ImmediatePushVO();

		immediatepushVO.setIpno(ipno);
		immediatepushVO.setAdmno(admno);
		immediatepushVO.setIpcont(ipcont);
		immediatepushVO.setPushno(pushno);
		dao.update(immediatepushVO);

		return immediatepushVO;
	}

	public void deleteImmediate_push(String ipno) {
		dao.delete(ipno);
	}

	public ImmediatePushVO getOneEmp(String ipno) {
		return dao.findByPrimaryKey(ipno);
	}

	public List<ImmediatePushVO> getAll() {
		return dao.getAll();
	}
}

