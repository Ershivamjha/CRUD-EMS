package com.ems.employee.fallback;

//package com.ems.employee.fallback;

import com.ems.employee.client.DepartmentClient;
import com.ems.employee.dto.DepartmentResponse;
import org.springframework.stereotype.Component;

@Component
public class DepartmentFallback implements DepartmentClient {

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        return DepartmentResponse.builder()
                .id(id)
                .departmentCode("N/A")
                .departmentName("Department Service Unavailable")
                .description("Fallback Response")
                .location("Unknown")
                .build();

    }
}
