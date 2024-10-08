package com.demo.department_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentPojo {
	private long deptId;
	private String deptName;
	private List<EmployeePojo> allEmployees;

	public void setAllEmployees(List<EmployeePojo> allEmployees) {
		this.allEmployees = allEmployees;
	}
}
