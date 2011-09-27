package com.umljava;

import java.util.HashSet;
import java.util.Set;

public class JavaClass {

	private final String fullName;
	private final JavaClass baseClass;
	private final Set<JavaClass> implementList = new HashSet<JavaClass>();
	private final Set<JavaClass> calledClasses = new HashSet<JavaClass>();
	private final Set<JavaClass> fieldClasses = new HashSet<JavaClass>();
		
	public JavaClass(String fullName) {
		this(fullName, null);
	}

	public JavaClass(String fullName, JavaClass baseClass) {
		super();
		
		if (fullName == null)  throw new IllegalArgumentException("class full name can not be null");
		
		this.fullName = fullName;
		this.baseClass = baseClass;
	}

	public String getFullName() {
		return fullName;
	}

	public JavaClass getBaseClass() {
		return baseClass;
	}

	public Set<JavaClass> getImplementList() {
		return implementList;
	}

	public Set<JavaClass> getCalledClasses() {
		return calledClasses;
	}

	public Set<JavaClass> getFieldClasses() {
		return fieldClasses;
	}
	
}
