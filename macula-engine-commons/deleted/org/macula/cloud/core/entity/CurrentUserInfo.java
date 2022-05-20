package org.macula.cloud.core.entity;

/**
 * Created by linqina on 2018/12/26 9:25 PM.
 */
public class CurrentUserInfo {
	private Long userId;

	private String username;

	private String email;

	private String phone;

	private String employeeId;

	private String company;

	private String department;

	private String origin;

	public CurrentUserInfo() {
	}

	public CurrentUserInfo(Long userId, String username, String email, String phone, String employeeId, String company, String department,
			String origin) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.employeeId = employeeId;
		this.company = company;
		this.department = department;
		this.origin = origin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
