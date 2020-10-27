package practice;

import java.util.TreeMap;

import utils.task;

public class atreemap {

	public static void main(String[] args){
		
		TreeMap<Double, task> tasklist=new TreeMap<Double, task>();
		task a=new task(1,1,1,1);
		task b=new task(1,1,1,1);
		tasklist.put(1.0, a);
		tasklist.put(2.0, a);
		System.out.println(tasklist.keySet());
		for(double d:tasklist.keySet()){
			System.out.println(d);
		}
	}
}
