package utils;

import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class uavthread implements Runnable{
    static double taskcount=0;
	static TreeMap<Double, task> tasklist=new TreeMap<Double, task>();
	uav u;
	String a1="";
	public uavthread(uav u){
		this.u=u;
	}
	
	public void run(){
		//时间变量，存储开始时间当前时间和上个位置到达的时间
		TreeMap<Double, Location> uavtreeMap = readtraffic.uavtreeMapArray.get(0);//存储无人机的轨迹的轨迹
		Location location;
		Date d=new Date();
		long starttime=d.getTime();
		long currenttime=starttime;
		long lasttime=currenttime;
		long lasttime1=clock.clocktime;
		task ta=null;
		time t=new time();
		while(true){//每次循环执行一个任务
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
              e.printStackTrace();
			}
			d=new Date();//每次循环都获取当前时间
			currenttime=d.getTime();
			if((clock.clocktime-lasttime1)>1000){//每秒更新位置信息，判断线程是否结束
			    location=uavtreeMap.firstEntry().getValue();
                u.setX(location.x);//每秒更新无人机位置信息，设置XY甚至Z的坐标
                u.setY(location.y);
                lasttime1=clock.clocktime;
                uavtreeMap.remove(uavtreeMap.firstKey());
			}
			if(Cycliccontrol.cycliccontrol(u.id)){
			synchronized (a1) {
			if(t.nexttime>0){
				t.Cumulativetime=t.Cumulativetime+t.nexttime;
				if(t.Cumulativetime<clock.timeslot){
					ta.endtime=clock.clocktime;
				    main.maintasklist.put((double) ta.tasknum, ta);//把任务加入已完成链表
				    vehiclethread.havetask[ta.origin]=false;//把车辆上标志任务状态的变量置为假，代表任务已经完成
				    System.out.println("无人机计算了id为"+ta.origin+"的车的任务ID:"+ta.tasknum);
				    taskcount++;//无人机任务完成数加一
				    t.nexttime=0;   
				}
				else{
					t.nexttime=t.Cumulativetime-clock.timeslot;
					t.Cumulativetime=0;
					Cycliccontrol.addsign(u.id);

				}
			}
			else{
			if(tasklist.size()>0){
				
			    ta=tasklist.firstEntry().getValue(); 
			    tasklist.remove(tasklist.firstKey());
			    double calculationtouav=(ta.revolution)/(3*1024);//当前任务的执行时间
                t.currenttime=calculationtouav*1000;
                t.Cumulativetime=t.Cumulativetime+t.currenttime;
                if(t.Cumulativetime<clock.timeslot){//判断是否能在时间步长内执行完
                	d=new Date();
    			    currenttime=d.getTime();
    			    ta.endtime=clock.clocktime;
    			    main.maintasklist.put((double) ta.tasknum, ta);//把任务加入已完成链表
    			    vehiclethread.havetask[ta.origin]=false;//把车辆上标志任务状态的变量置为假，代表任务已经完成
    			    System.out.println("无人机计算了id为"+ta.origin+"的车的任务ID:"+ta.tasknum);
    			    taskcount++;//无人机任务完成数加一
    			    t.nexttime=0;    			    
                }
                else{
                	t.nexttime=t.Cumulativetime-clock.timeslot;
                	t.Cumulativetime=0;
                	Cycliccontrol.addsign(u.id);

                }			    
				}
			else{
				Cycliccontrol.addsign(u.id);

			}
				}
			}
		}
			}
		}
       
	
}
