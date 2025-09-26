package com.example.CRUD.Exception;

public class EmployeeNotFoundException extends RuntimeException {

    // Constructor with ID
    public EmployeeNotFoundException(Long ID) {
        super("Employee not found with ID: " + ID);
    }

    // Constructor with custom message
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
