package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.annotations.Annotation;

public class AttributeRuntimeVisibleAnnotations extends AttributeInfo {
    public final int attributeNameIndex;
    public final int attributeLength;
    public final int numAnnotations;
    public final Annotation[] annotations;
    public AttributeRuntimeVisibleAnnotations(int attributeNameIndex, int attributeLength, int attributeNameIndex1, int attributeLength1, int numAnnotations, Annotation[] annotations) {
        super(attributeNameIndex, attributeLength);
        this.attributeNameIndex = attributeNameIndex1;
        this.attributeLength = attributeLength1;
        this.numAnnotations = numAnnotations;
        this.annotations = annotations;
    }
}
