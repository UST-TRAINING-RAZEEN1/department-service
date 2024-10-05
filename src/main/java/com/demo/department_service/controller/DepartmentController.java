package com.demo.department_service.controller;

import java.util.List;

import com.demo.department_service.model.EmployeePojo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.demo.department_service.model.DepartmentPojo;
import com.demo.department_service.service.DepartmentService;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api")
public class DepartmentController {

	DepartmentService deptService;

	public static final Logger LOG= LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	public DepartmentController(DepartmentService deptService) {
		this.deptService = deptService;
	}
	
	@GetMapping("/departments")
	public List<DepartmentPojo> getAllDpartments(){
		LOG.info("getalldept()");
		return deptService.getAllDepartments();
	}
	
	@GetMapping("/departments/{did}")
	@CircuitBreaker(name="ciremp",fallbackMethod = "empFallBack")
	public DepartmentPojo getADepartment(@PathVariable("did") long deptId) {
		LOG.info("getadept()");
		DepartmentPojo departmentPojo=deptService.getADepartment(deptId);
		RestClient restClient = RestClient.create();
		List<EmployeePojo> allEmps = restClient
				.get()
				.uri("http://employee-sr:8082/api/employees/departments/"+deptId)
				.retrieve()
				.body(List.class);
		departmentPojo.setAllEmployees(allEmps);
		return departmentPojo;


	}
	public DepartmentPojo empFallBack(){
		return new DepartmentPojo(0,"fallback",null);
	}
	
	@PostMapping("/departments")
	public DepartmentPojo addDepartment(@RequestBody DepartmentPojo newDept) {
		LOG.info("adddept()");
		return deptService.addDepartment(newDept);
	}
	
	@PutMapping("/departments")
	public DepartmentPojo updateDepartment(@RequestBody DepartmentPojo editDept) {
		LOG.info("updatedept()");
		return deptService.updateDepartment(editDept);
	}
	
	@DeleteMapping("/departments/{did}")
	public void removeDepartment(@PathVariable("did") long deptId) {
		deptService.deleteDepartment(deptId);
		LOG.info("removedept()");
	}
}
