package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.annotations.Annotation;

public class AttributeRuntimeInvisibleAnnotations extends AttributeInfo {
    public final int numAnnotations;
    public final Annotation[] annotations;
    public AttributeRuntimeInvisibleAnnotations(int attributeNameIndex, int attributeLength, int numAnnotations, Annotation[] annotations) {
        super(attributeNameIndex, attributeLength);
        this.numAnnotations = numAnnotations;
        this.annotations = annotations;
    }
}
