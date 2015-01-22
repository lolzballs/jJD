package tk.jackyliao123.jd.cpinfo.numerics;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

public class ConstantLongInfo extends ConstantPoolInfo {
    public final long value;
    public ConstantLongInfo(long value) {
        super(LONG);
        this.value = value;
    }
}
