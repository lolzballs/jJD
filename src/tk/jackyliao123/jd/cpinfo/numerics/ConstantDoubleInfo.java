package tk.jackyliao123.jd.cpinfo.numerics;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

public class ConstantDoubleInfo extends ConstantPoolInfo {
    public final double value;
    public ConstantDoubleInfo(double value) {
        super(DOUBLE);
        this.value = value;
    }
}
