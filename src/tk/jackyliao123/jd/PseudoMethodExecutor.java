package tk.jackyliao123.jd;

import tk.jackyliao123.jd.attributes.AttributeCode;
import tk.jackyliao123.jd.attributes.AttributeInfo;
import tk.jackyliao123.jd.cpinfo.ConstantNameAndTypeInfo;
import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;
import tk.jackyliao123.jd.cpinfo.ConstantStringInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantDoubleInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantFloatInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantIntegerInfo;
import tk.jackyliao123.jd.cpinfo.numerics.ConstantLongInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantFieldReferenceInfo;
import tk.jackyliao123.jd.cpinfo.reference.ConstantMethodReferenceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class PseudoMethodExecutor{
	public final ClassFile clazz;
	public final MethodInfo method;
	private int k;
	private Stack<OperandStackItem> stack;
	private boolean[] variables;
	public PseudoMethodExecutor(ClassFile clazz, MethodInfo method){
		this.method = method;
		this.clazz = clazz;
		stack = new Stack<>();
	}
	public void pseudoExecuteMethod(){
		for(AttributeInfo info : method.attributes){
			if(info instanceof AttributeCode){
				AttributeCode codeInfo = (AttributeCode) info;
				byte[] code = codeInfo.code;
				variables = new boolean[codeInfo.maxLocals];
				for (k = 0; k < code.length; k++) {
					int start = k;
					Opcode opcode = Opcode.getOpcode(code[k]);
					switch (opcode) {
						case NOP:
							System.out.println("NOP NOP NOP!!! WHAT SHOULD I DO??? NOP NOP NOP...");
							break;
						case ACONST_NULL:
							stack.push(new OperandStackItem("null", "Ljava/lang/Object;"));
							break;
						case ICONST_M1:
							stack.push(new OperandStackItem(-1));
							break;
						case ICONST_0:
							stack.push(new OperandStackItem(0));
							break;
						case ICONST_1:
							stack.push(new OperandStackItem(1));
							break;
						case ICONST_2:
							stack.push(new OperandStackItem(2));
							break;
						case ICONST_3:
							stack.push(new OperandStackItem(3));
							break;
						case ICONST_4:
							stack.push(new OperandStackItem(4));
							break;
						case ICONST_5:
							stack.push(new OperandStackItem(5));
							break;
						case LCONST_0:
							stack.push(new OperandStackItem(0L));
							break;
						case LCONST_1:
							stack.push(new OperandStackItem(1L));
							break;
						case FCONST_0:
							stack.push(new OperandStackItem(0.0f));
							break;
						case FCONST_1:
							stack.push(new OperandStackItem(1.0f));
							break;
						case FCONST_2:
							stack.push(new OperandStackItem(2.0f));
							break;
						case DCONST_0:
							stack.push(new OperandStackItem(0.0d));
							break;
						case DCONST_1:
							stack.push(new OperandStackItem(1.0f));
							break;
						case BIPUSH:
							stack.push(new OperandStackItem((int)code[k + 1]));
							k++;
							break;
						case SIPUSH:
							stack.push(new OperandStackItem((int)Util.bytesToSignedShort(code[k + 1], code[k + 2])));
							k += 2;
							break;
						case LDC:
							int index = Util.b2ub(code[k+1]);
							ConstantPoolInfo constant = clazz.getConstant(index);
							if (constant instanceof ConstantStringInfo) {
								stack.push(new OperandStackItem("\"" + escapeString(clazz.getConstantUTF8(((ConstantStringInfo) constant).stringIndex).string) + "\"", "Ljava/lang/String;"));
							} else if (constant instanceof ConstantIntegerInfo) {
								stack.push(new OperandStackItem(((ConstantIntegerInfo) constant).value));
							} else if (constant instanceof ConstantFloatInfo) {
								stack.push(new OperandStackItem(((ConstantFloatInfo) constant).value));
							} else {
								System.err.println("[CRITICAL] LDC cannot take anything other than a string, integer or float.");
							}
							k++;
							break;
						case LDC_W:
							index = Util.b2us(code[k + 1], code[k + 2]);
							constant = clazz.getConstant(index);
							if (constant instanceof ConstantStringInfo) {
								stack.push(new OperandStackItem("\"" + escapeString(clazz.getConstantUTF8(((ConstantStringInfo) constant).stringIndex).string) + "\"", "Ljava/lang/String;"));
							} else if (constant instanceof ConstantIntegerInfo) {
								stack.push(new OperandStackItem(((ConstantIntegerInfo) constant).value));
							} else if (constant instanceof ConstantFloatInfo) {
								stack.push(new OperandStackItem(((ConstantFloatInfo) constant).value));
							} else {
								System.err.println("[CRITICAL] LDC_W cannot take anything other than a string, integer or float.");
							}
							k +=2;
							break;
						case LDC2_W:
							index = Util.b2us(code[k + 1], code[k + 2]);
							constant = clazz.getConstant(index);
							if (constant instanceof ConstantLongInfo) {
								stack.push(new OperandStackItem(((ConstantLongInfo) constant).value));
							} else if (constant instanceof ConstantDoubleInfo) {
								stack.push(new OperandStackItem(((ConstantDoubleInfo) constant).value));
							} else {
								System.err.println("[CRITICAL] LDC2_W cannot take anything other than a long or a double.");
							}
							k +=2;
							break;
						case ILOAD:
							load(Util.b2ub(code[k + 1]), "int");
							k++;
							break;
						case LLOAD:
							load(Util.b2ub(code[k + 1]), "long");
							k++;
							break;
						case FLOAD:
							load(Util.b2ub(code[k + 1]), "float");
							k++;
							break;
						case DLOAD:
                            load(Util.b2ub(code[k + 1]), "double");
							k++;
							break;
						case ALOAD:
							load(Util.b2ub(code[k + 1]), "toBeImplemented");
							k++;
							break;
						case ILOAD_0:
							load(0, "int");
							break;
						case ILOAD_1:
							load(1, "int");
							break;
						case ILOAD_2:
							load(2, "int");
							break;
						case ILOAD_3:
							load(3, "int");
							break;
						case LLOAD_0:
                            load(0, "long");
							break;
						case LLOAD_1:
                            load(1, "long");
							break;
						case LLOAD_2:
                            load(2, "long");
							break;
						case LLOAD_3:
                            load(3, "long");
							break;
						case FLOAD_0:
							load(0, "float");
							break;
						case FLOAD_1:
							load(1, "float");
							break;
						case FLOAD_2:
							load(2, "float");
							break;
						case FLOAD_3:
							load(3, "float");
							break;
						case DLOAD_0:
                            load(0, "double");
							break;
						case DLOAD_1:
                            load(1, "double");
							break;
						case DLOAD_2:
                            load(2, "double");
							break;
						case DLOAD_3:
                            load(3, "double");
							break;
						case ALOAD_0:
							load(0, "toBeImplemented");
							break;
						case ALOAD_1:
							load(1, "toBeImplemented");
							break;
						case ALOAD_2:
							load(2, "toBeImplemented");
							break;
						case ALOAD_3:
							load(3, "toBeImplemented");
							break;
						case IALOAD:
							arrayLoad();
							break;
						case LALOAD:
                            arrayLoad();
							break;
						case FALOAD:
							arrayLoad();
							break;
						case DALOAD:
                            arrayLoad();
							break;
						case AALOAD:
							arrayLoad();
							break;
						case BALOAD:
							arrayLoad();
							break;
						case CALOAD:
							arrayLoad();
							break;
						case SALOAD:
							arrayLoad();
							break;
						case ISTORE:
							store(Util.b2ub(code[k + 1]));
							k++;
							break;
						case LSTORE:
                            store(Util.b2ub(code[k + 1]));
							k++;
							break;
						case FSTORE:
							store(Util.b2ub(code[k + 1]));
							k++;
							break;
						case DSTORE:
                            store(Util.b2ub(code[k + 1]));
							k++;
							break;
						case ASTORE:
							store(Util.b2ub(code[k + 1]));
							k++;
							break;
						case ISTORE_0:
							store(0);
							break;
						case ISTORE_1:
							store(1);
							break;
						case ISTORE_2:
							store(2);
							break;
						case ISTORE_3:
							store(3);
							break;
						case LSTORE_0:
                            store(0);
							break;
						case LSTORE_1:
                            store(1);
							break;
						case LSTORE_2:
                            store(2);
							break;
						case LSTORE_3:
                            store(3);
							break;
						case FSTORE_0:
							store(0);
							break;
						case FSTORE_1:
							store(1);
							break;
						case FSTORE_2:
							store(2);
							break;
						case FSTORE_3:
							store(3);
							break;
						case DSTORE_0:
                            store(0);
							break;
						case DSTORE_1:
                            store(1);
							break;
						case DSTORE_2:
                            store(2);
							break;
						case DSTORE_3:
							store(3);
							break;
						case ASTORE_0:
							store(0);
							break;
						case ASTORE_1:
							store(1);
							break;
						case ASTORE_2:
							store(2);
							break;
						case ASTORE_3:
							store(3);
							break;
                        case IASTORE:
							arrayStore();
							break;
                        case LASTORE:
                            arrayStore();
                            break;
                        case FASTORE:
							arrayStore();
                            break;
                        case DASTORE:
							arrayStore();
                            break;
                        case AASTORE:
							arrayStore();
                            break;
                        case BASTORE:
							arrayStore();
                            break;
                        case CASTORE:
							arrayStore();
                            break;
                        case SASTORE:
							arrayStore();
                            break;
                        case POP:
                            System.out.println(stack.pop().stringItem);
                            break;
                        case POP2:
                            stack.pop();
                            stack.pop();
                            break;
                        case DUP:
                            stack.push(stack.peek());
                            break;
                        case DUP_X1:
                            OperandStackItem value1 = stack.pop();
                            OperandStackItem value2 = stack.pop();
                            stack.push(value1);
                            stack.push(value2);
                            stack.push(value1);
                            break;
                        case DUP_X2:
                            value1 = stack.pop();
                            value2 = stack.pop();
                            if (!value2.category2) {
                                stack.push(value1);
                                stack.push(value2);
                                stack.push(value1);
                            } else {
                                OperandStackItem value3 = stack.pop();
                                stack.push(value1);
                                stack.push(value3);
                                stack.push(value2);
                                stack.push(value1);
                            }
                            break;
                        case DUP2:
                            value1 = stack.pop();
                            if (value1.category2) {
                                stack.push(value1);
                                stack.push(value1);
                            } else {
                                value2 = stack.pop();
                                stack.push(value2);
                                stack.push(value1);
                                stack.push(value2);
                                stack.push(value1);
                            }
                            break;
                        case DUP2_X1:
                            value1 = stack.pop();
                            if (value1.category2) {
                                value2 = stack.pop();
                                stack.push(value1);
                                stack.push(value2);
                                stack.push(value1);
                            } else {
                                value2 = stack.pop();
                                OperandStackItem value3 = stack.pop();
                                stack.push(value2);
                                stack.push(value1);
                                stack.push(value3);
                                stack.push(value2);
                                stack.push(value1);
                            }
                            break;
                        case DUP2_X2:
                            value1 = stack.pop();
                            value2 = stack.pop();
                            if (value1.category2) {
                                if (value2.category2) { // Form 4
                                    stack.push(value1);
                                    stack.push(value2);
                                    stack.push(value1);
                                } else { // Form 2
                                    OperandStackItem value3 = stack.pop();
                                    stack.push(value1);
                                    stack.push(value3);
                                    stack.push(value2);
                                    stack.push(value1);
                                }
                            } else {
                                OperandStackItem value3 = stack.pop();
                                if (value3.category2) { // Form 3
                                    stack.push(value2);
                                    stack.push(value1);
                                    stack.push(value3);
                                    stack.push(value2);
                                    stack.push(value1);
                                } else { // Form 1
                                    OperandStackItem value4 = stack.pop();
                                    stack.push(value2);
                                    stack.push(value1);
                                    stack.push(value4);
                                    stack.push(value3);
                                    stack.push(value2);
                                    stack.push(value1);
                                }
                            }
                            break;
                        case SWAP:
							value1 = stack.pop();
                            value2 = stack.pop();
                            stack.push(value1);
                            stack.push(value2);
                            break;
                        case IADD:
							arithmetic("I", "+");
                            break;
                        case LADD:
							arithmetic("J", "+");
                            break;
                        case FADD:
							arithmetic("F", "+");
                            break;
                        case DADD:
							arithmetic("D", "+");
                            break;
                        case ISUB:
							arithmetic("I", "-");
                            break;
                        case LSUB:
							arithmetic("J", "-");
                            break;
                        case FSUB:
							arithmetic("F", "-");
                            break;
                        case DSUB:
							arithmetic("D", "-");
                            break;
                        case IMUL:
							arithmetic("I", "*");
                            break;
                        case LMUL:
							arithmetic("J", "*");
                            break;
                        case FMUL:
							arithmetic("F", "*");
                            break;
                        case DMUL:
							arithmetic("D", "*");
                            break;
                        case IDIV:
							arithmetic("I", "/");
                            break;
                        case LDIV:
							arithmetic("J", "/");
                            break;
                        case FDIV:
							arithmetic("F", "/");
                            break;
                        case DDIV:
							arithmetic("D", "/");
                            break;
                        case IREM:
							arithmetic("I", "%");
                            break;
                        case LREM:
							arithmetic("J", "%");
                            break;
                        case FREM:
							arithmetic("F", "%");
                            break;
                        case DREM:
							arithmetic("D", "%");
                            break;
                        case INEG:
                            value1 = stack.pop();
                            stack.push(new OperandStackItem("-" + value1.stringItem, "I"));
                            break;
                        case LNEG:
                            value1 = stack.pop();
                            stack.push(new OperandStackItem("-" + value1.stringItem, "J"));
                            break;
                        case FNEG:
                            value1 = stack.pop();
                            stack.push(new OperandStackItem("-" + value1.stringItem, "F"));
                            break;
                        case DNEG:
                            value1 = stack.pop();
                            stack.push(new OperandStackItem("-" + value1.stringItem, "D"));
                            break;
                        case ISHL:
							arithmetic("I", "<<");
                            break;
                        case LSHL:
							arithmetic("J", "<<");
                            break;
                        case ISHR:
							arithmetic("I", ">>");
                            break;
                        case LSHR:
							arithmetic("J", ">>");
                            break;
                        case IUSHR:
							arithmetic("I", ">>");
                            break;
                        case LUSHR:
							arithmetic("J", ">>");
                            break;
                        case IAND:
							arithmetic("I", "&");
                            break;
                        case LAND:
							arithmetic("J", "&");
                            break;
                        case IOR:
							arithmetic("I", "|");
                            break;
                        case LOR:
							arithmetic("J", "|");
                            break;
                        case IXOR:
							arithmetic("I", "^");
                            break;
                        case LXOR:
							arithmetic("J", "^");
                            break;
                        case IINC:
                            int incrementBy = code[k + 2];
                            System.out.println(getLVarName(Util.b2ub(code[k + 1])) + (incrementBy >= 0 ? " += " : " -= ") + Math.abs(incrementBy) + ";");
							k += 2;
							break;
						case I2L:
							cast("long");
							break;
						case I2F:
							cast("float");
							break;
						case I2D:
							cast("double");
							break;
						case L2I:
							cast("int");
							break;
						case L2F:
							cast("float");
							break;
						case L2D:
							cast("double");
							break;
						case F2I:
							cast("int");
							break;
						case F2L:
							cast("long");
							break;
						case F2D:
							cast("double");
							break;
						case D2I:
							cast("int");
							break;
						case D2L:
							cast("long");
							break;
						case D2F:
							cast("float");
							break;
						case I2B:
							cast("byte");
							break;
						case I2C:
							cast("char");
							break;
						case I2S:
							cast("short");
							break;
						case IFEQ:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IFNE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IFLT:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IFGE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IFGT:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IFLE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPEQ:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPNE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPLT:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPGE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPGT:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ICMPLE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ACMPEQ:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case IF_ACMPNE:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case GOTO:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]) + k;
							System.out.println("\t\t\t" + (index + start));
							k +=2;
							break;
						case JSR:
							index = Util.bytesToSignedShort(code[k + 1], code[k + 2]);
							System.out.println("\t\t\t" + (index + start));
							k +=2;
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
								int keyOffset = Util.getInt(code, this.k);
								this.k += 4;
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
								int key = Util.getInt(code, this.k);
								this.k += 4;
								int keyOffset = Util.getInt(code, this.k);
								this.k += 4;
								keyToByteLocation.put(key, keyOffset + start);
								System.out.println("\t\t\t" + key + ":" + (keyOffset + start));
							}
							System.out.println("\t\t\tdefault:" + (defaultOffset + start));
							System.out.println("}");
							--k;
							break;
						case GETSTATIC:
							index = Util.b2us(code[k + 1], code[k + 2]);
                            ConstantFieldReferenceInfo field = (ConstantFieldReferenceInfo) clazz.getConstant(index);
                            ConstantNameAndTypeInfo nameAndType = clazz.getConstantNameAndTypeInfo(field.nameAndTypeIndex);
                            String fieldName = clazz.getConstantUTF8(nameAndType.nameIndex).string;
                            String fieldType = clazz.getConstantUTF8(nameAndType.descriptorIndex).string;
                            String fieldClass = clazz.getConstantClassName(field.classIndex);
                            stack.push(new OperandStackItem(fieldClass.replace('/', '.') + "." + fieldName, fieldType));
							k += 2;
							break;
						case PUTSTATIC:
							index = Util.b2us(code[k + 1], code[k + 2]);
							System.out.print(" #" + index);
							System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
							k += 2;
							break;
						case GETFIELD:
                            index = Util.b2us(code[k + 1], code[k + 2]);
                            field = (ConstantFieldReferenceInfo) clazz.getConstant(index);
                            nameAndType = (ConstantNameAndTypeInfo) clazz.getConstant(field.nameAndTypeIndex);
                            fieldName = clazz.getConstantUTF8(nameAndType.nameIndex).string;
                            fieldType = clazz.getConstantUTF8(nameAndType.descriptorIndex).string;
                            fieldClass = clazz.getConstantClassName(field.classIndex);
                            stack.push(new OperandStackItem(fieldClass.replace('/', '.') + "." + fieldName, fieldType));
							k += 2;
							break;
						case PUTFIELD:
							index = Util.b2us(code[k + 1], code[k + 2]);
							System.out.print(" #" + index);
							System.out.println("\t\t\t// " + clazz.getConstantFieldSignature(index));
							k += 2;
							break;
						case INVOKEVIRTUAL:
                            index = Util.b2us(code[k + 1], code[k + 2]);
                            String[] method = invokeMethod(index);
                            if (method[2].equals("V")) {
                                System.out.println(stack.pop().stringItem + "." + method[1] + ";");
                            } else {
                                stack.push(new OperandStackItem(stack.pop().stringItem + "." + method[1], method[2]));
                            }
							k += 2;
							break;
						case INVOKESPECIAL:
                            index = Util.b2us(code[k + 1], code[k + 2]);
                            method = invokeMethod(index);
                            System.out.println(stack.pop().stringItem + method[1] + ";");
                            k += 2;
							break;
						case INVOKESTATIC:
							index = Util.b2us(code[k + 1], code[k + 2]);
                            method = invokeMethod(index);
                            if (method[2].equals("V")) {
                                System.out.println(method[0] + "." + method[1] + ";");
                            } else {
                                stack.push(new OperandStackItem(method[0] + "." + method[1], method[2]));
                            }
							k += 2;
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
							k +=2;
							break;
						case NEWARRAY:
							index = Util.b2ub(code[k + 1]);
                            String arrayLength = stack.pop().stringItem;
							String s = "new ";
                            String type;
                            switch (index) {
								case 4:
									s += "boolean[" + arrayLength + "]";
                                    type = "Z";
									break;
								case 5:
									s += "char[" + arrayLength + "]";
                                    type = "C";
                                    break;
								case 6:
									s += "float[" + arrayLength + "]";
                                    type = "F";
									break;
								case 7:
									s += "double[" + arrayLength + "]";
                                    type = "D";
									break;
								case 8:
                                    s += "byte[" + arrayLength + "]";
                                    type = "B";
									break;
								case 9:
                                    s += "short[" + arrayLength + "]";
                                    type = "S";
									break;
								case 10:
                                    s += "int[" + arrayLength + "]";
                                    type = "I";
									break;
								case 11:
                                    s += "long[" + arrayLength + "]";
                                    type = "J";
									break;
								default:
                                    s += "unknownPrimitive_" + index + "[" + arrayLength + "]";
                                    type = "unknownPrimitve";
									break;
							}
                            stack.push(new OperandStackItem(s, "[" + type));
							k++;
							break;
						case ANEWARRAY:
                            index = Util.b2us(code[k + 1], code[k + 2]);
                            arrayLength = stack.pop().stringItem;
                            String className = clazz.getConstantClassName(index);
                            stack.push(new OperandStackItem("new " + className.replace("/", ".") + "[" + arrayLength + "]", "[L" + className));
                            System.out.println();
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
				System.out.println("Method end");
			}
		}
	}
    public String primitive(String primitive) {
        switch (primitive) {
            case "boolean":
                return "Z";
            case "byte":
                return "B";
            case "char":
                return "C";
            case "double":
                return "D";
            case "float":
                return "F";
            case "int":
                return "I";
            case "long":
                return "J";
            case "short":
                return "S";
            default:
                return reference(primitive);
        }
    }
	private static String generateBrackets(int n){
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < n; ++i){
			b.append("[]");
		}
		return b.toString();
	}
    private String reversePrimitive(String encoded) {
	    int depthOfArray = 0;
		while(encoded.startsWith("[")){
			++depthOfArray;
			encoded = encoded.substring(1);
		}
	    if(encoded.startsWith("L")){
			return encoded.substring(1).replace('/', '.') + generateBrackets(depthOfArray);
	    }
        switch (encoded){
            case "Z":
                return "boolean" + generateBrackets(depthOfArray);
            case "B":
                return "byte" + generateBrackets(depthOfArray);
            case "C":
                return "char" + generateBrackets(depthOfArray);
            case "D":
                return "double" + generateBrackets(depthOfArray);
            case "F":
                return "float" + generateBrackets(depthOfArray);
            case "I":
                return "int" + generateBrackets(depthOfArray);
            case "J":
                return "long" + generateBrackets(depthOfArray);
            case "S":
                return "short" + generateBrackets(depthOfArray);
            case "V":
                return "void" + generateBrackets(depthOfArray);
            default:
                return encoded;
        }
    }
    private String reference(String fieldClass) {
        return "L" + fieldClass;
    }
    public String getLVarName(int index){
        if (index == 0) {
            return "this";
        }
		return "local_" + index;
	}
	public void load(int index, String type){
		stack.push(new OperandStackItem(getLVarName(index), primitive(type)));
	}
	public void arrayLoad(){
		OperandStackItem index = stack.pop();
        OperandStackItem array = stack.pop();
		stack.push(new OperandStackItem(array.stringItem + "[" + index.stringItem + "]", "[" + primitive(array.type)));
	}
	public void store(int index){
		OperandStackItem item = stack.pop();
		System.out.println(reversePrimitive(item.type) + " " + getLVarName(index) + " = " + item.stringItem + ";");
	}
	public void arrayStore(){
		String val = stack.pop().stringItem;
        String index = stack.pop().stringItem;
		String varLoc = stack.pop().stringItem;
		System.out.println(varLoc + "[" + index + "] = " + val + ";");
	}
	public void arithmetic(String type, String op) {
		OperandStackItem value2 = stack.pop();
		OperandStackItem value1 = stack.pop();
		stack.push(new OperandStackItem(value1.stringItem + " " + op + "" + value2.stringItem, type));
	}
	public void cast(String to){
		stack.push(new OperandStackItem("(" + to + ")(" + stack.pop().stringItem + ")", primitive(to)));
	}
    private String[] invokeMethod(int index) {
        ConstantMethodReferenceInfo method = (ConstantMethodReferenceInfo) clazz.getConstant(index);
        ConstantNameAndTypeInfo nameAndType = clazz.getConstantNameAndTypeInfo(method.nameAndTypeIndex);
        String methodClass = clazz.getConstantClassName(method.classIndex).replace('/', '.');
        String methodName = clazz.getConstantUTF8(nameAndType.nameIndex).string;
        if (methodName.equals("<init>")) {
            methodName = "";
        }

        String methodDescriptor = clazz.getConstantUTF8(nameAndType.descriptorIndex).string;
        ArrayList<String> methodArguments = new ArrayList<>();
        StringBuilder methodReturnType = new StringBuilder();
	    boolean beginParameter = false;
        boolean endParameter = false;
        boolean object = false;
        for (int i = 0; i < methodDescriptor.length(); ++i) {
            char c = methodDescriptor.charAt(i);
            if (object) {
                if (c == ';') {
                    object = false;
                } else if (endParameter) {
                    methodReturnType.append(c);
               }
            } else if (c == '(') {
	            beginParameter = true;
            } else if (c == ')') {
                if (!beginParameter) {
                    throw new RuntimeException("WTF is this crap WTF WTF!!!!! ERROR! CRITICAL ERROR! NO OPEN BRACKET! NOP NOP NOP! PLEASE WAIT WHILST THE SYSTEM CRASHES...");
                } else {
                    endParameter = true;
                }
            } else if (endParameter) {
                if (c == 'L') {
                    object = true;
                } else {
                    methodReturnType.append(c);
                }
            } else if (c == 'L') {
                object = true;
                methodArguments.add(stack.pop().stringItem);
            } else {
                methodArguments.add(stack.pop().stringItem);
            }
        }
        StringBuilder methodCall = new StringBuilder();
        int argumentLength = methodArguments.size();
        for (int i = 0; i < argumentLength; i++) {
            methodCall.append(methodArguments.get(i - argumentLength + 1));
            if (i != argumentLength - 1) {
                methodCall.append(", ");
            }
        }

        return new String[] {
                methodClass,
                methodName + "(" + methodCall + ")",
                methodReturnType.toString()
        };
    }
    public String escapeString(String s){
        return s;
	}
}
