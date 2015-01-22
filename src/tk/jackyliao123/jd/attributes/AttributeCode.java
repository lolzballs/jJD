package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeCode extends AttributeInfo {
    public final int maxStack;
    public final int maxLocals;
    public final int codeLength;
    public final byte[] code;
    public final int exceptionTableLength;
    public final Exception[] exceptionTable;
    public final int attributesCount;
    public final AttributeInfo[] attributes;
    public AttributeCode(int attributeNameIndex, int attributeLength, int maxStack, int maxLocals, int codeLength, byte[] code, int exceptionTableLength, Exception[] exceptionTable, int attributesCount, AttributeInfo[] attributes) {
        super(attributeNameIndex, attributeLength);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.code = code;
        this.exceptionTableLength = exceptionTableLength;
        this.exceptionTable = exceptionTable;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    static class Exception {
        public final int startPC;
        public final int endPC;
        public final int handlerPC;
        public final int catchType;

        public Exception(int startPC, int endPC, int handlerPC, int catchType) {
            this.startPC = startPC;
            this.endPC = endPC;
            this.handlerPC = handlerPC;
            this.catchType = catchType;
        }
    }

    public static AttributeCode getAttributeCode(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int maxStack = input.readUnsignedShort();
        int maxLocals = input.readUnsignedShort();
        int codeLength = input.readInt(); // TODO: Make this not crash for values larger than 2^31
        byte[] code = new byte[codeLength];
        for (int j = 0; j < codeLength; j++) {
            code[j] = input.readByte();
        }
        int exceptionTableLength = input.readUnsignedShort();
        Exception[] exceptionTable = new Exception[exceptionTableLength];
        for (int j = 0; j < exceptionTableLength; j++) {
            exceptionTable[j] = new Exception(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }
        int attributesCount = input.readUnsignedShort();
        AttributeInfo[] attributes = AttributeInfo.getAttributeInfo(constantPool, input, attributesCount);
        return new AttributeCode(attributeNameIndex, attributeLength, maxStack, maxLocals, codeLength, code, exceptionTableLength, exceptionTable, attributesCount, attributes);
    }
}
