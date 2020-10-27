package practice;

import java.util.Random;

import utils.CollisionMonitoring;
import utils.FaceIdentify;
import utils.licenseIdentify;
import utils.task;

public class ability {
	static Random rand = new Random();
	static double a=0;
    public static void creatatask(int ori){
   	 int i=rand.nextInt(ori);
   	 //task t=new task(FaceIdentify.offloadsize,FaceIdentify.revolution,FaceIdentify.receivesize, ori);
   	 if(i==0){
   		 a=0.5;
   	 }
   	 else if(i==1){
   		 
   		 a=0.7;
   	 }
   	 else{
   		a=0.8;
   	 }
   	 
   	System.out.println(i);
   	System.out.println(a);
 
    }
    public static void main(String[] args){
    	double i;
    	creatatask(3);

    }
    
}


