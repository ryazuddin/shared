package com.integration.rest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Service class to communicate with dao.
 * 
 * @author Ryazuddin
 *
 */
public class EmployeeService {

	EmployeeDao employeeDao;

	public EmployeeService() {
		employeeDao = employeeDao.instance;
	}

	public void createEmployee(Employee employee) {
		employeeDao.getEmployees().put(employee.getId(), employee);
	}

	public Employee getEmployee(String id) {
		Employee emp = employeeDao.getEmployees().get(id);
		return emp;
	}

	public Map<String, Employee> getEmployees() {
		return employeeDao.getEmployees();
	}

	public List<Employee> getEmployeeAsList() {
		//check the token exist or not if not then redirect to login page else carry on
		List<Employee> productList = new ArrayList<Employee>();
		productList.addAll(employeeDao.getEmployees().values());
		return productList;
	}

	public int getEmployeesCount() {
		return employeeDao.getEmployees().size();
	}

	public void deleteEmployee(String id) {
		employeeDao.getEmployees().remove(id);
	}

	public boolean authenticateUser(String userId, String password) {
		boolean status = false;
		for (Employee employee : employeeDao.getEmployees().values()) {
			if (userId.equalsIgnoreCase(employee.getName()) && password.equalsIgnoreCase(password)) {
				System.out.println("User found ");
				addUserAuthToken(userId);
				status = true;
				break;
			} else {
				System.out.println("Uer not found " + userId);
			}
		}
		return status;
	}

	/**
	 * After sucessFul authentication This will add the token in the hashmap for
	 * further transaction
	 * 
	 * @param userId
	 * @param password
	 */
	public void addUserAuthToken(String userId) {
		Employee emp = this.getEmployee(userId);
		// hashed key and key will be
		Map<String, String> loggedUserToken = new HashMap<String, String>();
		// Timer data to hold
		Map<String, Long> timerMap = new LinkedHashMap<>();
		String generatedToken;
		try {
			generatedToken = EmployeeUtils.encriptData(userId);
			loggedUserToken.put(generatedToken, emp.getId());
			timerMap.put(generatedToken, System.currentTimeMillis());
			employeeDao.setLoggedUserToken(loggedUserToken);
			employeeDao.setTimerMap(timerMap);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This will remove the token from the Hashmap.This is not persistant once
	 * server start or buld this data will lost. may beput in database to
	 * indicate session active,remove from dtabase once logout called.
	 * 
	 * @param token
	 */
	public void logout(String token) {
		employeeDao.actionAfterTimeout(token);
	}

}
