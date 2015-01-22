package tk.jackyliao123.jd.cpinfo.numerics;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

public class ConstantIntegerInfo extends ConstantPoolInfo {
    public final int value;

    public ConstantIntegerInfo(int value) {
        super(INTEGER);
        this.value = value;
    }
}
