package com.example.CRUD.Services;

import com.example.CRUD.DTO.EmployeeDto;
import com.example.CRUD.Entity.Employee;
import com.example.CRUD.Exception.EmployeeNotFoundException;
import com.example.CRUD.Repository.Emp_Repo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class Emp_ServiceTest {

    @Mock
    private Emp_Repo empRepo;

    @InjectMocks
    private Emp_Service empService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // CREATE
    @Test
    void testSaveEmployee() {
        EmployeeDto dto = new EmployeeDto(1L, "John");
        Employee savedEmployee = new Employee(1L, "John");

        when(empRepo.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee result = empService.saveEmployee(dto);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(empRepo, times(1)).save(any(Employee.class));
    }

    // GET ALL - success
    @Test
    void testGetAllEmployees_Success() {
        List<Employee> employees = Arrays.asList(new Employee(1L, "John"), new Employee(2L, "Doe"));
        when(empRepo.findAll()).thenReturn(employees);

        List<Employee> result = empService.getAllEmployees();

        assertEquals(2, result.size());
        verify(empRepo, times(1)).findAll();
    }

    // GET ALL - exception
    @Test
    void testGetAllEmployees_ThrowsExceptionWhenEmpty() {
        when(empRepo.findAll()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> empService.getAllEmployees());

        assertEquals("No employees found in the system", exception.getMessage());
        verify(empRepo, times(1)).findAll();
    }

    // GET BY ID - success
    @Test
    void testGetEmployeeById_Success() {
        Employee employee = new Employee(1L, "John");
        when(empRepo.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = empService.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(empRepo, times(1)).findById(1L);
    }

    // GET BY ID - exception
    @Test
    void testGetEmployeeById_ThrowsException() {
        when(empRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> empService.getEmployeeById(1L));

        assertEquals("Employee with ID 1 not found", exception.getMessage());
        verify(empRepo, times(1)).findById(1L);
    }

    // UPDATE - success
    @Test
    void testUpdateEmployee_Success() {
        Employee existing = new Employee(1L, "OldName");
        Employee updated = new Employee(1L, "NewName");

        when(empRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(empRepo.save(any(Employee.class))).thenReturn(updated);

        Employee result = empService.updateEmployee(1L, updated);

        assertNotNull(result);
        assertEquals("NewName", result.getName());
        verify(empRepo, times(1)).findById(1L);
        verify(empRepo, times(1)).save(existing);
    }

    // UPDATE - exception
    @Test
    void testUpdateEmployee_ThrowsException() {
        Employee updated = new Employee(1L, "NewName");
        when(empRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> empService.updateEmployee(1L, updated));

        assertEquals("Employee with ID 1 not found", exception.getMessage());
        verify(empRepo, times(1)).findById(1L);
        verify(empRepo, never()).save(any(Employee.class));
    }

    // DELETE - success
    @Test
    void testDeleteEmployee_Success() {
        when(empRepo.existsById(1L)).thenReturn(true);
        doNothing().when(empRepo).deleteById(1L);

        empService.deleteEmployee(1L);

        verify(empRepo, times(1)).existsById(1L);
        verify(empRepo, times(1)).deleteById(1L);
    }

    // DELETE - exception
    @Test
    void testDeleteEmployee_ThrowsException() {
        when(empRepo.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EmployeeNotFoundException.class,
                () -> empService.deleteEmployee(1L));

        assertEquals("Employee with ID 1 not found, cannot delete", exception.getMessage());
        verify(empRepo, times(1)).existsById(1L);
        verify(empRepo, never()).deleteById(anyLong());
    }
}
