package tk.jackyliao123.jd;

import tk.jackyliao123.jd.attributes.AttributeInfo;
import tk.jackyliao123.jd.cpinfo.*;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantIntegerInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantFieldReferenceInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantMethodReferenceInfo;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassFile {
	public static final int MAGIC_VALUE = 0xCAFEBABE;
	private static final int BUFFER_SIZE = 4096;

	private DataInputStream input;

	private int minorVersion;
	private int majorVersion;
	private int constantPoolCount;
	private ConstantPoolInfo[] constantPool;
	private int accessFlags;
	private int thisIndex;
	private int superIndex;
	private int interfaceCount;
	private int[] interfacesIndex;
	private int fieldsCount;
	private FieldInfo[] fields;
	private int methodCount;
	public MethodInfo[] methods;
	private int attributesCount;
	private AttributeInfo[] attributes;

	public ClassFile(InputStream input) {
		this.input = new DataInputStream(input);
	}
	public void loadClass() throws IOException, InvalidClassFileException {
		int magicValue = input.readInt();
		if(magicValue != MAGIC_VALUE){
			throw new InvalidClassFileException("Invalid magic value: 0x" + Integer.toHexString(magicValue).toUpperCase());
		}
		minorVersion = input.readUnsignedShort();
		majorVersion = input.readUnsignedShort();
		System.out.println("Class file is java version: " + majorVersion + "." + minorVersion);

		constantPoolCount = input.readUnsignedShort();
		constantPool = ConstantPoolInfo.getConstantPool(input, constantPoolCount - 1);
		accessFlags = input.readUnsignedShort();
		thisIndex = input.readUnsignedShort();
		superIndex = input.readUnsignedShort();
		interfaceCount = input.readUnsignedShort();
		interfacesIndex = new int[interfaceCount];
		for (int i = 0; i < interfaceCount; i++) {
			interfacesIndex[i] = input.readUnsignedShort();
		}

		fieldsCount = input.readUnsignedShort();
		fields = FieldInfo.getFieldInfo(constantPool, input, fieldsCount);

		methodCount = input.readUnsignedShort();
		methods = MethodInfo.getMethodInfo(constantPool, input, methodCount);

		attributesCount = input.readUnsignedShort();

		System.out.println("Class loading finished!");
		System.out.printf("There are %d constants, %d interfaces, %d fields, %d methods, %d attributes%n", constantPoolCount, interfaceCount, fieldsCount, methodCount, attributesCount);

		PseudoExecutor executor = new PseudoExecutor(this);
		executor.startPseudoExecution();

//		outputConstantPool();

//		Disassembler disassembler = new Disassembler(this);
//		disassembler.disassemble();
	}
	public void outputConstantPool() {
		for (int i = 0; i < constantPoolCount - 1; i++) {
			ConstantPoolInfo constantPoolInfo = constantPool[i];
			if (constantPoolInfo instanceof ConstantUTF8Info) {
				System.out.println(i + 1 + ": " + constantPoolInfo + "	// " + ((ConstantUTF8Info) constantPoolInfo).string);
			} else if (constantPoolInfo instanceof ConstantStringInfo) {
//				System.out.println(i + 1 + ": " + constantPoolInfo + "	// " + getConstantUTF8(((ConstantStringInfo) constantPoolInfo).stringIndex));
			} else{
				System.out.println(i + 1 + ": " + constantPoolInfo);
			}
		}
	}
	public String getConstantFieldSignature(int position) {
		ConstantFieldReferenceInfo field = ((ConstantFieldReferenceInfo) getConstant(position));
		String className = getConstantClassName(field.classIndex);
		ConstantNameAndTypeInfo nameAndType = getConstantNameAndTypeInfo(field.nameAndTypeIndex);
		String fieldName = getConstantUTF8(nameAndType.nameIndex).string;
		String fieldDesc = getConstantUTF8(nameAndType.descriptorIndex).string;
		return "Field " + className + "." + fieldName + ":" + fieldDesc;
	}
	public String getConstantMethodSignature(int position) {
		ConstantMethodReferenceInfo method = getConstantMethodReference(position);
		String className = getConstantClassName(method.classIndex);
		ConstantNameAndTypeInfo nameAndType = getConstantNameAndTypeInfo(method.nameAndTypeIndex);
		String methodName = getConstantUTF8(nameAndType.nameIndex).string;
		String methodDesc = getConstantUTF8(nameAndType.descriptorIndex).string;
		return "Method " + className + "." + methodName + methodDesc;
	}
	public String getConstantClassName(int position) {
		return getConstantUTF8(getConstantClass(position).nameIndex).string;
	}
	public ConstantNameAndTypeInfo getConstantNameAndTypeInfo(int position) {
		return (ConstantNameAndTypeInfo) getConstant(position);
	}
	public ConstantMethodReferenceInfo getConstantMethodReference(int position) {
		return (ConstantMethodReferenceInfo) getConstant(position);
	}
	public ConstantIntegerInfo getConstantInteger(int position) {
		return (ConstantIntegerInfo) getConstant(position);
	}
	public ConstantClassInfo getConstantClass(int position) {
		return (ConstantClassInfo) getConstant(position);
	}

	public ConstantStringInfo getConstantString(int position) {
		return (ConstantStringInfo) getConstant(position);
	}
	public ConstantUTF8Info getConstantUTF8(int position) {
		return (ConstantUTF8Info) getConstant(position);
	}
	public ConstantPoolInfo getConstant(int position) {
		return constantPool[position - 1];
	}
}
