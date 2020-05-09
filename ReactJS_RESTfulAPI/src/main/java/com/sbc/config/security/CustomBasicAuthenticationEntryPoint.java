package com.sbc.config.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbc.exception.ExceptionMessage;

/**
 * AuthenticationEntryPoint is used to throw 401 UNAUTHORIZED response if user fails to provide
 * valid credentials or valid token.
 * It allows us to customize the Http response by setting the headers and body in the response. 
 * Lets suppose two APIs are communicating instead of 
 * a human client trying to consume web services. If authentication fails a response is 
 * expected in JSON/text format and not a jsp/html page.
 */

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	
	private static final int CODE_UNAUTHORIZED = 401;
	
	@Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) 
    																		throws IOException, ServletException {
		
		// 1. set Response.Status
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // 2. set Response.Header
        /**
         * "WWW-Authenticate" header will create "401 Unauthorized" response.status
         * This header prompts the browser to pop-up [Signin http://localhost:3000 username: "" password: ""] 
         * To avoid this behavior of the browser, you must either remove "WWW-Authenticate" header from the response
         * or return with a response.status of 200 and exception message 
         * {code: 401, status: 'Unauthorized', timestamp: '', message: ''}
         */
        //response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");  
         
        ExceptionMessage exceptionMessage = new ExceptionMessage
        												(CODE_UNAUTHORIZED, HttpStatus.UNAUTHORIZED, new Date(), authException.getMessage());
        // 3. set Response.ContentType
        response.setContentType("application/json");
        
        // 4. set Response.Body
        PrintWriter writer = response.getWriter();
        writer.println(convertJavaObjectToJSONString(exceptionMessage));
    }
     
	
    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("MY_TEST_REALM");
        super.afterPropertiesSet();
    }
    
    
    /** 
     * convert Exception Java Object to JSON String 
     * @param Object 
     * @return String 
     */  
    private String convertJavaObjectToJSONString(Object obj) {  
        ObjectMapper mapper = new ObjectMapper();  
        String JSON = "";  
        try {  
            JSON = mapper.writeValueAsString(obj);  
        } catch (JsonProcessingException e) {  
            e.printStackTrace();  
        }            
        return JSON;         
    }  
}
