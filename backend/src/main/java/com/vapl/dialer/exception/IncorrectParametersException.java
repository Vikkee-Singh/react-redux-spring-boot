package com.vapl.dialer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="User not found")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectParametersException extends RuntimeException 
{
	private static final long serialVersionUID = 100L;
	public IncorrectParametersException(String exception) {
	    super(exception);
	  }
}