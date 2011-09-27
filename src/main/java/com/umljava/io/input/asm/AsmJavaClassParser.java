package com.umljava.io.input.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.umljava.Configuration;
import com.umljava.io.input.JavaClassInfo;
import com.umljava.io.input.JavaClassParser;

public final class AsmJavaClassParser implements JavaClassParser {
	
	@SuppressWarnings("unchecked")
	@Override
	public JavaClassInfo parse(final Configuration config, InputStream in) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode cn = new ClassNode();
        cr.accept(cn, ClassReader.SKIP_DEBUG);

        String baseClass = toDotNotation(cn.superName);
        if (!config.include(baseClass)) {
        	baseClass = null;
        }
        
        final JavaClassInfo clazz = new JavaClassInfo(false, toDotNotation(cn.name), baseClass);
        
        List<String> interfaces = cn.interfaces;
        for (String intface : interfaces) {
        	String dotName = toDotNotation(intface);
        	if (config.include(dotName)) {
        		clazz.getImplementList().add(toDotNotation(intface));
        	}
        }
        
        List<FieldNode> fields = cn.fields;
        for (FieldNode field : fields) {
     	
        	if (field.signature != null) {
	        	SignatureReader sig = new SignatureReader(field.signature);
	        	sig.accept(new SignatureVisitor(Opcodes.ASM4) {
	        		
	        		@Override
	        		public void visitClassType(String classType) {
	        			String dotName = toDotNotation(classType);
	        			if (config.include(dotName)) {
	        				clazz.getFieldClasses().add(toDotNotation(classType));
	        			}
	        		}
	        		
	        	});
        	}
        	
        }
        
        List<MethodNode> methods = cn.methods;
        for (MethodNode m : methods) {
        	InsnList instructions = m.instructions;
        	for (int i=0;i<instructions.size();i++) {
        		AbstractInsnNode ins = instructions.get(i);
        		if (ins instanceof MethodInsnNode) {
        			MethodInsnNode mi = (MethodInsnNode) ins;
        			String dotName = toDotNotation(mi.owner);
        			if (config.include(dotName)) {
        				clazz.getCalledClasses().add(toDotNotation(mi.owner));
        			}
        		}
        	}
        	
        }
		
		return clazz;
	}
	
	private String toDotNotation(String className) {
		return className.replace('/', '.');
	}
	
}
