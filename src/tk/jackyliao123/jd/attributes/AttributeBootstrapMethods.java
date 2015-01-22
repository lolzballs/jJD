package tk.jackyliao123.jd.attributes;

public class AttributeBootstrapMethods extends AttributeInfo {
    public final int numBootstrapMethods;
    public final BootstrapMethod[] bootstrapMethods;
    public AttributeBootstrapMethods(int attributeNameIndex, int attributeLength, int numBootstrapMethods, BootstrapMethod[] bootstrapMethods) {
        super(attributeNameIndex, attributeLength);
        this.numBootstrapMethods = numBootstrapMethods;
        this.bootstrapMethods = bootstrapMethods;
    }

    class BootstrapMethod {
        public final int bootstrapMethodRef;
        public final int numBootstrapArguments;
        public final int[] bootstrapArguments;

        public BootstrapMethod(int bootstrapMethodRef, int numBootstrapArguments, int[] bootstrapArguments) {
            this.bootstrapMethodRef = bootstrapMethodRef;
            this.numBootstrapArguments = numBootstrapArguments;
            this.bootstrapArguments = bootstrapArguments;
        }
    }
}


