package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeLocalVariableTable extends AttributeInfo {
    public final int localVariableTableLength;
    public final LocalVariable[] localVariableTable;
    public AttributeLocalVariableTable(int attributeNameIndex, int attributeLength, int localVariableTableLength, LocalVariable[] localVariableTable) {
        super(attributeNameIndex, attributeLength);
        this.localVariableTableLength = localVariableTableLength;
        this.localVariableTable = localVariableTable;
    }

    public static AttributeLocalVariableTable getAttributeLocalVariableTable(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int localVariableTableLength = input.readUnsignedShort();
        LocalVariable[] localVariableTable = new LocalVariable[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            localVariableTable[i] = new LocalVariable(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }
        return new AttributeLocalVariableTable(attributeNameIndex, attributeLength, localVariableTableLength, localVariableTable);
    }

    public static class LocalVariable {
        public final int startPC;
        public final int length;
        public final int nameIndex;
        public final int descriptorIndex;
        public final int index;

        public LocalVariable(int startPC, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPC = startPC;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }
    }
}
