package utils;

public class allthreadstate {

	public static boolean[] threadstate=new boolean[200];
	static int threadnum=0;
	public allthreadstate(int num){
		this.threadnum=num;
		for(int i=0;i<num+3;i++){
			threadstate[i]=true;
		}
	}
}
