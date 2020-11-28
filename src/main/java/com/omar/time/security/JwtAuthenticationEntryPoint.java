package com.omar.time.security;

//The first spring security related class that we’ll define is JwtAuthenticationEntryPoint
//It implements AuthenticationEntryPoint interface and provides the implementation for its commence() method.
//This method is called whenever an exception is thrown due to an unauthenticated user trying to access a resource
//that requires authentication.
//In this case, we’ll simply respond with a 401 error containing the exception message.

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omar.time.exception.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
   
	private final MessageSource messageSource;
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	
	@Autowired
	public JwtAuthenticationEntryPoint(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
    
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());     
    	
    	ApiError response = new ApiError(HttpStatus.UNAUTHORIZED);
    	String message = messageSource.getMessage("errors.app.unauthorized", null, httpServletRequest.getLocale());
        response.setMessage(message);
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
    }
    
}