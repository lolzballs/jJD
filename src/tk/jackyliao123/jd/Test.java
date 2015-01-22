package tk.jackyliao123.jd;

public class Test {
	public static void main(String[] args){
		int local_1 = 0b10111;
		int local_2 = 0b10;
		System.out.println(local_1 & local_1 << 43 | 54 ^ (32 * local_1 + local_2 * local_1 << 3));
	}
}
