package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeSourceDebugExtension extends AttributeInfo {
    public final int[] debugExtension;
    public AttributeSourceDebugExtension(int attributeNameIndex, int attributeLength, int[] debugExtension) {
        super(attributeNameIndex, attributeLength);
        this.debugExtension = debugExtension;
    }

    public static AttributeSourceDebugExtension getAttributeSourceDebugExtension(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int[] debugExtension = new int[attributeLength];
        for(int i = 0; i < attributeLength; i++) {
            debugExtension[i] = input.readUnsignedByte();
        }
        return new AttributeSourceDebugExtension(attributeNameIndex, attributeLength, debugExtension);
    }
}
