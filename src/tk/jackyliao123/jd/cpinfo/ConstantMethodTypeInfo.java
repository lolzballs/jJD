package tk.jackyliao123.jd.cpinfo;

public class ConstantMethodTypeInfo extends ConstantPoolInfo {
    public final int descriptorIndex;
    public ConstantMethodTypeInfo(int descriptorIndex) {
        super(METHOD_TYPE);
        this.descriptorIndex = descriptorIndex;
    }
}
