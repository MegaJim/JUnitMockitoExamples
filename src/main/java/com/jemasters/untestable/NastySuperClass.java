package com.jemasters.untestable;

import java.util.Queue;

import com.jemasters.annotations.Injected;
import com.jemasters.annotations.RequestScope;

@RequestScope
public class NastySuperClass {

	@Injected
	private Queue<String> logQueue;

	private String userId;
	private String firstName;
	private String lastName;

	public boolean validateFields() {
		return LegacyUserValidator.validateFirstName(firstName)
				&& LegacyUserValidator.validateLastName(lastName);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Queue<String> getLogQueue() {
		return logQueue;
	}

	public void setLogQueue(Queue<String> logQueue) {
		this.logQueue = logQueue;
	}

}
