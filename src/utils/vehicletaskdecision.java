package utils;

import java.text.DecimalFormat;

public class vehicletaskdecision {//主要是车辆的卸载决策
    //创建建筑物
	static building b1=new building(15,1031,275,1031,275,922,15,922);
	static building b2=new building(15,737,275,737,275,568,15,568);
	static building b3=new building(15,235,275,235,275,15,15,15);
	static building b4=new building(309,885,683,885,683,767,309,767);
	static building b5=new building(320,461,676,461,676,322,320,322);
	
	public static task taskdecide(task ta,vehicle v){//返回true代表车辆自己算，false代表卸载
		double ecvcpu=1;//所有设备都是，计算1单位的数据消耗的能量----车辆
		double ecucpu=2;//无人机
		double ecbcpu=0;//基站
		double esv=20;//发送单位数据耗能----车辆
		double esu=50;//无人机
		double esb=0;//基站
		//下面是计算各种距离的代码用于生成日志！！！
		//车辆与基站的距离
		double v2bdistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		//无人机和基站的距离
		double u2bdistance=channelgain.getdistance(main.u.getX(), main.u.getY(), main.u.getZ(), basestation.x, basestation.y, basestation.z);
		//车辆与无人机的距离
		double v2udistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, main.u.getX(), main.u.getY(), main.u.getZ());
		//是否车辆与基站之前有障碍物
		double havebuilding;
		if(vehicletaskdecision.basedecide(v)){
			havebuilding=0;
		}
		else{
			havebuilding=1;
		}
		ta.v2bdistance=v2bdistance;
		ta.u2bdistance=u2bdistance;
		ta.v2udistance=v2udistance;
		ta.havebuilding=havebuilding;
		//接下来是带宽传输功率背景噪声等不变的量
		ta.backgroundnoise=transmissionspeed.n0;
		ta.band=transmissionspeed.w;
		ta.transmissionpower=transmissionspeed.pn;
		//下面是信道增益
		double distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		double chann=channelgain.getchannelgain(distance);
		ta.v2bchannelgain=chann;//车辆对基站
		distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, uav.x, uav.y, uav.z);
		chann=channelgain.getchannelgain(distance);
		ta.v2uchannelgain=chann;//车辆对无人机
		//接下来是单位发送耗能和单位计算耗能
		ta.vsende=esv;//车辆发送单位耗能
		ta.usende=esu;
		ta.vcalcue=ecvcpu;
		ta.ucalcue=ecucpu;
		
		DecimalFormat df=new DecimalFormat("#0.000000");
		DecimalFormat df1=new DecimalFormat("#0.0000000000000");
		//各种时间时间时间时间时间时间时间时间时间时间时间时间
		double sendtimetobase=(ta.offloadsize)/(transmissionspeed.gettransmissionspeed(ta.origin,0)*1024);
		double calculation=(ta.revolution)/(50*1024)+800*basestationthread.tasklist.size()/(main.b.calculation*1024);//计算自己的时间和计算列表里的任务的时间
		double receivetimebase=(ta.receivesize)/(transmissionspeed.gettransmissionspeed(ta.origin, 0)*1024);
		double receivetimebtou=ta.receivesize/(20*1024);
		double totaltimebase=sendtimetobase+calculation+receivetimebase;
		double self=ta.revolution/(v.calculation*1024);
		
		//在无人机通信范围内但是不和基站直接相连：1 在无人机上算的总时间，2 在基站上算的总时间
		//1 先算在无人机上算的,不管是给无人机还是基站都不考虑距离对传输时间的影响
		double vtouspeed=transmissionspeed.gettransmissionspeed(ta.origin,1);
		double sendtimetouav=(ta.offloadsize)/(vtouspeed*1024);
		double receivetimeuav=(ta.receivesize)/(vtouspeed*1024);
		double calculationtouav=(ta.revolution)/(main.u.calculation*1024)+(((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*(uavthread.tasklist.size()+1))/(main.u.calculation*1024);
		double totaltouav=sendtimetouav+calculationtouav+receivetimeuav;
		//2在算卸载去基站的无人机和基站的传输速率是20Mb
		double uavtobase=(ta.offloadsize)/(20*1024);
		double sendtobase=uavtobase+sendtimetouav;
		double calculationtobase=(ta.revolution)/(main.b.calculation*1024)+((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*basestationthread.tasklist.size()/(main.b.calculation*1024);
		double receivetobase=receivetimebtou+receivetimeuav;
		double totaltobase=sendtobase+calculationtobase+receivetobase;
		//System.out.println("自己算"+self);
		//System.out.println("无人机算"+totaltouav);
		//System.out.println("基站算"+totaltobase);
		
		//各种能耗能耗能耗能耗能耗能耗能耗能耗能耗能耗能耗
		double energycv=(ta.revolution*ecvcpu)/1000;//车辆自己计算任务耗费的能量
		double energysv=(ta.offloadsize/1024)*esv;//车辆发送该任务需要的能量
		double energycu=(ta.revolution*ecucpu)/1000;//无人机计算该任务需要的能量
		double energyrsu=(ta.receivesize/1024)*esu;//无人机返回给车辆结果的能耗
		double energysu=(ta.offloadsize/1024)*esu;//无人机发送该任务需要的能耗
		double energycb=ta.revolution*ecbcpu;//基站计算该任务需要的能耗========不需要
		
		//车辆自己计算的能耗
		double totalv=energycv;
		//无人机计算的能耗
		double totalvtou=energysv+energycu+energyrsu;
		//基站计算的能耗
		double totaltobs=energysv;
		//无人机中继基站算的能耗
		double totaltoutobs=energysv+energysu+energyrsu;
		
		//总的效用计算
		//车辆自己计算的总效用
		double totalutilityv =main.ke*((totalv-main.mine)/(main.maxe-main.mine))+main.kt*((self-main.mint)/(main.maxt-main.mint));
        //无人机算的总效用
		double totalutilitytou =main.ke*((totalvtou-main.mine)/(main.maxe-main.mine))+main.kt*((totaltouav-main.mint)/(main.maxt-main.mint));
        //基站计算的总效用
		double totalutilitytobs =main.ke*((totaltobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltimebase-main.mint)/(main.maxt-main.mint));
        //无人机中继的基站计算的总效用
		double totalutilitytoutobs =main.ke*((totaltoutobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltobase-main.mint)/(main.maxt-main.mint));
       
		boolean touav=true;
		boolean tobs=true;
		if(vehicletaskdecision.basedecide(v)){
			tobs=true;
			System.out.println("可以与基站直接通信，交付基站总时间为："+totaltimebase);
			System.out.println("sendtimetobase:"+df.format(sendtimetobase)+"   calculation:"+df.format(calculation)+"   receivetimebase:"+df.format(receivetimebase));
			//System.out.println("车辆给基站的传输速度："+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 0)));
		}
		else{
			tobs=false;
			System.out.println("不可以与基站直接通信");
		}//此处无人机是三维的而车辆坐标是二维的，车辆的Z坐标默认为0
		if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){
			touav=false;
			System.out.println("不在无人机通信范围");
		}
		else{
			touav=true;
			System.out.println("在无人机通信范围，卸载到无人机时间："+df.format(totaltouav)+"   通过无人机卸载到基站时间"+df.format(totaltobase));
		}
		
		
//		System.out.println("任务大小："+ta.offloadsize+"  需要转速："+ta.revolution);
//		System.out.println("车辆发无人机的传输速度："+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 1)));
//		System.out.println("无人机算：  车辆发送给无人机时间："+df.format(sendtimetouav)+"  无人机返回给车辆时间："+df.format(receivetimeuav));
//		System.out.println("无人机中继基站算：  发送给无人机的时间："+df.format(sendtimetouav)+"  无人机给基站的时间："+df.format(uavtobase)+"   发送总时间："+df.format(sendtobase)+"   基站返回给无人机："+df.format(receivetimebtou)+"   无人机返回给基站"+df.format(receivetimeuav));
		System.out.println("自己算时间："+df.format(self)+"  无人机算时间："+df.format(totaltouav)+"   通过无人机给基站的时间："+df.format(totaltobase));
		System.out.println("车辆自己算的能耗："+totalv);
		System.out.println("车辆卸载到无人机的能耗："+totalvtou);
		System.out.println("车辆直接卸载到基站的能耗："+totaltobs);
		System.out.println("车辆以无人机为中继卸载到基站的能耗："+totaltoutobs);
		System.out.println("车辆自己算的总效用："+totalutilityv);
		System.out.println("车辆卸载到无人机的总效用："+totalutilitytou);
		System.out.println("车辆卸载到基站的总效用："+totalutilitytobs);
		System.out.println("车辆以无人机为中继卸载到基站算的总效用："+totalutilitytoutobs);
		System.out.println("当前无人机列表大小为："+uavthread.tasklist.size());
		System.out.println("无人机计算总时间："+calculationtouav);
		System.out.println("任务编号："+ta.taskid);
		
		double[] total=new double[4];
		total[0]=totalutilityv;
		total[1]=totalutilitytou;
		total[2]=totalutilitytobs;
		total[3]=totalutilitytoutobs;
		int[] totalsign=new int[4];
		totalsign[0]=0;
		totalsign[1]=1;
		totalsign[2]=2;
		totalsign[3]=3;
		double[] totalsendtime=new double[4];
		totalsendtime[0]=0;
		totalsendtime[1]=sendtimetouav;
		totalsendtime[2]=sendtimetobase;
		totalsendtime[3]=sendtobase;
		double n;
		int n1;
		for(int i =0;i<3;i++){
			for(int j=0;j<3;j++){
				if(total[j]>total[j+1]){
					
					n=total[j];
					total[j]=total[j+1];
					total[j+1]=n;
					n1=totalsign[j];
					totalsign[j]=totalsign[j+1];
					totalsign[j+1]=n1;
					
				}
				
			}
		}
	    //tobs=false;//使车辆和基站与无人机都无法相连@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//touav=false;//
		ta.cost0=totalutilityv;
		ta.cost1=totalutilitytou;
		ta.cost2=totalutilitytobs;
		ta.cost3=totalutilitytoutobs;
		ta.energyconsumption0=totalv;
		ta.energyconsumption1=totalvtou;
		ta.energyconsumption2=totaltobs;
		ta.energyconsumption3=totaltoutobs;
		if(tobs){//与基站可以直接通信
			if(touav){//可以与无人机通信
				//for(int i=0;i<4;i++) {//修改为无人机不中继
					//if(totalsign[i]!=3) {
						ta.setDec(new decision(totalsign[0],total[0],totalsendtime[0]));	
						return(ta);
					}
				
			
				//}
				
				
		//	}
			else{//不可以与无人机通信
				for(int i=0;i<4;i++){
					if(totalsign[i]!=1&&totalsign[i]!=3){
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}
				}
			}
				}
			
		else{//不可以与基站直接通信
			if(touav){//可以与无人机通信
				for(int i=0; i<4;i++) {
					if(totalsign[i]!=2) {//修改为无人机不能中继
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}
				}
}
			else{//不可以与无人机通信
				for(int i=0;i<4;i++){
					if(totalsign[i]!=2&&totalsign[i]!=3&&totalsign[i]!=1){
						//System.out.println(totalsign[i]+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
						return(ta);
					}

				}
			}
		}
		
		System.out.println("********************************决策出错*****************************************************************");
		return(ta);
//		if(vehicletaskdecision.basedecide(v)){//可以与基站通信                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
//			//
//			if(self>totaltimebase){
//				ta.setDec(new decision(2,totaltimebase,sendtimetobase));
//				return(ta);//返回假，把任务卸载到基站，并把任务挂载到基站的处理队列中
//			}
//			else{
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);//返回真，车辆自己计算
//		}
//		}
//		else{//不可以与基站通信，继续判断卸载到哪里
//			//首先判断与无人机是否能通信
//			if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){//不在无人机通信范围内，自己算
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);
//			}
//			else{//在无人机通信范围内，判断决策
//				if(self<=totaltouav&&self<=totaltobase){//自己计算
//					ta.setDec(new decision(0,self,0.0));
//					return(ta);
//				}
//				else if(totaltouav<=self&&totaltouav<=totaltobase){//卸载到无人机
//                    ta.setDec(new decision(1,totaltouav,sendtimetouav));
//					return(ta);
//				}
//				else{//卸载到基站
//                    ta.setDec(new decision(3,totaltobase,sendtobase));
//					return(ta);
//				}
//			}
//			
//		}
		
	}
	public static boolean basedecide(vehicle v){//判断车辆与建筑物之间是否有障碍物
		if(b1.judge(basestation.x, basestation.y, v.x, v.y)||
				b2.judge(basestation.x, basestation.y, v.x, v.y)||
				b3.judge(basestation.x, basestation.y, v.x, v.y)||
				b4.judge(basestation.x, basestation.y, v.x, v.y)||
				b5.judge(basestation.x, basestation.y, v.x, v.y)
				)
			return(false);//只要有一个建筑物真就代表有障碍物，就返回假代表无法直接相连
		else
			return(true);//所欲建筑物都假代表都不相交，可以直接相连
	}
	
}
