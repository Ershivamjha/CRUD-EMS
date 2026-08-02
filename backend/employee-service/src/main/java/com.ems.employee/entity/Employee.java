package com.ems.employee.entity;

//package com.ems.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employee_code",
                        columnNames = "employee_code"
                ),
                @UniqueConstraint(
                        name = "uk_employee_email",
                        columnNames = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code",
            nullable = false,
            length = 30)
    private String employeeCode;

    @Column(name = "first_name",
            nullable = false,
            length = 100)
    private String firstName;

    @Column(name = "last_name",
            length = 100)
    private String lastName;

    @Column(nullable = false,
            length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(precision = 12, scale = 2)
    private BigDecimal salary;

    @Column(name = "department_id",
            nullable = false)
    private Long departmentId;

}
