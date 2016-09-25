package com.foodrescue.domain;

public class User{

	@Override
	public String toString() {
		return "User [name=" + name + ", deviceId=" + deviceId + ", phone=" + phone + ", location=" + location
				+ ", password=" + password + "]";
	}
	private String name;
	private String deviceId;
	private String phone;
	private String location;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
