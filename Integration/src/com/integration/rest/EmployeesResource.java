package com.integration.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/employees")
public class EmployeesResource {

	@Context
	UriInfo uriInfo;

	@Context
	Request request;

	EmployeeService employeeService;

	public EmployeesResource() {
		employeeService = new EmployeeService();
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Employee> getEmployee() {
		return employeeService.getEmployeeAsList();
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Employee> getEmployeeAsHtml() {
		return employeeService.getEmployeeAsList();
	}

	// URI: http://localhost:8080/Integration/rest/employees/count
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		return String.valueOf(employeeService.getEmployeesCount());
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void createEmployee(@FormParam("id") String id, @FormParam("empname") String name,
			@FormParam("empgender") String gender, @FormParam("empdepartment") String department,
			@FormParam("empdoj") String doj, @FormParam("empPassword") String password,
			@Context HttpServletResponse servletResponse) throws IOException {
		Employee employee = new Employee(id, name, gender, department, doj, password);
		employeeService.createEmployee(employee);
		servletResponse.sendRedirect("./employees/");
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Employee authenticateEmployee(@FormParam("userId") String userId,
			@FormParam("empPassword") String password, @Context HttpServletResponse servletResponse)
			throws IOException {
		System.out.println(userId + " : " + password);
		employeeService.authenticateUser(userId, password);
		return employeeService.getEmployee(userId);
	}

	// URI : http://localhost:8080/Integration/rest/employees/2 (get)
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Employee getEmployee(@PathParam("id") String id) {
		return employeeService.getEmployee(id);
	}

	// URI : http://localhost:8080/Integration/rest/employees/1 (post)
	@POST
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String logOut(@PathParam("id") String id) {
		employeeService.logout(id);
		return "success";
	}

}