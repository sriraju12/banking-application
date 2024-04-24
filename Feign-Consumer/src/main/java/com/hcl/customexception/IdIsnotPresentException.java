package com.hcl.customexception;

public class IdIsnotPresentException extends RuntimeException {

	public IdIsnotPresentException(String str) {
		super(str);
	}

}
