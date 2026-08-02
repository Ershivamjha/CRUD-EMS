package com.ems.employee.client;

//package com.ems.employee.client;

import com.ems.employee.dto.DepartmentResponse;
import com.ems.employee.fallback.DepartmentFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "DEPARTMENT-SERVICE",
        path = "/api/departments",
        fallback = DepartmentFallback.class
)
public interface DepartmentClient {

    @GetMapping("/{id}")
    DepartmentResponse getDepartmentById(
            @PathVariable("id") Long id);

}