package com.ems.employee.service;

//package com.ems.employee.service;

import com.ems.employee.dto.EmployeeDetailsResponse;
import com.ems.employee.dto.EmployeeRequest;
import com.ems.employee.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    EmployeeDetailsResponse getEmployeeWithDepartment(Long id);

    EmployeeResponse updateEmployee(Long id,
                                    EmployeeRequest request);

    void deleteEmployee(Long id);

    Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir);

}
