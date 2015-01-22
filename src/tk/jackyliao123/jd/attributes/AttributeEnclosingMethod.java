package tk.jackyliao123.jd.attributes;

public class AttributeEnclosingMethod extends AttributeInfo {
    public final int classIndex;
    public final int methodIndex;
    public AttributeEnclosingMethod(int attributeNameIndex, int classIndex, int methodIndex) {
        super(attributeNameIndex, 4);
        this.classIndex = classIndex;
        this.methodIndex = methodIndex;
    }
}
