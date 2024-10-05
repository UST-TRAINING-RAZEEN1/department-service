package com.demo.department_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePojo {
    private Long employeeId;
    private String employeeName;
    private Long deptId;
}
