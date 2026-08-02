package com.ems.employee.service;
//package com.ems.employee.service;

import com.ems.employee.client.DepartmentClient;
import com.ems.employee.dto.DepartmentResponse;
import com.ems.employee.dto.EmployeeDetailsResponse;
import com.ems.employee.dto.EmployeeRequest;
import com.ems.employee.dto.EmployeeResponse;
import com.ems.employee.entity.Employee;
import com.ems.employee.exception.EmployeeAlreadyExistsException;
import com.ems.employee.exception.EmployeeNotFoundException;
import com.ems.employee.mapper.EmployeeMapper;
import com.ems.employee.repository.EmployeeRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private final DepartmentClient departmentClient;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        if (repository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new EmployeeAlreadyExistsException(
                    "Employee code already exists");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmployeeAlreadyExistsException(
                    "Email already exists");
        }

        Employee employee = mapper.toEntity(request);

        return mapper.toResponse(
                repository.save(employee));
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found : " + id));

        return mapper.toResponse(employee);
    }

    @Override
    @CircuitBreaker(
            name = "departmentService",
            fallbackMethod = "departmentFallback")
    public EmployeeDetailsResponse getEmployeeWithDepartment(
            Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found : " + id));

        DepartmentResponse department =
                departmentClient.getDepartmentById(
                        employee.getDepartmentId());

        return EmployeeDetailsResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .department(department)
                .build();
    }

    public EmployeeDetailsResponse departmentFallback(
            Long id,
            Exception ex) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found : " + id));

        return EmployeeDetailsResponse.builder()
                .id(employee.getId())
                .employeeCode(employee.getEmployeeCode())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .department(
                        DepartmentResponse.builder()
                                .id(employee.getDepartmentId())
                                .departmentCode("N/A")
                                .departmentName("Unavailable")
                                .description("Department service unavailable")
                                .location("Unknown")
                                .build())
                .build();
    }

    @Override
    public EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found : " + id));

        mapper.updateEntity(request, employee);

        return mapper.toResponse(
                repository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found : " + id));

        repository.delete(employee);
    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

}
