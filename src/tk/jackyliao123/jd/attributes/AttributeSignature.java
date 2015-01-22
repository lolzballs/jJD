package tk.jackyliao123.jd.attributes;

public class AttributeSignature extends AttributeInfo {
    public final int signatureIndex;
    public AttributeSignature(int attributeNameIndex, int signatureIndex) {
        super(attributeNameIndex, 2);
        this.signatureIndex = signatureIndex;
    }
}
