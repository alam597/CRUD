package com.example.CRUD.Services;

import com.example.CRUD.DTO.EmployeeDto;
import com.example.CRUD.Entity.Employee;
import com.example.CRUD.Exception.EmployeeNotFoundException;
import com.example.CRUD.Repository.Emp_Repo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Emp_Service {
    
    private final Emp_Repo empRepo;

    // CREATE (using DTO)
    public Employee saveEmployee(EmployeeDto dto) {
        Employee employee = new Employee(dto.getEmp_Id(), dto.getName());
        return empRepo.save(employee);
    }

    // GET ALL
    public List<Employee> getAllEmployees() {
        List<Employee> employees = empRepo.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found in the system");
        }
        return employees;
    }

    // GET BY ID
    public Employee getEmployeeById(Long Emp_id) {
        return empRepo.findById(Emp_id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + Emp_id + " not found"));
    }

    // UPDATE
    public Employee updateEmployee(Long Emp_id, Employee updatedEmployee) {
        Employee employee = empRepo.findById(Emp_id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + Emp_id + " not found"));

        employee.setName(updatedEmployee.getName());
        return empRepo.save(employee);
    }

    // DELETE
    public void deleteEmployee(Long Emp_id) {
        if (!empRepo.existsById(Emp_id)) {
            throw new EmployeeNotFoundException("Employee with ID " + Emp_id + " not found, cannot delete");
        }
        empRepo.deleteById(Emp_id);
    }
}
