package com.ems.employee.dto;

//package com.ems.employee.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private BigDecimal salary;

    private Long departmentId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
