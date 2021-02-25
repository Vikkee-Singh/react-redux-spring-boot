package com.vapl.dialer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value=HttpStatus.NOT_FOUND,reason="User not found")
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException 
{
	private static final long serialVersionUID = 100L;
	public GenericException(String exception) {
	    super(exception);
	  }
}