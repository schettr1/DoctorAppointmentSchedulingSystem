package com.sbc.config.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sbc.jwt.JWTRequestFilter;


/** 
 * Extend WebSecurityConfigurerAdapter and override its methods to 
 * provide Basic Authentication and Authorization
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class); 
	
	@Autowired
	private UserDetailsService userDetailsService; 

	@Autowired
	private JWTRequestFilter jwtRequestFilter;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());	
	}
       
    @Override 
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	LOG.info("inside configure(HttpSecurity httpSecurity) method");
    	 
    	/** 
    	 * 1. If you have defined @RequestMapping(path='/reactjs_restful') for Controller as well, then you must include it in pathname.
    	 * 2. anyRequest().authenticated() will exempt any requests from authentication. If you had not defined antMatchers().access()
    	 *    then you should place it after the antMatchers().permitAll()
    	 * 3. If you are defining antMatchers().access() then remove anyRequest().authenticated()
    	 * 4. Order is authorizeRequests() -> antMatchers().permitAll() -> anyRequest().authenticated() -> authenticationEntryPoint() -> accessDeniedHandler()
    	 *    OR, authorizeRequests() -> antMatchers().permitAll() -> antMatchers().access() -> authenticationEntryPoint() -> accessDeniedHandler()
    	 *           
    	 */
    	httpSecurity	
    	//.cors().and()										// .cors() also permits pre-flight method OPTIONS without authentication 
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS).permitAll()		// 'pre-flight [OPTIONS]' request is sent by browser to check if server can handle cross-origin issues 	
		.antMatchers("/reactjs_restful/authorize").permitAll()				 
		.antMatchers("/reactjs_restful/renew_access_token").permitAll()		 
		.antMatchers("/reactjs_restful/save_patient").permitAll()			
		//.anyRequest().authenticated()						              // all other requests must be authenticated 
		/** 
		 * If antMatchers().access() is used remove anyRequest().authenticated()
		 * Also, if paths are defined for controllers, you must include those here.
		 * "/**" means that there is some text after "/" 
		 * Include everything except domain "http://localhost://8080" in antMatchers()
		 */
		.antMatchers("/reactjs_restful/admin/**").access("hasRole('ROLE_ADMIN')")  					
		.antMatchers("/reactjs_restful/doctor/**").access("hasRole('ROLE_DOCTOR')") 				
		.antMatchers("/reactjs_restful/patient/**").access("hasRole('ROLE_PATIENT')") 				
		.antMatchers("/reactjs_restful/admin_or_patient/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_PATIENT')")		
		.antMatchers("/reactjs_restful/admin_or_doctor/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR')")		
		.antMatchers("/reactjs_restful/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DOCTOR') or hasRole('ROLE_PATIENT')")	
		.and().httpBasic().realmName("MY_TEST_REALM").authenticationEntryPoint(getBasicAuthEntryPoint()) 	// throw exception for 401 UNAUTHORIZED
		.and().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())					// throw exception for 403 FORBIDDEN
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)		// No session creation needed for REST web service
        .and().csrf().disable();															// enabling CSRF would require csrf_token validation as well
    	
    	httpSecurity
		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	// intercepts every incoming request for validating token
    	
    }
    
    
    /**
     * AuthenticationManager is autowired/needed in User Controller 
     * to authenticate UsernamePasswordAuthenticationToken(username, password)
     */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
    	LOG.info("inside authenticationManagerBean()");
		return super.authenticationManagerBean();
	}
	
	
	/**
	 * Encoder helps to convert the password from String to BCrypted so that 
	 * it can be compared to database password
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
    	LOG.info("inside getPasswordEncoder()");
		LOG.info("\nB-encrypted value of 'password' is " + new BCryptPasswordEncoder().encode("password") + "\n");
		return new BCryptPasswordEncoder();
	}
	
	
	/**
	 * Used to throw Exception (401 ~ Unauthorized) when users enter
	 * invalid credentials for authentication
	 */
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
    	LOG.info("inside getBasicAuthEntryPoint()");
        return new CustomBasicAuthenticationEntryPoint();
    }
     
    
	/**
	 * Used to throw Exception when user have
	 * no authority/role to access the resources
	 */
    @Bean
    public CustomAccessDeniedHandler getAccessDeniedHandler(){
    	LOG.info("inside getAccessDeniedHandler()");
        return new CustomAccessDeniedHandler();
    }
    
    
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
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "HEAD"));
        config.setAllowedHeaders(Arrays.asList("Accept",
        										"Authorization", 
        										"Access-Control-Allow-Credentials", 
        										"Access-Control-Allow-Headers", 
        										"Access-Control-Allow-Methods",
        										"Access-Control-Allow-Origin", 
        										"Access-Control-Expose-Headers", 
        										"Access-Control-Max-Age",
        										"Access-Control-Request-Headers", 
        										"Access-Control-Request-Method", 
        										"Age", 
        										"Allow", 
        										"Content-Range", 
        										"Content-Disposition", 
        										"Content-Description",
        										"Content-Type", 
        										"Origin",
        										"X-Requested-With"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        CorsFilter corsfilter = new CorsFilter(source);
        return corsfilter;
    }
    
    
    /**
     * To allow using multiple slashes '/, /' in URLs 
     * Select either of these two methods 
     * defaultHttpFirewall() or
     * allowUrlEncodedSlashHttpFirewall() 
     * Spring Security 5.1x has this bug!!
     */
    @Override 
    public void configure(WebSecurity webSecurity) throws Exception {
    	//webSecurity.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    	LOG.info("inside configure(WebSecurity web)");
    	webSecurity
    		.httpFirewall(defaultHttpFirewall());
    }
    
    
	/**
	 * Default FireWall 
	 */
    @Bean
    public HttpFirewall defaultHttpFirewall() {
    	LOG.info("inside defaultHttpFirewall()");
        return new DefaultHttpFirewall();
    }
    
    
	/**
	 * Custom FireWall allows to use multiple '/'s in the URL.
	 * It is used because spring security sometimes throws error 
	 * "The request was rejected because the URL was not normalized" 
	 * if the URL has multiple '/'s
	 */
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
    	LOG.info("inside allowUrlEncodedSlashHttpFirewall()");
        StrictHttpFirewall fireWall = new StrictHttpFirewall();
        fireWall.setAllowUrlEncodedSlash(true);    
        return fireWall;
    }

    
}
