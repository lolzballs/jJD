package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeExceptions extends AttributeInfo {
    public final int numberOfExceptions;
    public final int[] exceptionIndexTable;
    public AttributeExceptions(int attributeNameIndex, int attributeLength, int numberOfExceptions, int[] exceptionIndexTable) {
        super(attributeNameIndex, attributeLength);
        this.numberOfExceptions = numberOfExceptions;
        this.exceptionIndexTable = exceptionIndexTable;
    }
    public static AttributeExceptions getAttributeExceptions(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int numberOfExceptions = input.readUnsignedShort();
        int[] exceptionIndexTable = new int[numberOfExceptions];
        for (int i = 0; i < numberOfExceptions; i++) {
            exceptionIndexTable[i] = input.readUnsignedShort();
        }
        return new AttributeExceptions(attributeNameIndex, attributeLength, numberOfExceptions, exceptionIndexTable);
    }
}
