package com.example.marinehackatonbe.global.exception;

import com.example.marinehackatonbe.global.domain.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 공통적으로 사용하는 CommonResponse 빌더 메서드
	private ResponseEntity<CommonResponse<?>> generateErrorResponse(HttpStatus status, String message) {
		CommonResponse<?> errorResponse = CommonResponse.of(status.value(), message, null);
		return new ResponseEntity<>(errorResponse, status);
	}

	// 커스텀 Exception
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<CommonResponse<?>> handleCustomException(CustomException exception) {
		log.error("Exception occurred: {}\n", exception.getMessage());
		return generateErrorResponse(exception.getContent().getHttpStatus(), exception.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponse<?>> handleAllExceptions(Exception e) {
		log.error("Unhandled exception occurred: {}\n", e.getMessage(), e);
		return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<CommonResponse<?>> handleNoSuchElementException(NoSuchElementException e) {
		log.error("No such element found: {}\n", e.getMessage(), e);
		return generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<CommonResponse<?>> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
		log.error("Empty result for data access operation: {}\n", e.getMessage(), e);
		return generateErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<CommonResponse<?>> handleJsonException(HttpMessageNotReadableException e) {
		log.error("Json Exception ErrMessage={}\n", e.getMessage());
		return generateErrorResponse(HttpStatus.BAD_REQUEST, "json 형식이 올바르지 않습니다.");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<CommonResponse<?>> handleRequestMethodException(HttpRequestMethodNotSupportedException e) {
		log.error("Http Method not supported Exception ErrMessage={}\n", e.getMessage());
		return generateErrorResponse(HttpStatus.BAD_REQUEST, "해당 요청에 대한 API가 존재하지 않습니다. 엔드 포인트를 확인해주시길 바랍니다.");
	}


}