package com.umljava.io.input;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.umljava.Configuration;

public class JarJavaClassRepositoryReader implements JavaClassRepositoryReader {

	@Override
	public Set<JavaClassInfo> read(Configuration config, File fileOrFolder) throws IOException {

		Set<JavaClassInfo> classes = new HashSet<JavaClassInfo>();
		
		JarFile jf = new JarFile(fileOrFolder);
		
		JavaClassParser parser = JavaClassParserFactory.getInstance().createParser("bcel");
		
		for (Enumeration<JarEntry> e = jf.entries(); e.hasMoreElements();) {
		       JarEntry entry = e.nextElement();
		       if (entry.getName().endsWith(".class")) {
		    	   JavaClassInfo clazz = parser.parse(config, jf.getInputStream(entry));
		    	   if (config.include(clazz.getFullName())) {
		    		   classes.add(clazz);
		    	   }
		       }
		}
		
		return classes;
	}

}
