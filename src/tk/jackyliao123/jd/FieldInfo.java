package tk.jackyliao123.jd;

import tk.jackyliao123.jd.attributes.AttributeInfo;
import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class FieldInfo {
    public final int accessFlags;
    public final int nameIndex;
    public final int descriptorIndex;
    public final int attributesCount;
    public final AttributeInfo[] attributes;
    public FieldInfo(int accessFlags, int nameIndex, int descriptorIndex, int attributesCount, AttributeInfo[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    public static FieldInfo[] getFieldInfo(ConstantPoolInfo[] constantPool, DataInputStream input, int fieldCount) throws IOException {
        FieldInfo[] fields = new FieldInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            int accessFlags = input.readUnsignedShort();
            int nameIndex = input.readUnsignedShort();
            int descriptorIndex = input.readUnsignedShort();
            int attributeCount = input.readUnsignedShort();
            AttributeInfo[] attributes = AttributeInfo.getAttributeInfo(constantPool, input, attributeCount);
            fields[i] = new FieldInfo(accessFlags, nameIndex, descriptorIndex, attributeCount, attributes);
        }
        return fields;
    }
}
