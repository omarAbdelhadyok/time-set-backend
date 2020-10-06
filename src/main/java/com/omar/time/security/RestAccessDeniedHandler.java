package com.omar.time.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omar.time.exception.ApiError;

@Component
public class RestAccessDeniedHandler extends AccessDeniedHandlerImpl {
	
	private MessageSource messageSource;
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	
	
	@Autowired
	public RestAccessDeniedHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
		logger.error("Responding with access denied error. Message - {}", e.getMessage());
		
		ApiError response = new ApiError(HttpStatus.UNAUTHORIZED);
    	String message = messageSource.getMessage("errors.app.accessDenied", null, httpServletRequest.getLocale());
        response.setMessage(message);
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, response);
        out.flush();
	}

}
