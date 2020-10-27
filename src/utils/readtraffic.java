package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;



public class readtraffic {
	static List<TreeMap<Double, Location>> treeMapArray;
	static List<TreeMap<Double, Location>> uavtreeMapArray;
	public void readcar(String pathname)throws Exception{
		double[][] data=new double[200][200];
		treeMapArray = new ArrayList<TreeMap<Double, Location>>();
		String[] d=new String[20];
		String d1;
		double x,y,time;
		int id;
		for(int i=0;i<200;i++){
			treeMapArray.add(i, new TreeMap<Double, Location>());
		}
		FileReader reader = new FileReader(pathname);
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
           // System.out.println(line);
            d=line.split(" ");
            d1=d[3].split("\\(")[1];
            d1=d1.split("\\)")[0];
            id=Integer.parseInt(d1);
            x=Double.parseDouble(d[5]);
            y=Double.parseDouble(d[6]);
            time=Double.parseDouble(d[2]);
            treeMapArray.get(id).put(time, new Location(x, y));
        }
		/*for(int i=0;i<100;i++){
			System.out.println(treeMapArray.get(i));
		}
		*/
	}
	public static void readuav(String path)throws Exception{
		double[][] data=new double[200][200];
		uavtreeMapArray = new ArrayList<TreeMap<Double, Location>>();
		String[] d=new String[20];
		String d1;
		double x,y,time;
		int id;
		double i=0;
		uavtreeMapArray.add(0, new TreeMap<Double, Location>());
		FileReader reader = new FileReader(path);
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
			
			d=line.split(", ");
			x=Double.parseDouble(d[0]);
			y=Double.parseDouble(d[1]);
			uavtreeMapArray.get(0).put(i, new Location(x, y));
			i++;
			//System.out.println();
		}
		//System.out.println(uavtreeMapArray.get(0));
		
	}
	public static void main(String[] args)throws Exception {//处理原始的tcl数据，把里面多余的东西去掉
		String[] d=new String[20];
		FileReader reader = new FileReader("D://traffic.tcl");
		BufferedReader br = new BufferedReader(reader);
		String line;
		FileOutputStream fos;
			fos = new FileOutputStream("D://traffic.txt");
		while ((line = br.readLine()) != null) {
			d=line.split(" ");
			if(!d[1].equals("set")){
				fos.write(line.getBytes());
				fos.write("\r\n".getBytes());
			}
		}
		}
           
	}


