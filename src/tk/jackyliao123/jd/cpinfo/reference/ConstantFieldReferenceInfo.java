package tk.jackyliao123.jd.cpinfo.reference;

public class ConstantFieldReferenceInfo extends ConstantReferenceInfo {
    public ConstantFieldReferenceInfo(int classIndex, int nameAndTypeIndex) {
        super(FIELD_REF, classIndex, nameAndTypeIndex);
    }
}
