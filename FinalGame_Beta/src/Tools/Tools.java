package Tools;

public class Tools {
	public Tools(){}
	
	public int randomRange(int min, int max){
		int range = max - min;
		double num = Math.random() * range + 1;
		num = num + min;
		return (int)num;
	}
	
	
}
