package com.vapl.dialer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoDataException extends RuntimeException 
{
	private static final long serialVersionUID = 100L;
	public NoDataException(String exception) {
	    super(exception);
	  }
}