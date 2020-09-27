package com.omar.time.exception;

import java.util.List;
<<<<<<< HEAD
import java.util.Locale;
=======
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
=======
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
=======
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
<<<<<<< HEAD
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	private MessageSource messageSource;
	
	
	@Autowired
	public RestResponseEntityExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
=======
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	//handle custom exceptions
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(
			EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDenied(
			AccessDeniedException ex) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequest(
			BadRequestException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<Object> handleUsernameNotFound(
			UsernameNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	
//	@ExceptionHandler({ Exception.class })
//	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
//	    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//	    apiError.setMessage(ex.getMessage());
//	    return buildResponseEntity(apiError);
//	}

	
	
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889
	//override and handle default ResponseEntityExceptionHandler handlers
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Method not allowed";
		return buildResponseEntity(new ApiError(HttpStatus.METHOD_NOT_ALLOWED, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Media type not supported";
		return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Media type not acceptable";
		return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Missing parameter(s)";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Missing parameter(s)";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Something went wrong";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Type conversion not supported";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Type mismatch";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Something went wrong";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder sb = new StringBuilder(); 
        List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        for(FieldError fieldError: fieldErrors){
            sb.append(fieldError.getDefaultMessage());
            sb.append(";");
        }
		String error = sb.toString();
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Something went wrong";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		String error = "Something went wrong";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "No handler found";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		String error = "Request timeout";
		return buildResponseEntity(new ApiError(HttpStatus.REQUEST_TIMEOUT, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Something went wrong";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

<<<<<<< HEAD
	
	//handle custom exceptions
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(
			EntityNotFoundException ex, Locale locale) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		String message = messageSource.getMessage(ex.getMessage(), null, locale);
		apiError.setMessage(message);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleAccessDenied(
			AccessDeniedException ex, Locale locale) {
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED);
		String message = messageSource.getMessage(ex.getMessage(), null, locale);
		apiError.setMessage(message);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleBadRequest(
			BadRequestException ex, Locale locale) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		String message = messageSource.getMessage(ex.getMessage(), null, locale);
		apiError.setMessage(message);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	protected ResponseEntity<Object> handleUsernameNotFound(
			UsernameNotFoundException ex, Locale locale) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		String message = messageSource.getMessage(ex.getMessage(), null, locale);
		apiError.setMessage(message);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request, Locale locale) {
	    ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
	    String message = messageSource.getMessage(ex.getMessage(), null, locale);
		apiError.setMessage(message);
	    return buildResponseEntity(apiError);
	}
	
	
	
	
=======
>>>>>>> 4de2425f60ccc091d3a544b44ac3af7938fdb889

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
