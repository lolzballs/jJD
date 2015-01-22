package tk.jackyliao123.jd.cpinfo;

public class ConstantStringInfo extends ConstantPoolInfo {
    public final int stringIndex;
    public ConstantStringInfo(int stringIndex) {
        super(STRING);
        this.stringIndex = stringIndex;
    }
}
