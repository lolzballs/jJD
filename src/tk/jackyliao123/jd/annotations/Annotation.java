package tk.jackyliao123.jd.annotations;

public class Annotation {
    public final int typeIndex;
    public final int numElementValuePairs;
    public final ElementValuePair[] elementValuePairs;

    public Annotation(int typeIndex, int numElementValuePairs, ElementValuePair[] elementValuePairs) {
        this.typeIndex = typeIndex;
        this.numElementValuePairs = numElementValuePairs;
        this.elementValuePairs = elementValuePairs;
    }
}

class ElementValuePair {
    public final int elementNameIndex;
    public final ElementValue value;

    ElementValuePair(int elementNameIndex, ElementValue value) {
        this.elementNameIndex = elementNameIndex;
        this.value = value;
    }
}

