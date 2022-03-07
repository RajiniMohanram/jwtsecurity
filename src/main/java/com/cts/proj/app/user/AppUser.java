package com.cts.proj.app.user;

public class AppUser {
	private String userId;
	private String password;
	private String userName;
	private String userMail;
	private String userCity;
	//include constructors
	//getters, setters
	public AppUser(String userId, String password, String userName, String userMail, String userCity) {
		super();
		this.userId = userId;
		this.password = password;
		this.userName = userName;
		this.userMail = userMail;
		this.userCity = userCity;
	} 
	public AppUser() {}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	
}
