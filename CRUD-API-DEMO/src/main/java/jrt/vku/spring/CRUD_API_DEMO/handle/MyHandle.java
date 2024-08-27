package jrt.vku.spring.CRUD_API_DEMO.handle;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyHandle {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorRespone> checkAll(Exception ex){
		ErrorRespone errorRespone = new ErrorRespone(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRespone);
	}

}
