package practice;

public class sort {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		double[] total=new double[4];
		total[0]=22;
		total[1]=44;
		total[2]=11;
		total[3]=333;
		int[] totalsign=new int[4];
		totalsign[0]=2;
		totalsign[1]=4;
		totalsign[2]=1;
		totalsign[3]=3;
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
		for(int i=0;i<4;i++){
			System.out.println(total[i]);
		}
		for(int i=0;i<4;i++){
			System.out.println(totalsign[i]);
		}
	}

}
