package com.ems.employee.exception;

//package com.ems.employee.exception;

public class EmployeeNotFoundException
        extends RuntimeException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }

}
