package com.sbc.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.sbc.controller.UserController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(JWTUtil.class); 
	
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_ACCESS_TOKEN_EXPIRATION = 60;				// 1 min
	public static final long JWT_REFRESH_TOKEN_EXPIRATION = 30*60;			// 30 min
	public static final String TOKEN_ISSUER = "Authorization_Server";				
	public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;	
	
	/* 
	 * get 'secret' value from properties file. It is unique to each client (any SPA application that wants to access
	 * the owner's resources must register to the server before authenticating using tokens. ClientId and secret 
	 * are generated while registering each SPA to the resource server and are unique to each SPA)
	 */ 
	@Value("${jwt.secret}")		
	private String JWTsecret; 
	
	
	/**
	 * ACCESS TOKEN BUILDER 
	 * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
	 * 2. Sign the JWT using the HS512 algorithm and secret key.
	 * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	 *    compaction of the JWT to a URL-safe string 
	 */
	private String accessTokenBuilder(Map<String, Object> claims, String username) {
		String jwtTokenBuilder = null;		
		jwtTokenBuilder = Jwts.builder()
						.setClaims(claims)
						.setIssuer(TOKEN_ISSUER)
						.setSubject(username)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_EXPIRATION * 1000))		// convert sec. to milliseconds
						.signWith(SIGNATURE_ALGORITHM, JWTsecret)
						.compact();
		LOG.info("jwtTokenBuilder=" + jwtTokenBuilder);
		return jwtTokenBuilder;
	}
	
	/**
	 * REFRESH TOKEN BUILDER
	 */
	private String refreshTokenBuilder(Map<String, Object> claims, String username) {
		String jwtTokenBuilder = null;		
		jwtTokenBuilder = Jwts.builder()
						.setClaims(claims)
						.setIssuer(TOKEN_ISSUER)
						.setId(UUID.randomUUID().toString())
						.setSubject(username)
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_EXPIRATION * 1000))		// convert sec. to milliseconds
						.signWith(SIGNATURE_ALGORITHM, JWTsecret)
						.compact();
		LOG.info("jwtTokenBuilder=" + jwtTokenBuilder);
		return jwtTokenBuilder;
	}
	
	private Claims getAllClaimsFromToken(String token) {
		LOG.info("JWTsecret=" + JWTsecret);
		return Jwts.parser().setSigningKey(JWTsecret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	
	/**
	 * GENERATE ACCESS TOKEN
	 * @param userDetails
	 * @return access_token
	 * you can define your own claim as shown below; if you do not define claim object, spring security will
	 * generate default claim
	 */
	public String generateAccessToken(UserDetails userDetails) {
		LOG.info("JWTsecret=" + JWTsecret);		
		//Map<String, Object> claims = new HashMap<>();
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("scopes", userDetails.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
		String access_token =  accessTokenBuilder(claims, userDetails.getUsername());		
		return access_token;
	}
	
	
	/**
	 * GENERATE REFRESH TOKEN
	 * @param userDetails
	 * @return refresh_token
	 */
	public String generateRefreshToken(UserDetails userDetails) {
		LOG.info("JWTsecret=" + JWTsecret);
		//Map<String, Object> claims = new HashMap<>();
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        //claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));
		String refresh_token =  refreshTokenBuilder(claims, userDetails.getUsername());		
		return refresh_token;
	}
	
	
	/**
	 * GET USERNAME FROM TOKEN
	 * @param token
	 * @return username
	 */
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	
	/**
	 * GET EXPIRATION DATE FROM TOKEN
	 * @param token
	 * @return expiration date
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	
	/**
	 * GET CLAIM FROM TOKEN
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	
	/**
	 * VALIDATE TOKEN 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	

	
}
