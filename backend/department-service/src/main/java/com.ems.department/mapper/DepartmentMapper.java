package com.ems.department.mapper;

import com.ems.department.dto.DepartmentRequest;
import com.ems.department.dto.DepartmentResponse;
import com.ems.department.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    Department toEntity(DepartmentRequest request);

    DepartmentResponse toResponse(Department department);

    void updateEntity(
            DepartmentRequest request,
            @MappingTarget Department department
    );

}
