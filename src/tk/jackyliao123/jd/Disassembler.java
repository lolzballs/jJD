package tk.jackyliao123.jd;

import tk.jackyliao123.jd.attributes.AttributeCode;
import tk.jackyliao123.jd.attributes.AttributeInfo;
import tk.jackyliao123.jd.cpinfo.ConstantClassInfo;
import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantIntegerInfo;

import java.util.HashMap;

public class Disassembler {
    private ClassFile clazz;
    public Disassembler(ClassFile clazz) {
        this.clazz = clazz;
    }
    public void disassemble() {
        for (MethodInfo method : clazz.methods) {
            AttributeInfo[] methodAttributes = method.attributes;
            for (AttributeInfo methodAttribute : methodAttributes) {
                System.out.println("Method: " + clazz.getConstantUTF8(method.nameIndex).string + " start");
                if (methodAttribute instanceof AttributeCode) {
                    byte[] code = ((AttributeCode) methodAttribute).code;
                    for (int k = 0; k < code.length; k++) {
                        int start = k;
                        Opcode opcode = Opcode.getOpcode(code[k]);
                        System.out.print(k + ": " + opcode + " ");
                        switch (opcode) {
                            case BIPUSH:
                                int index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case SIPUSH:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + index);
                                k += 2;
                                break;
                            case LDC:
                                index = Util.b2ub(code[k + 1]);
                                System.out.print(" #" + index);
                                ConstantPoolInfo constant = clazz.getConstant(index);
                                if (constant instanceof ConstantIntegerInfo) {
                                    System.out.println("\t\t\t// " + clazz.getConstantInteger(index).value);
                                } else if (constant instanceof ConstantClassInfo) {
                                    System.out.println("\t\t\t// " + clazz.getConstantUTF8(clazz.getConstantString(index).stringIndex).string);
                                } else {
                                    System.out.println();
                                }
                                k++;
                                break;
                            case LDC_W:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                constant = clazz.getConstant(index);
                                if (constant instanceof ConstantIntegerInfo) {
                                    System.out.println("\t\t// " + clazz.getConstantInteger(index).value);
                                } else if (constant instanceof ConstantClassInfo) {
                                    System.out.println("\t\t// " + clazz.getConstantUTF8(clazz.getConstantString(index).stringIndex).string);
                                } else {
                                    System.out.println();
                                }
                                k+=2;
                                break;
                            case LDC2_W:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                constant = clazz.getConstant(index);
                                if (constant instanceof ConstantIntegerInfo) {
                                    System.out.println("\t\t\t// " + clazz.getConstantInteger(index).value);
                                } else if (constant instanceof ConstantClassInfo) {
                                    System.out.println("\t\t\t// " + clazz.getConstantUTF8(clazz.getConstantString(index).stringIndex).string);
                                } else {
                                    System.out.println("\t\t\t//" + constant);
                                }
                                k+=2;
                                break;
                            case ILOAD:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case LLOAD:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case FLOAD:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case DLOAD:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case ALOAD:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case ISTORE:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case LSTORE:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case FSTORE:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case DSTORE:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case ASTORE:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                k++;
                                break;
                            case IINC:
                                index = Util.b2ub(code[k + 1]);
                                int constantNum = code[k + 2];
                                System.out.println("\t\t\t" + index + ", " + constantNum);
                                k += 2;
                                break;
                            case IFEQ:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IFNE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IFLT:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IFGE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IFGT:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IFLE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPEQ:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPNE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPLT:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPGE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPGT:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ICMPLE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ACMPEQ:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case IF_ACMPNE:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case GOTO:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]) + k;
                                System.out.println("\t\t\t" + index);
                                k+=2;
                                break;
                            case JSR:
                                index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t" + (index + start));
                                k+=2;
                                break;
                            case RET:
                                index = Util.b2ub(code[k + 1]);
                                System.out.println("\t\t\t" + index);
                                break;
                            case TABLESWITCH:
                                int offset = 3 - k % 4 + 1;
                                k += offset;
                                int defaultOffset = Util.getInt(code, k);
                                k += 4;
                                int low = Util.getInt(code, k);
                                k += 4;
                                int high = Util.getInt(code, k);
                                k += 4;
                                System.out.println("{ // " + low + " to " + high);
                                HashMap<Integer, Integer> keyToByteLocation = new HashMap<>();
                                for (int i = low; i <= high; i++) {
                                    int keyOffset = Util.getInt(code, k);
                                    k += 4;
                                    keyToByteLocation.put(i, keyOffset + start);
                                    System.out.println("\t\t\t" + i + ": " + (keyOffset + start));
                                }
                                System.out.println("\t\t\tdefault: " + (defaultOffset + start));
                                System.out.println("}");
                                --k;
                                break;
                            case LOOKUPSWITCH:
                                offset = 3 - k % 4 + 1;
                                k += offset;
                                defaultOffset = Util.getInt(code, k);
                                k += 4;
                                int npairs = Util.getInt(code, k);
                                k += 4;
                                System.out.println("{ // " + npairs);
                                keyToByteLocation = new HashMap<>();
                                for (int i = 0; i < npairs; i++) {
                                    int key = Util.getInt(code, k);
                                    k += 4;
                                    int keyOffset = Util.getInt(code, k);
                                    k += 4;
                                    keyToByteLocation.put(key, keyOffset + start);
                                    System.out.println("\t\t\t" + key + ":" + (keyOffset + start));
                                }
                                System.out.println("\t\t\tdefault:" + (defaultOffset + start));
                                System.out.println("}");
                                --k;
                                break;
                            case GETSTATIC:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
                                k+=2;
                                break;
                            case PUTSTATIC:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
                                k+=2;
                                break;
                            case GETFIELD:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
                                k+=2;
                                break;
                            case PUTFIELD:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
                                k+=2;
                                break;
                            case INVOKEVIRTUAL:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantMethodSignature(index));
                                k+=2;
                                break;
                            case INVOKESPECIAL:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantMethodSignature(index));
                                k+=2;
                                break;
                            case INVOKESTATIC:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantMethodSignature(index));
                                k+=2;
                                break;
                            case INVOKEINTERFACE:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                int count = Util.b2ub(code[k + 3]);
                                System.out.print(" #" + index + ", " + count);
                                k += 4;
//								System.out.println("\t\t\t Unimplemented!");
                                break;
                            case INVOKEDYNAMIC:
                                System.out.println("\t\t\t Unimplemented!");
                                break;
                            case NEW:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstant(index));
                                k+=2;
                                break;
                            case NEWARRAY:
                                index = Util.b2ub(code[k + 1]);
                                System.out.print("\t\t\t" + index);
                                switch (index) {
                                    case 4:
                                        System.out.println(" boolean");
                                        break;
                                    case 5:
                                        System.out.println(" char");
                                        break;
                                    case 6:
                                        System.out.println(" float");
                                        break;
                                    case 7:
                                        System.out.println(" double");
                                        break;
                                    case 8:
                                        System.out.println(" byte");
                                        break;
                                    case 9:
                                        System.out.println(" short");
                                        break;
                                    case 10:
                                        System.out.println(" int");
                                        break;
                                    case 11:
                                        System.out.println(" long");
                                        break;
                                    default:
                                        System.out.println(" Unknown primitive");
                                        break;
                                }
                                k++;
                                break;
                            case ANEWARRAY:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantClassName(index));
                                k += 2;
                                break;
                            case CHECKCAST:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantClassName(index));
                                k += 2;
                                break;
                            case INSTANCEOF:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.print(" #" + index);
                                System.out.println("\t\t\t// " + clazz.getConstantClassName(index));
                                k += 2;
                                break;
                            case WIDE:
                                System.out.println("\t\t\t Unimplemented!");
                                break;
                            case MULTIANEWARRAY:
                                System.out.println("\t\t\t Unimplemented!");
                                break;
                            case IFNULL:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t// " + index);
                                k += 2;
                                break;
                            case IFNONNULL:
                                index = Util.b2us(code[k + 1], code[k + 2]);
                                System.out.println("\t\t\t// " + index);
                                k += 2;
                                break;
                            case GOTO_W:
                                System.out.println("\t\t\t Unimplemented!");
                                break;
                            case JSR_W:
                                System.out.println("\t\t\t Unimplemented!");
                                break;
                            default:
                                System.out.println();
                                break;
                        }
                    }
                }
                System.out.println("Method end");
            }
        }
    }
}
