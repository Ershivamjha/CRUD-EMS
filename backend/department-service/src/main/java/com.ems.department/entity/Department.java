package com.ems.department.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "departments",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_department_code",
                        columnNames = "department_code")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code",
            nullable = false,
            length = 20)
    private String departmentCode;

    @Column(name = "department_name",
            nullable = false,
            length = 100)
    private String departmentName;

    @Column(length = 500)
    private String description;

    @Column(length = 100)
    private String location;

}
