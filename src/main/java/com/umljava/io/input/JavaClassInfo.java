package com.umljava.io.input;

import java.util.HashSet;
import java.util.Set;

public class JavaClassInfo {

	private final String fullName;
	private final String baseClass;
	private final boolean isInterface;
	private final Set<String> implementList = new HashSet<String>();
	private final Set<String> calledClasses = new HashSet<String>();
	private final Set<String> fieldClasses = new HashSet<String>();

	public JavaClassInfo(String fullName) {
		this(false, fullName, null);
	}
	
	public JavaClassInfo(boolean isInterface, String fullName, String baseClass) {
		super();
		
		if (fullName == null)  throw new IllegalArgumentException("class full name can not be null");
	
		this.isInterface = isInterface;
		this.fullName = fullName;
		this.baseClass = baseClass;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public String getFullName() {
		return fullName;
	}

	public String getBaseClass() {
		return baseClass;
	}

	public Set<String> getImplementList() {
		return implementList;
	}

	public Set<String> getCalledClasses() {
		return calledClasses;
	}

	public Set<String> getFieldClasses() {
		return fieldClasses;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JavaClassInfo) {
			JavaClassInfo other = (JavaClassInfo) obj;
			return this.fullName.equals(other.fullName);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.fullName.hashCode();
	}
	
}
