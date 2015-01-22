package tk.jackyliao123.jd.cpinfo;


import tk.jackyliao123.jd.cpinfo.numerics.ConstantDoubleInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantFloatInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantIntegerInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantLongInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantFieldReferenceInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantInterfaceMethodReferenceInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantMethodReferenceInfo;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class ConstantPoolInfo {
	public static final byte CLASS = 7;
	public static final byte FIELD_REF = 9;
	public static final byte METHOD_REF = 10;
	public static final byte INTERFACE_METHOD_REF = 11;
	public static final byte STRING = 8;
	public static final byte INTEGER = 3;
	public static final byte FLOAT = 4;
	public static final byte LONG = 5;
	public static final byte DOUBLE = 6;
	public static final byte NAME_AND_TYPE = 12;
	public static final byte UTF_8 = 1;
	public static final byte METHOD_HANDLE = 15;
	public static final byte METHOD_TYPE = 16;
	public static final byte INVOKE_DYNAMIC = 18;

	public final byte tag;

	public ConstantPoolInfo(byte tag) {
		this.tag = tag;
	}

	public static ConstantPoolInfo[] getConstantPool(DataInputStream input, int constantPoolLength) throws IOException {
		ConstantPoolInfo[] constantPool = new ConstantPoolInfo[constantPoolLength];
		for (int i = 0; i < constantPoolLength; i++) {
			int tag = input.readUnsignedByte();
			switch (tag) {
				case CLASS:
					constantPool[i] = new ConstantClassInfo(input.readUnsignedShort());
					break;
				case FIELD_REF:
					constantPool[i] = new ConstantFieldReferenceInfo(input.readUnsignedShort(), input.readUnsignedShort());
					break;
				case METHOD_REF:
					constantPool[i] = new ConstantMethodReferenceInfo(input.readUnsignedShort(), input.readUnsignedShort());
					break;
				case INTERFACE_METHOD_REF:
					constantPool[i] = new ConstantInterfaceMethodReferenceInfo(input.readUnsignedShort(), input.readUnsignedShort());
					break;
				case STRING:
					constantPool[i] = new ConstantStringInfo(input.readUnsignedShort());
					break;
				case INTEGER:
					constantPool[i] = new ConstantIntegerInfo(input.readInt());
					break;
				case FLOAT:
					constantPool[i] = new ConstantFloatInfo(input.readFloat());
					break;
				case LONG:
					constantPool[i] = new ConstantLongInfo(input.readLong());
					i++;
					break;
				case DOUBLE:
					constantPool[i] = new ConstantDoubleInfo(input.readDouble());
					i++;
					break;
				case NAME_AND_TYPE:
					constantPool[i] = new ConstantNameAndTypeInfo(input.readUnsignedShort(), input.readUnsignedShort());
					break;
				case UTF_8:
					byte[] b = new byte[input.readUnsignedShort()];
					input.readFully(b);
					constantPool[i] = new ConstantUTF8Info(b);
					break;
				case METHOD_HANDLE:
					constantPool[i] = new ConstantMethodHandleInfo(input.readUnsignedByte(), input.readUnsignedShort());
					break;
				case METHOD_TYPE:
					constantPool[i] = new ConstantMethodTypeInfo(input.readUnsignedShort());
					break;
				case INVOKE_DYNAMIC:
					constantPool[i] = new ConstantInvokeDynamicInfo(input.readUnsignedShort(), input.readUnsignedShort());
					break;
				default:
					System.out.println("ERROR! Tag is unknown: " + tag);
			}
		}
		return constantPool;
	}
}
