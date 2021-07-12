package com.tcra.hrms.entity.login;

import java.io.Serializable;


public class UserAuthentication implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String fullname;
	private String filenumber;
	private String token;
	private String photo;
	private int empId;
	private String email;
	private String roles;
	private String username;
	private String password;
	private String designation;
	
	private int leaveApproval;

	private int leaveCommutationApproval;

	private int leaveRecallApproval;
	private int trainingApproval;

	private int totalApproval;
	
	public UserAuthentication() {}
	
	public UserAuthentication(String username, String password) {
		setUsername(username);
		setPassword(password);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getFilenumber() {
		return filenumber;
	}
	public void setFilenumber(String filenumber) {
		this.filenumber = filenumber;
	}
		
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
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
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public int getLeaveApproval() {
		return leaveApproval;
	}
	public void setLeaveApproval(int leaveApproval) {
		this.leaveApproval = leaveApproval;
	}
	
	public int getLeaveCommutationApproval() {
		return leaveCommutationApproval;
	}
	public void setLeaveCommutationApproval(int leaveCommutationApproval) {
		this.leaveCommutationApproval = leaveCommutationApproval;
	}

	public int getLeaveRecallApproval() {
		return leaveRecallApproval;
	}
	public void setLeaveRecallApproval(int leaveRecallApproval) {
		this.leaveRecallApproval = leaveRecallApproval;
	}
	
	public int getTrainingApproval() {
		return trainingApproval;
	}
	public void setTrainingApproval(int trainingApproval) {
		this.trainingApproval = trainingApproval;
	}
	
	public int getTotalApproval() {
		return totalApproval;
	}
	public void setTotalApproval(int totalApproval) {
		this.totalApproval = totalApproval;
	}
}
