package tk.jackyliao123.jd.attributes;

public class AttributeSourceFile extends AttributeInfo {
    public final int sourceFileIndex;
    public AttributeSourceFile(int attributeNameIndex, int sourceFileIndex) {
        super(attributeNameIndex, 2);
        this.sourceFileIndex = sourceFileIndex;
    }
}
