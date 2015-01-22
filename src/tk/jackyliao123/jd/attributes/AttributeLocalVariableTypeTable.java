package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeLocalVariableTypeTable extends AttributeInfo {
    public final int localVariableTypeTableLength;
    public final LocalVariableType[] localVariableTypeTable;
    public AttributeLocalVariableTypeTable(int attributeNameIndex, int attributeLength, int localVariableTypeTableLength, LocalVariableType[] localVariableTypeTable) {
        super(attributeNameIndex, attributeLength);
        this.localVariableTypeTableLength = localVariableTypeTableLength;
        this.localVariableTypeTable = localVariableTypeTable;
    }

    public static AttributeInfo getAttributeLocalVariableTypeTable(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int localVariableTypeTableLength = input.readShort();
        LocalVariableType[] localVariableTypeTable = new LocalVariableType[localVariableTypeTableLength];
        for (int i = 0; i < localVariableTypeTableLength; i++) {
            localVariableTypeTable[i] = new LocalVariableType(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }
        return new AttributeLocalVariableTypeTable(attributeNameIndex, attributeLength, localVariableTypeTableLength, localVariableTypeTable);
    }

    public static class LocalVariableType {
        public final int startPC;
        public final int length;
        public final int nameIndex;
        public final int signatureIndex;
        public final int index;

        LocalVariableType(int startPC, int length, int nameIndex, int signatureIndex, int index) {
            this.startPC = startPC;
            this.length = length;
            this.nameIndex = nameIndex;
            this.signatureIndex = signatureIndex;
            this.index = index;
        }
    }
}
