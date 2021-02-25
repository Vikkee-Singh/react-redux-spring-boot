package com.vapl.dialer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vapl.dialer.exception.GenericException;
import com.vapl.dialer.exception.IncorrectParametersException;
import com.vapl.dialer.exception.NoDataException;
import com.vapl.dialer.exception.UserNotFoundException;
import com.vapl.dialer.pojo.ErrorDetails;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
    //ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
       // request.getDescription(false));
	  ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(NoDataException.class)
  public final ResponseEntity<ErrorDetails> handleNoDataException(NoDataException ex, WebRequest request) {
    //ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
       // request.getDescription(false));
	  ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
  }
  
  @ExceptionHandler(GenericException.class)
  public final ResponseEntity<ErrorDetails> handleGenericException(GenericException ex, WebRequest request) {
    //ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
       // request.getDescription(false));
	  ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @ExceptionHandler(IncorrectParametersException.class)
  public final ResponseEntity<ErrorDetails> handleIncorrectParametersException(IncorrectParametersException ex, WebRequest request) {
    //ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
       // request.getDescription(false));
	  ErrorDetails errorDetails = new ErrorDetails(ex.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
