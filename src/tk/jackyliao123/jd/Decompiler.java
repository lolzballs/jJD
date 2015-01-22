package tk.jackyliao123.jd;

import java.io.File;
import java.io.FileInputStream;

public class Decompiler {
	public static void main(String[] args) throws Throwable {
		ClassFile reader = new ClassFile(new FileInputStream(new File("./out/production/jJD/tk/jackyliao123/jd/Test.class")));
//		ClassFile reader = new ClassFile(new FileInputStream(new File("./out/production/jJD/tk/jackyliao123/jd/ClassFile.class")));
//		ClassFile reader = new ClassFile(new FileInputStream(new File("./res/blm.class")));
		try {
			reader.loadClass();
//			Disassembler disassembler = new Disassembler(reader);
//			disassembler.disassemble();
		}
		catch(Exception e){
			System.out.println(e.toString());
			for(StackTraceElement element : e.getStackTrace()){
				System.out.println("\tat " + element);
			}
		}
	}
}
