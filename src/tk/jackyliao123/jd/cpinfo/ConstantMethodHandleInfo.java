package tk.jackyliao123.jd.cpinfo;

public class ConstantMethodHandleInfo extends ConstantPoolInfo {
    public final int referenceKind;
    public final int referenceIndex;
    public ConstantMethodHandleInfo(int referenceKind, int referenceIndex) {
        super(METHOD_HANDLE);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }
}
