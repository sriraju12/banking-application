package com.hcl.capstone.customexception;

public class NoSuchIdPresentInDBException extends RuntimeException {
	public NoSuchIdPresentInDBException(String msg) {
		super(msg);
	}

}
