package com.trabajodegrado.freshfruitventas.configuracion;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.trabajodegrado.freshfruitventas.excepciones.ApiError;
import com.trabajodegrado.freshfruitventas.excepciones.ConflictException;
import com.trabajodegrado.freshfruitventas.excepciones.DatosInvalidosExcepcion;
import com.trabajodegrado.freshfruitventas.excepciones.NotFoundException;

@ControllerAdvice(annotations = RestController.class)
public class InterceptorExcepciones {

    	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ApiError> processSupportedExceptions(Exception ex) {	//Buscar todas las excepciones configuradas
		

		return  new ResponseEntity<>(ApiError.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error(ex.getClass().getSimpleName())
				.message(ex.getMessage())
				.build(), 
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

    	@ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<ApiError> notFoundException(NotFoundException ex) {
	        return createResponseEntityFromException(ex, HttpStatus.NOT_FOUND);
	    }
    	
    	@ExceptionHandler(ConflictException.class)
	    public ResponseEntity<ApiError> conflictoExcepcion(ConflictException ex) {
	        return createResponseEntityFromException(ex, HttpStatus.CONFLICT);
	    }

    	@ExceptionHandler(DatosInvalidosExcepcion.class)
	    public ResponseEntity<ApiError> datosInvalidosExcepcion(DatosInvalidosExcepcion ex) {
	        return createResponseEntityFromException(ex, HttpStatus.BAD_REQUEST);
	    }
    	

	private ResponseEntity<ApiError> createResponseEntityFromException(RuntimeException ex, HttpStatus status) {
		Objects.requireNonNull(ex);
		return new ResponseEntity<>(ApiError.builder()
				.status(status.value())
				.error(ex.getClass().getSimpleName())
				.message(ex.getMessage())
				.build(), 
				status);
	}
	
	
	
}