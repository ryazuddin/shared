package com.integration.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 * resource class
 * @author Ryazuddin
 *
 */
public class EmployeeResource {

	@Context
	UriInfo uriInfo;
	
	@Context
	Request request;
	String id;

	EmployeeService employeeService;

	public EmployeeResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		employeeService = new EmployeeService();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Employee getEmployee() {
		Employee employee = employeeService.getEmployee(id);
		return employee;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public Employee getEmployeeAsHtml() {
		Employee employee = employeeService.getEmployee(id);
		return employee;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putEmployee(JAXBElement<Employee> employeeElement) {
		Employee employee = employeeElement.getValue();
		Response response;
		if (employeeService.getEmployees().containsKey(employee.getId())) {
			response = Response.noContent().build();
		} else {
			response = Response.created(uriInfo.getAbsolutePath()).build();
		}
		employeeService.createEmployee(employee);
		return response;
	}

	@DELETE
	public void deleteEmployee() {
		employeeService.deleteEmployee(id);
	}

}