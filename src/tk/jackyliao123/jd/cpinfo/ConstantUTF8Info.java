package tk.jackyliao123.jd.cpinfo;

import java.io.UnsupportedEncodingException;

public class ConstantUTF8Info extends ConstantPoolInfo {
    public final String string;
    public ConstantUTF8Info(byte[] string) {
        super(UTF_8);
        String string1;
        try {
            string1 = new String(string, "UTF-8");
        }
        catch(UnsupportedEncodingException e){
            string1 = new String(string);
        }
        this.string = string1;
    }
}
