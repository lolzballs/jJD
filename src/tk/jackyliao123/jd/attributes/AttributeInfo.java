package tk.jackyliao123.jd.attributes;

import tk.jackyliao123.jd.cpinfo.ConstantPoolInfo;
import tk.jackyliao123.jd.cpinfo.ConstantUTF8Info;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class AttributeInfo {
    public static final String CONSTANT_VALUE = "ConstantValue";
    public static final String CODE = "Code";
    public static final String STACK_MAP_TABLE = "StackMapTable";
    public static final String EXCEPTIONS = "Exceptions";
    public static final String INNER_CLASSES = "InnerClasses";
    public static final String ENCLOSING_METHOD = "EnclosingMethod";
    public static final String SYNTHETIC = "Synthetic";
    public static final String SIGNATURE = "Signature";
    public static final String SOURCE_FILE = "SourceFile";
    public static final String SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    public static final String LINE_NUMBER_TABLE = "LineNumberTable";
    public static final String LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    public static final String LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    public static final String DEPRECATED = "Deprecated";
    public static final String RUNTIME_VISIBLE_ANNOTATIONS = "RuntimeVisibleAnnotations";
    public static final String RUNTIME_INVISIBLE_ANNOTATIONS = "RuntimeInvisibleAnnotations";
    public static final String RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS = "RuntimeVisibleParameterAnnotations";
    public static final String RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS = "RuntimeInvisibleParameterAnnotations";
    public static final String ANNOTATION_DEFAULT = "AnnotationDefault";
    public static final String BOOTSTRAP_METHODS = "BootstrapMethods";

    public final int attributeNameIndex;
    public final int attributeLength;
    public AttributeInfo(int attributeNameIndex, int attributeLength) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
    }

    public static AttributeInfo[] getAttributeInfo(ConstantPoolInfo[] constantPool, DataInputStream input, int attributeCount) throws IOException {
        AttributeInfo[] attributes = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            int attributeNameIndex = input.readUnsignedShort();
            int attributeLength = input.readInt(); // TODO: Make this not crash for values larger than 2^31
            switch (((ConstantUTF8Info) constantPool[attributeNameIndex - 1]).string) {
                case CONSTANT_VALUE:
                    assert attributeLength != 2;
                    attributes[i] = new AttributeConstantValue(attributeNameIndex, input.readUnsignedShort());
                    break;
                case CODE:
                    attributes[i] = AttributeCode.getAttributeCode(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case STACK_MAP_TABLE:
                    System.err.println("Unimplemented: " + STACK_MAP_TABLE);
                    input.skipBytes(attributeLength);
                    break;
                case EXCEPTIONS:
                    attributes[i] = AttributeExceptions.getAttributeExceptions(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case INNER_CLASSES:
                    attributes[i] = AttributeInnerClass.getAttributeInnerClass(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case ENCLOSING_METHOD:
                    attributes[i] = new AttributeEnclosingMethod(attributeNameIndex, input.readUnsignedShort(), input.readUnsignedShort());
                    break;
                case SYNTHETIC:
                    attributes[i] = new AttributeSynthetic(attributeNameIndex);
                    break;
                case SIGNATURE:
                    attributes[i] = new AttributeSignature(attributeNameIndex, input.readUnsignedShort());
                    break;
                case SOURCE_FILE:
                    attributes[i] = new AttributeSourceFile(attributeNameIndex, input.readUnsignedShort());
                    break;
                case SOURCE_DEBUG_EXTENSION:
                    attributes[i] = AttributeSourceDebugExtension.getAttributeSourceDebugExtension(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case LINE_NUMBER_TABLE:
                    attributes[i] = AttributeLineNumberTable.getAttributeLineNumberTable(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case LOCAL_VARIABLE_TABLE:
                    attributes[i] = AttributeLocalVariableTable.getAttributeLocalVariableTable(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case LOCAL_VARIABLE_TYPE_TABLE:
                    attributes[i] = AttributeLocalVariableTypeTable.getAttributeLocalVariableTypeTable(constantPool, input, attributeNameIndex, attributeLength);
                    break;
                case DEPRECATED:
                    attributes[i] = new AttributeDeprecated(attributeNameIndex);
                    break;
                case RUNTIME_VISIBLE_ANNOTATIONS:
                    System.err.println("Unimplemented: " + RUNTIME_VISIBLE_ANNOTATIONS);
                    input.skipBytes(attributeLength);
                    break;
                case RUNTIME_INVISIBLE_ANNOTATIONS:
                    System.err.println("Unimplemented: " + RUNTIME_INVISIBLE_ANNOTATIONS);
                    input.skipBytes(attributeLength);
                    break;
                case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
                    System.err.println("Unimplemented: " + RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS);
                    input.skipBytes(attributeLength);
                    break;
                case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
                    System.err.println("Unimplemented: " + RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS);
                    input.skipBytes(attributeLength);
                    break;
                case ANNOTATION_DEFAULT:
                    System.err.println("Unimplemented: " + ANNOTATION_DEFAULT);
                    input.skipBytes(attributeLength);
                    break;
                case BOOTSTRAP_METHODS:
                    System.err.println("Unimplemented: " + BOOTSTRAP_METHODS);
                    input.skipBytes(attributeLength);
                    break;
                default:
                    System.out.println("ERROR: " + ((ConstantUTF8Info) constantPool[attributeNameIndex - 1]).string);
            }
        }
        return attributes;
    }
}
