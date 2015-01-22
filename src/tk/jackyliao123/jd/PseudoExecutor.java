package tk.jackyliao123.jd;

import tk.jackyliao123.jd.attributes.AttributeCode;
import tk.jackyliao123.jd.attributes.AttributeInfo;

import java.util.Stack;

public class PseudoExecutor {
    private ClassFile clazz;
    public PseudoExecutor(ClassFile classFile){
        this.clazz = classFile;
    }
    public void startPseudoExecution(){
        for(MethodInfo method : clazz.methods){
//            test(method);
            PseudoMethodExecutor executor = new PseudoMethodExecutor(clazz, method);
            executor.pseudoExecuteMethod();
        }
    }

    private void test(MethodInfo method) {
        Stack<OperandStackItem> stack = new Stack<>();
        for (AttributeInfo attribute : method.attributes) {
            if (attribute instanceof AttributeCode) {
                byte[] code = ((AttributeCode) attribute).code;
                for (int i = 0; i < code.length; i++) {
                    Opcode opcode = Opcode.getOpcode(code[i]);
                    if (opcode == Opcode.GOTO) {
                        i += pseudoExecuteGOTO(stack, code, i);
                    }
                }
            }
        }
    }

    private int pseudoExecuteGOTO(Stack<OperandStackItem> stack, byte[] code, int i) {
        int gotoLocation = Util.bytesToSignedShort(code[i + 1], code[i + 2]) + i + i - 1;
        i += 2;
        int branchTo = Util.bytesToSignedShort(code[gotoLocation + 1], code[gotoLocation + 2]) + gotoLocation;
        i += 2;
        Opcode opIf = Opcode.getOpcode(code[gotoLocation]);
        switch (opIf) {
            case IFNE:
                System.out.println("");
                System.out.println(branchTo);
                break;
            default:
                break;
        }
//        System.out.println(branchTo);
        return i - 1;
    }

    public String getLocalVariableName(MethodInfo m, int n, int offset){
        return "__local" + n;
    }
}
