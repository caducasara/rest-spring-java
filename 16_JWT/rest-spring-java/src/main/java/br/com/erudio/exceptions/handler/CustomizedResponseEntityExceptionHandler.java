package br.com.erudio.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.erudio.exceptions.ExceptionResponse;
import br.com.erudio.exceptions.InvalidJWTAuthenticationException;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest req){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				req.getDescription(false)
				);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(Exception ex, WebRequest req){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				req.getDescription(false)
				);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest req){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				req.getDescription(false)
				);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidJWTAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJWTAuthenticationException(Exception ex, WebRequest req){
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(
				new Date(),
				ex.getMessage(),
				req.getDescription(false)
				);
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
}
