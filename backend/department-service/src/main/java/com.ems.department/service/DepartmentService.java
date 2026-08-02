package com.ems.department.service;
import com.ems.department.dto.DepartmentRequest;
import com.ems.department.dto.DepartmentResponse;
import org.springframework.data.domain.Page;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    void deleteDepartment(Long id);

    Page<DepartmentResponse> getAllDepartments(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

}
