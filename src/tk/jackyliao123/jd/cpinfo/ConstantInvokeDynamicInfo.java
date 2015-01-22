package tk.jackyliao123.jd.cpinfo;

public class ConstantInvokeDynamicInfo extends ConstantPoolInfo {
    public final int bootstrapMethodAttrIndex;
    public final int nameAndTypeIndex;
    public ConstantInvokeDynamicInfo(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
        super(INVOKE_DYNAMIC);
        this.bootstrapMethodAttrIndex = bootstrapMethodAttrIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
