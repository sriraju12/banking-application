package com.hcl.globalexception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hcl.customexception.EmptyDataFoundInDatabaseException;
import com.hcl.customexception.EmptyInputException;
import com.hcl.customexception.IdIsnotPresentException;

@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyInputException(EmptyInputException emptyInputException) {
		return new ResponseEntity<String>("Empty input fields", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmptyDataFoundInDatabaseException.class)
	public ResponseEntity<String> handleEmptyDataFoundInDatabaseException(
			EmptyDataFoundInDatabaseException databaseException) {
		return new ResponseEntity<String>("No Data found in Dababase", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyResultDataAcessException(EmptyResultDataAccessException accessException) {
		return new ResponseEntity<String>("Id is not present in Database", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IdIsnotPresentException.class)
	public ResponseEntity<String> handleIdIsnotPresentException(IdIsnotPresentException idIsnotPresentException) {
		return new ResponseEntity<String>("Id is not present in Database", HttpStatus.NOT_FOUND);
	}

}
