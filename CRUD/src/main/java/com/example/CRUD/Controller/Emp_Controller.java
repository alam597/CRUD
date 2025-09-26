package com.example.CRUD.Controller;

import com.example.CRUD.DTO.EmployeeDto;
import com.example.CRUD.Entity.Employee;
import com.example.CRUD.Services.Emp_Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Emp_Controller {


    private final Emp_Service employeeService;

//    create

    @PostMapping("/add")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        Employee saved = employeeService.saveEmployee(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


//   get
    @GetMapping("/getEmployee")
    public List<Employee> getAllEmployees() {
    return employeeService.getAllEmployees();
}

    //  GET by ID
    @GetMapping("/getEmployee/{Emp_id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long Emp_id) {
        Employee employee = employeeService.getEmployeeById(Emp_id);
        return ResponseEntity.ok(employee);
    }

    // UPDATE
    @PutMapping("/{Emp_id}")
    public Employee updateEmployee(@PathVariable Long Emp_id, @RequestBody Employee updatedEmployee) {
        return employeeService.updateEmployee(Emp_id, updatedEmployee);
    }

    @DeleteMapping("/{Emp_id}")
    public String deleteEmployee(@PathVariable Long Emp_id) {
        employeeService.deleteEmployee(Emp_id);
        return "Employee deleted with ID: " + Emp_id;
    }

}
