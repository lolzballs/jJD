package tk.jackyliao123.jd.cpinfo;

public class ConstantNameAndTypeInfo extends ConstantPoolInfo {
    public final int nameIndex;
    public final int descriptorIndex;
    public ConstantNameAndTypeInfo(int nameIndex, int descriptorIndex) {
        super(NAME_AND_TYPE);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }
}
