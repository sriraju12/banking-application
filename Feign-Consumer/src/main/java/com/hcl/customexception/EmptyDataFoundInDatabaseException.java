package com.hcl.customexception;

public class EmptyDataFoundInDatabaseException extends RuntimeException {

	public EmptyDataFoundInDatabaseException(String str) {
		super(str);
	}

}
