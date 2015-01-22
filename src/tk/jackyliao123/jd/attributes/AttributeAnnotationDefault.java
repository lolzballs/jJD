package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.annotations.ElementValue;

public class AttributeAnnotationDefault extends AttributeInfo{
    public final ElementValue value;
    public AttributeAnnotationDefault(int attributeNameIndex, int attributeLength, ElementValue value) {
        super(attributeNameIndex, attributeLength);
        this.value = value;
    }
}
