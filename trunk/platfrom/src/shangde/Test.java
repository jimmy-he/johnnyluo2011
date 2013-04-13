package shangde;

public class Test {
	public static void main(String[] args){
		int bai;
		int shi;
		int ge;
		for(int i=100;i<=999;i++){
			bai=i/100;
			ge=i%100;
			shi=(i/10)%10;
			if(bai*bai*bai+shi*shi*shi+ge*ge*ge==i){
				System.out.println(i+"是水仙花数");
				}
		}
	}
}
