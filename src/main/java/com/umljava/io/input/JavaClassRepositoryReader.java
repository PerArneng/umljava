package com.umljava.io.input;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.umljava.Configuration;

public interface JavaClassRepositoryReader {

	Set<JavaClassInfo> read(Configuration config, File fileOrFolder) throws IOException;
	
}
