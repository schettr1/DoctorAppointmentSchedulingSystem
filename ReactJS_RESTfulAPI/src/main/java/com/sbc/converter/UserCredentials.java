package com.sbc.converter;

/**
 * It is used in AuthService and UserService.
 * It is different from projection because projection has methods that match the columns of resultSet or tables.
 * but Converter is a class that contains specific parameters. UserCredentials has username, password and role only. 
 *
 * 	{
 * 		"userId": 3001,
 *    	"username": "surya",
 *    	"password": "$2a$10$aRwVeemUfr2bzos2G6cjeOi0VfMc8NWu9ckS7XAzgRlzh5PKDEcaK",
 *    	"role": "ROLE_DOCTOR"
 * 	}
 * 
 */

public class UserCredentials {

	private int userId;
	private String username;
	private String password;
	private String role;		// It cannot be RoleEnum because Spring Security needs String value 
	
	/* constructor + setter and getter + toString */
	
	public UserCredentials() {
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserCredentials [userId=" + userId + ", username=" + username + ", password=" + password + ", role="
				+ role + "]";
	}
	
	
	
}
