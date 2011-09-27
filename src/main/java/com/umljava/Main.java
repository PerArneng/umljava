package com.umljava;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.umljava.io.input.FolderJavaClassRepositoryReader;
import com.umljava.io.input.JarJavaClassRepositoryReader;
import com.umljava.io.input.JavaClassInfo;
import com.umljava.io.input.JavaClassRepositoryReader;

public class Main {

	public static void main(String[] args) throws IOException {
		
		if (args.length < 1) {
			System.err.println("umljava: need at least one argument");
			System.err.println("umljava: usage: umljava <jar files>");
			System.exit(1);
		}

		Configuration config = new Configuration() {
			
			@Override
			public boolean include(String fullName) {
				return fullName.startsWith("com.umljava");
			}

			@Override
			public String group(String fullName) {
				return null;
			}
			
		};
		
		Set<JavaClassInfo> classes = new HashSet<JavaClassInfo>();
		
		for (String fileName : args) {
			File file = new File(fileName);
			JavaClassRepositoryReader reader = null;
			if (file.isFile()) {
				if (file.getName().endsWith(".jar")) {
					reader = new JarJavaClassRepositoryReader();
				}
			} else {
				reader = new FolderJavaClassRepositoryReader();
			}
			if (reader != null) {
				Set<JavaClassInfo> jarClasses = reader.read(config, file);
				for (JavaClassInfo clazz : jarClasses) {
						classes.add(clazz);
				}
			}
		}
		
		for (JavaClassInfo clazz : classes) {
			printJavaClass(clazz);
		}
		
	}
	
	private final static void printJavaClass(JavaClassInfo clazz) {
		System.out.print("class " + clazz.getFullName());
		
		if (clazz.getBaseClass() != null) System.out.println(" extends " + clazz.getBaseClass());
		
		if (clazz.getImplementList().size() > 0) {
			System.out.print(" implements");
			for (String s : clazz.getImplementList()) {
				System.out.print(" " + s);
			}
		}
		System.out.println();
		
		for (String s : clazz.getFieldClasses()) {
			System.out.println("  field:" + s);
		}
		
		for (String s : clazz.getCalledClasses()) {
			System.out.println("  meth: " + s);
		}
	}
	
}
