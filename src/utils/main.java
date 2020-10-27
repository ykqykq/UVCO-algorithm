package utils;

import java.util.TreeMap;

import javax.print.attribute.standard.OrientationRequested;

public class main {

	static int num=33;//*****************车辆的仿真数量
	static TreeMap<Double, task> maintasklist=new TreeMap<Double, task>();//所有处理完的任务链表
	static double[][] vehiclestation=new double[100][100];
	static uav u=new uav(1,500,500,0,10);//初始化无人机和基站
	static basestation b=new basestation(0,0,20,70);
	static double maxtransspeed=100;//单位Mb/s
	static double maxe=145.5;//最大最小的能耗与时间
	static double mine=0.024;
	static double maxt=5.6;
	static double mint=0.01953125;
	static double ke=0.9;//能量的权重
	static double kt=0.1 ;//时间的权重
	
	public static void main(String[] args)throws Exception{
		
		//basestation bs=new basestation(-144.0,627.0,50);//定义基站无人机读取文件
		//uav ua=new uav(541.0,639.0,50.0,3);
		readtraffic mobilitymodel=new readtraffic();   
		mobilitymodel.readcar("F:\\traffic.txt");
		mobilitymodel.readuav("F:\\uav.txt");
		//System.out.println(mobilitymodel.treeMapArray);
		
		
		
		new Thread(new uavthread(u)).start();//无人机id为1
		new Thread(new basestationthread(b)).start();//基站id为0
		new Thread(new clockthread(2)).start();
		new allthreadstate(num);//车辆线程是否结束的标志，true表示还在，false表示已经死亡
		for(int i=3;i<num+3;i++){
			
			//new Thread(new vehiclethread(i,new vehicle(i,2,2,vehicleability.creatatask(3)))).start();
			new Thread(new vehiclethread(i,new vehicle(i,2,2,0.7))).start();
		}
		//new Thread(new vehiclethread(2,new vehicle(2,2,2,0.8))).start();
		//new Thread(new vehiclethread(3,new vehicle(3,2,2,0.5))).start();
		//new Thread(new vehiclethread(4,new vehicle(4,2,2,0.5))).start();
		
		
	}
	public static String six(double a){
		String s=String.format("%.6f", a);
		return(s);
	}
}
