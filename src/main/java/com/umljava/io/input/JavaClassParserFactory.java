package com.umljava.io.input;

import java.util.HashMap;
import java.util.Map;

import com.umljava.io.input.asm.AsmJavaClassParser;
import com.umljava.io.input.bcel.BcelJavaClassParser;

public class JavaClassParserFactory {
	private static JavaClassParserFactory instance;
	
	private final Map<String, JavaClassParser> parsers = new HashMap<String, JavaClassParser>();
	
	private JavaClassParserFactory() { 
		parsers.put("asm", new AsmJavaClassParser());
		parsers.put("bcel", new BcelJavaClassParser());
	}
	
	public static JavaClassParserFactory getInstance() {
		if (instance == null) {
			instance = new JavaClassParserFactory();
		}
		
		return instance;
	}
	
	public JavaClassParser createParser(String impl) {
		JavaClassParser parser = this.parsers.get(impl);
		if (parser == null) {
			throw new IllegalArgumentException(impl + " is not a supported parser");
		}
		return parser;
	}
	
}
