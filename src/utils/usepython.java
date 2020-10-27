package utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class usepython {
    public static void main(String[] args)throws Exception{

    		usepython.print("0.0003208418891170431 0.0 0.3333333333333335 0.6666666666666667 0.6666666666666665 0.0 0.0 0.01116846678743139 0.0 0.8090473767002868 0.01116846678743139 0.0 0.0 0.0 0.032467532467532464 0.0 0.0 0.0 0.0 0.0");
    	
    }
	public static void print(String s)throws IOException,InterruptedException {
		String exe = "python";
        String command = "D:\\111111\\checkpoint1\\getresult.py";
        String num1 = s;
        String num2 = "Ñî¿­ä¿";
        String[] cmdArr = new String[] {exe,command,num1,num2};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String str = dis.readLine();
        process.waitFor();
        System.out.println(str);
        str = dis.readLine();
        System.out.println(str);
        str = dis.readLine();
        System.out.println(str);
        str = dis.readLine();
        System.out.println(str);

	}

}
