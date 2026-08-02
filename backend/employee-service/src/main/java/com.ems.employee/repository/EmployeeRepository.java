package com.ems.employee.repository;

//package com.ems.employee.repository;

import com.ems.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentId(Long departmentId);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

}
