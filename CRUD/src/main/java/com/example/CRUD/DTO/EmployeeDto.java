package com.example.CRUD.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class EmployeeDto {

    @NotNull(message = "Employee ID cannot be null")
    private Long emp_Id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    public EmployeeDto(Long empId, String name) {
        this.emp_Id = empId;
        this.name = name;
    }


    public Long getEmp_Id() {
        return emp_Id;
    }

    public void setEmp_Id(Long emp_Id) {
        this.emp_Id = emp_Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
