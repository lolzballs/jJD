package tk.jackyliao123.jd.cpinfo;

public class ConstantClassInfo extends ConstantPoolInfo {
    public final int nameIndex;
    public ConstantClassInfo(int nameIndex) {
        super(CLASS);
        this.nameIndex = nameIndex;
    }
}
