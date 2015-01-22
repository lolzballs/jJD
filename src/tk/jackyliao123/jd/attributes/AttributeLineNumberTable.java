package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeLineNumberTable extends AttributeInfo {
    public final int lineNumberTableLength;
    public final LineNumber[] lineNumberTable;
    public AttributeLineNumberTable(int attributeNameIndex, int attributeLength, int lineNumberTableLength, LineNumber[] lineNumberTable) {
        super(attributeNameIndex, attributeLength);
        this.lineNumberTableLength = lineNumberTableLength;
        this.lineNumberTable = lineNumberTable;
    }

    public static class LineNumber {
        public final int startPC;
        public final int lineNumber;

        LineNumber(int startPC, int lineNumber) {
            this.startPC = startPC;
            this.lineNumber = lineNumber;
        }
    }

    public static AttributeLineNumberTable getAttributeLineNumberTable(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int lineNumberTableLength = input.readUnsignedShort();
        LineNumber[] lineNumberTable = new LineNumber[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            lineNumberTable[i] = new LineNumber(input.readUnsignedShort(), input.readUnsignedShort());
        }
        return new AttributeLineNumberTable(attributeNameIndex, attributeLength, lineNumberTableLength, lineNumberTable);
    }
}

