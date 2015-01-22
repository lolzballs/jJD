package tk.jackyliao123.jd.attributes;

public class AttributeConstantValue extends AttributeInfo{
    public final int constantValueIndex;
    public AttributeConstantValue(int attributeNameIndex, int constantValueIndex) {
        super(attributeNameIndex, 2);
        this.constantValueIndex = constantValueIndex;
    }
}
