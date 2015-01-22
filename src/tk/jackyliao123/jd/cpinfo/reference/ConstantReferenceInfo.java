package tk.jackyliao123.jd.cpinfo.reference;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

public abstract class ConstantReferenceInfo extends ConstantPoolInfo {
    public final int classIndex;
    public final int nameAndTypeIndex;
    public ConstantReferenceInfo(byte tag, int classIndex, int nameAndTypeIndex) {
        super(tag);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
