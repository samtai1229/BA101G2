package com.immediate_push.model;

public class ImmediatePushVO implements java.io.Serializable{
	private String ipno;
	private String admno;
	private String ipcont;
	private String pushno;
	public ImmediatePushVO() {
		super();
	}
	public String getIpno() {
		return ipno;
	}
	public void setIpno(String ipno) {
		this.ipno = ipno;
	}
	public String getAdmno() {
		return admno;
	}
	public void setAdmno(String admno) {
		this.admno = admno;
	}
	public String getIpcont() {
		return ipcont;
	}
	public void setIpcont(String ipcont) {
		this.ipcont = ipcont;
	}
	public String getPushno() {
		return pushno;
	}
	public void setPushno(String pushno) {
		this.pushno = pushno;
	}
	

}
