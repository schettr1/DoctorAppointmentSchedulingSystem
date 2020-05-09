package com.sbc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sbc.controller.UserController;

/**
 * Configure CORS - Cross Origin Resource Sharing
 * If request coming from client domain (http://localhost:3000) is different than server domain (http://localhost:8080),
 * simple requests with GET, POST will be processed fine but requests that has headers such as "Authorization" or "Content-Type" will throw
 * CORS flag. Before sending GET or POST request, browser will send a pre-flight [OPTIONS] request to the server in order to check whether 
 * the server will accept such kind of request or not. The server then sends a response to that pre-flight request with information such as
 * Allowed-Origins, Allowed-Headers, Allowed-Methods etc. If these variables values matches with the values of client actual request(POST/GET),
 * client request is processed or else that request will not be sent from the client to the server and "401 - Unauthorized" error
 * message is returned. Because client sends a pre-flight [OPTIONS] request, you must either configure .cors() or 
 * .antMatchers(HttpMethod.OPTIONS).permitAll() in HttpSecurity so that the Server does not block the pre-flight request.
 * Response to the pre-flight [OPTIONS] should include Access-Control-Allow-Origin, Access-Control-Allow-Credentials etc.
 * You can define CORS as @Bean in one of the @Configuration class or globally define CORS in CORSConfig.class
 * This CORS will provide information about Access-Control-Allow-Origin, Access-Control-Allow-Credentials etc.
 * You cannot declare @CrossOrigin() in controller instead of declaring CORS as @Bean because it will not work with Basic Authentication. 
 * However, it will work only when different domain client sends simple requests to the Server. Sending pre-flight [OPTIONS] request
 * from the client as in Basic Authentication will require to implement CORS.
 */

//@Configuration
//@EnableWebMvc
public class CORSConfig implements WebMvcConfigurer {
	
	private static final Logger LOG = LoggerFactory.getLogger(CORSConfig.class); 
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	LOG.info("addCorsMappings()");
        registry.addMapping("/**")
        		.allowedMethods("POST", "GET", "OPTIONS", "PUT", "DELETE")
        		.allowedOrigins("http://localhost:3000")
                .allowedHeaders("Authorization", "Content-Type", "Accept");
    }
}