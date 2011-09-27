package com.umljava.io.input.bcel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.InvokeInstruction;

import com.umljava.Configuration;
import com.umljava.io.input.JavaClassInfo;
import com.umljava.io.input.JavaClassParser;

public class BcelJavaClassParser implements JavaClassParser {

	@Override
	public JavaClassInfo parse(Configuration config, InputStream in) throws IOException {
		
		ClassParser classParser = new ClassParser(in, "dummy.class");
		JavaClass clazz = classParser.parse();
		
		JavaClassInfo classInfo = new JavaClassInfo(clazz.isInterface(), clazz.getClassName(), 
													clazz.getSuperclassName());
		
		for (String interf : clazz.getInterfaceNames()) {
			classInfo.getImplementList().add(interf);
		}
		
		for (Field field : clazz.getFields()) {
			classInfo.getFieldClasses().add(field.getType().getClass().getName());
		}
		ConstantPoolGen poolGen = new ConstantPoolGen(clazz.getConstantPool());
		
		for (Method m : clazz.getMethods()) {
			Code code = m.getCode();
			if (code != null) {
				InstructionList instrs = new InstructionList(code.getCode());
				for (Instruction instr : instrs.getInstructions()) {
					if (instr instanceof InvokeInstruction) {
						InvokeInstruction invokeInstr = (InvokeInstruction) instr;
						classInfo.getCalledClasses().add(invokeInstr.getType(poolGen).getSignature());
					}
				}
			}
		}
		
		return classInfo;
	}

}
