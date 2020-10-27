package utils;

public class decision {

	int de;//代表三个决策，用于车辆决策，0：车辆自己算  1：卸载到无人机算   2 ：卸载到基站算
	double time;//代表最终挂载到无人机或者自己计算用时多少
	double sendtime;//发送时间
	public decision(int de,double time,double sendtime){
		this.de=de;
		this.time=time;
		this.sendtime=sendtime;
	}
	public int getDe() {
		return de;
	}
	public void setDe(int de) {
		this.de = de;
	}
	public double getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getSendtime() {
		return sendtime;
	}
	public void setSendtime() {
		this.sendtime=sendtime;
	}
	
}
