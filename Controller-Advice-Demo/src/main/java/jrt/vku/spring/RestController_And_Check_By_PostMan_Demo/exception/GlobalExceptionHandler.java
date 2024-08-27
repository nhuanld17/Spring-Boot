package jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.exception;

import jrt.vku.spring.RestController_And_Check_By_PostMan_Demo.entity.ErrorRespone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(StudentException.class)
//	public ResponseEntity<ErrorRespone> catchError(StudentException exception){
//		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.NOT_FOUND.value(),exception.getMessage());
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespone);
//	}
//
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorRespone> catchAllError(Exception exception){
//		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.BAD_REQUEST.value(),exception.getMessage());
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
//	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorRespone> catchError(StudentException exception){
		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.NOT_FOUND.value(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespone);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorRespone> catchAllError(Exception exception){
		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.NOT_FOUND.value(),exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRespone);
	}
}
