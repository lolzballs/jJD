package tk.jackyliao123.jd;

public class Util {
	public static int b2ub(byte a) {
		return a & 0xff;
	}
	public static int b2us(byte a, byte b) {
		return (b2ub(a) << 8) + b2ub(b);
	}
	public static short bytesToSignedShort(byte a, byte b) {
		return (short) ((b2ub(a) << 8) + b2ub(b));
	}
	
	public static int getInt(byte[] array, int index){
		return
				((array[index] & 0xFF) << 0x18) +
						((array[index + 1] & 0xFF) << 0x10) +
						((array[index + 2] & 0xFF) << 0x08) +
						((array[index + 3] & 0xFF));
	}
}
