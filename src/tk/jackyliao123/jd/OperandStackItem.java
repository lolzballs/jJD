package tk.jackyliao123.jd;

public class OperandStackItem {
	public String stringItem;
	public String type;
	public boolean category2;
	public OperandStackItem(String s, String t){
		stringItem = s;
		type = t;
		if(t.equals("D") || t.equals("J")){
			category2 = true;
		}
	}
	public OperandStackItem(int value){
		stringItem = Integer.toString(value);
		type = "I";
	}
	public OperandStackItem(long value){
		stringItem = Long.toString(value);
		category2 = true;
		type = "J";
	}
	public OperandStackItem(float value){
		stringItem = Float.toString(value);
		type = "F";
	}
	public OperandStackItem(double value){
		stringItem = Double.toString(value);
		category2 = true;
		type = "D";
	}
}
