package com.integration.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Employee information
 * @author Ryazuddin
 *
 */

public enum EmployeeDao {
	instance;
	// Hold the List of employee
	private Map<String, Employee> employee = new HashMap<String, Employee>();
	// Infuture we can store token and employyyee id to minimize the heap
	// memory.
	private Map<String, String> loggedUserToken = new HashMap<String, String>();

	private Map<String, Long> timerMap = new HashMap<String, Long>();

	public Map<String, Long> getTimerMap() {
		return timerMap;
	}

	public void setTimerMap(Map<String, Long> timerMap) {
		this.timerMap = timerMap;
	}

	private static final Long TIMEOUT_IN_MILLIS = 10000l;

	private EmployeeDao() {

		// pumping-in some default data we can pick the data from database in
		// future

		Employee employeeRecord = new Employee("1001", "Sertaj", "Male", "Business analyst", "12/3/2008", "password");
		employee.put("1", employeeRecord);

		employeeRecord = new Employee("1002", "Asif", "Male", "Architecture", "1/2/2008", "password");
		employee.put("2", employeeRecord);

		employeeRecord = new Employee("1003", "Riyaz", "Male", "Senior developer", "1/1/2008", "password");
		employee.put("3", employeeRecord);

		employeeRecord = new Employee("1004", "sujata", "Female", "Accountant", "12/12/2010", "password");
		employee.put("4", employeeRecord);
	}


	public Map<String, String> getLoggedUserToken() {
		return loggedUserToken;
	}

	public void setLoggedUserToken(Map<String, String> loggedUserToken) {
		this.loggedUserToken = loggedUserToken;
	}

	public Map<String, Employee> getEmployees() {
		return employee;
	}

	/**
	 * Check timer for every request if
	 * 
	 * @param tokenId
	 */
	public boolean isTokenExpired(String tokenId) {
		Long time = this.getTimerMap().get(tokenId);
		if ((System.currentTimeMillis() - time) > TIMEOUT_IN_MILLIS) {
			actionAfterTimeout(tokenId);
			return true;
		}
		return false;
	}

	/**
	 * remove the token
	 * @param tokenId
	 */
	public void actionAfterTimeout(String tokenId) {
		this.getTimerMap().remove(tokenId);
		this.getLoggedUserToken().remove(tokenId);
	}

}
