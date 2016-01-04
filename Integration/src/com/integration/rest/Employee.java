package com.integration.rest;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * To hold the Employee details
 * 
 * @author Ryazuddin
 *
 */
@XmlRootElement
public class Employee {
	private String id;
	private String name;
	private String gender;
	private String department;
	private String joiningDate;
	private String password;

	public Employee() {

	}

	public Employee(String id, String name, String gender, String department, String joiningDate,String password) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.department = department;
		this.joiningDate = joiningDate;
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}