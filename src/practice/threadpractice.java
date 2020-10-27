package practice;

import java.util.concurrent.locks.ReentrantLock;


public class threadpractice implements Runnable{

	private ReentrantLock r = new ReentrantLock();
	int id;
	static int i=0;
	static String aa="";
	public threadpractice(int id ){
		this.id=id;
	}
	public void run() {
		while(true){
		String name=Thread.currentThread().getName();
		if(name.equals(id+"")){
			i=(i+1)/2;
		    this.print();
		    try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}
		
	}
	public synchronized void print(){
		//System.out.println("开始ID为："+id);
		Thread t;
		try {
			Thread.sleep(1);
			System.out.println("结束ID为："+id);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}  
	
	public static Thread getThreadByName(String threadName) {
		for (Thread t : Thread.getAllStackTraces().keySet()) {
		if (t.getName().equals(threadName)) {
		//System.out.println(t.getName());
		return(t);
		}
		}
		return(null);}

public static void main(String[] args){
	Thread t1=new Thread(new threadpractice(1));
	Thread t2=new Thread(new threadpractice(0));
	t1.setName("1");
	t2.setName("0");
	t1.start();
	t2.start();
	
}

	
}
