package com.ems.department.service;

import com.ems.department.dto.DepartmentRequest;
import com.ems.department.dto.DepartmentResponse;
import com.ems.department.entity.Department;
import com.ems.department.exception.DepartmentAlreadyExistsException;
import com.ems.department.exception.DepartmentNotFoundException;
import com.ems.department.mapper.DepartmentMapper;
import com.ems.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository repository;

    private final DepartmentMapper mapper;

    @Override
    public DepartmentResponse createDepartment(
            DepartmentRequest request) {

        if (repository.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DepartmentAlreadyExistsException(
                    "Department code already exists.");
        }

        Department department = mapper.toEntity(request);

        Department saved = repository.save(department);

        return mapper.toResponse(saved);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id : " + id));

        return mapper.toResponse(department);
    }

    @Override
    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest request) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id : " + id));

        mapper.updateEntity(request, department);

        Department updated = repository.save(department);

        return mapper.toResponse(updated);
    }

    @Override
    public void deleteDepartment(Long id) {

        Department department = repository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id : " + id));

        repository.delete(department);
    }

    @Override
    public Page<DepartmentResponse> getAllDepartments(
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
