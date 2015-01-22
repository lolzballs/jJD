package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

import java.io.DataInputStream;
import java.io.IOException;

public class AttributeInnerClass extends AttributeInfo {
    public final int numberOfClasses;
    public final InnerClass[] classes;
    public AttributeInnerClass(int attributeNameIndex, int attributeLength, int numberOfClasses, InnerClass[] classes) {
        super(attributeNameIndex, attributeLength);
        this.numberOfClasses = numberOfClasses;
        this.classes = classes;
    }

    public static AttributeInnerClass getAttributeInnerClass(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeNameIndex, int attributeLength) throws IOException {
        int numberOfClasses = input.readUnsignedShort();
        InnerClass[] classes = new InnerClass[numberOfClasses];
        for(int i = 0; i < numberOfClasses; i++) {
            classes[i] = new InnerClass(input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort(), input.readUnsignedShort());
        }
        return new AttributeInnerClass(attributeNameIndex, attributeLength, numberOfClasses, classes);
    }

    public static class InnerClass {
        public final int innerClassInfoIndex;
        public final int outerClassInfoIndex;
        public final int innerNameIndex;
        public final int innerClassAccessFlags;

        public InnerClass(int innerClassInfoIndex, int outerClassInfoIndex, int innerNameIndex, int innerClassAccessFlags) {
            this.innerClassInfoIndex = innerClassInfoIndex;
            this.outerClassInfoIndex = outerClassInfoIndex;
            this.innerNameIndex = innerNameIndex;
            this.innerClassAccessFlags = innerClassAccessFlags;
        }
    }
}
