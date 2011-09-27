package com.umljava.io.input;

import java.io.IOException;
import java.io.InputStream;

import com.umljava.Configuration;

public interface JavaClassParser {

	JavaClassInfo parse(Configuration config, InputStream in) throws IOException;
	
}
