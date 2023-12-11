package com.blog.exceptions;

import com.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        String message = exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception,HttpStatus httpStatus){
        Map<String,String> response = new HashMap<>();
        response.put("timestamp",String.valueOf(System.currentTimeMillis()));
        response.put("status",String.valueOf(httpStatus.value()));
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName,message);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginPasswordException.class)
    public ResponseEntity<ApiResponse> usernamePasswordLoginExceptionHandler(LoginPasswordException exception){
        String message = exception.getMessage();
        return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
    }

}
