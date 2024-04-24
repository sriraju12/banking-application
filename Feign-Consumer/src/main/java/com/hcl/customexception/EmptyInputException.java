package com.hcl.customexception;

public class EmptyInputException extends RuntimeException {

	public EmptyInputException(String str) {

		super(str);
	}

}
