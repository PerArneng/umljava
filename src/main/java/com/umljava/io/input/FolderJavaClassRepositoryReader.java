package com.umljava.io.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.umljava.Configuration;

public class FolderJavaClassRepositoryReader implements JavaClassRepositoryReader {
	
	@Override
	public Set<JavaClassInfo> read(Configuration config, File fileOrFolder) throws IOException {

		JavaClassParser parser = JavaClassParserFactory.getInstance().createParser("bcel");		
		Set<JavaClassInfo> classes = new HashSet<JavaClassInfo>();
		readFilesRecursive(parser, config, fileOrFolder, classes);
		return classes;
	}
	
	private void readFilesRecursive(JavaClassParser parser, Configuration config, File fileOrFolder, Set<JavaClassInfo> classes) throws FileNotFoundException, IOException {
		
		if (fileOrFolder.isFile()) {
			if (fileOrFolder.getName().endsWith(".class")) {
				JavaClassInfo clazz = parser.parse(config, new FileInputStream(fileOrFolder));
				if (config.include(clazz.getFullName())) {
					classes.add(clazz);
				}
			}
		} else {
			for (File file : fileOrFolder.listFiles()) {
				readFilesRecursive(parser, config, file, classes);
			}
		}
	}

}
