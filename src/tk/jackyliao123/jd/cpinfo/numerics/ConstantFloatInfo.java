package tk.jackyliao123.jd.cpinfo.numerics;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;

public class ConstantFloatInfo extends ConstantPoolInfo {
    public final float value;
    public ConstantFloatInfo(float value) {
        super(FLOAT);
        this.value = value;
    }
}
