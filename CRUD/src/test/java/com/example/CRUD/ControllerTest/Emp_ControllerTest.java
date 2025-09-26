package com.example.CRUD.ControllerTest;

import com.example.CRUD.Controller.Emp_Controller;
import com.example.CRUD.DTO.EmployeeDto;
import com.example.CRUD.Entity.Employee;
import com.example.CRUD.Services.Emp_Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(Emp_Controller.class)
    class Emp_ControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private Emp_Service empService;

        @Autowired
        private ObjectMapper objectMapper;

        private Employee employee;
        private EmployeeDto employeeDto;

        @BeforeEach
        void setUp() {
            employee = new Employee();
            employee.setEmp_id(1L);
            employee.setName("John Doe");

        }

        // CREATE
        @Test
        void testCreateEmployee() throws Exception {
            String employeeJson = "{ \"emp_Id\": 1, \"name\": \"shahzaib\" }";

            mockMvc.perform(post("/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(employeeJson))
                    .andExpect(status().isCreated()); // expect 201
        }


        // GET ALL
        @Test
        void testGetAllEmployees() throws Exception {
            List<Employee> employees = Arrays.asList(employee);
            Mockito.when(empService.getAllEmployees()).thenReturn(employees);

            mockMvc.perform(get("/getEmployee"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].emp_id").value(1L))
                    .andExpect(jsonPath("$[0].name").value("John Doe"));
        }

        // GET BY ID
        @Test
        void testGetEmployeeById() throws Exception {
            Mockito.when(empService.getEmployeeById(1L)).thenReturn(employee);

            mockMvc.perform(get("/getEmployee/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.emp_id").value(1L))
                    .andExpect(jsonPath("$.name").value("John Doe"));
        }

        // UPDATE
        @Test
        void testUpdateEmployee() throws Exception {
            Employee updated = new Employee();
            updated.setEmp_id(1L);
            updated.setName("Updated Name");

            Mockito.when(empService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updated);

            mockMvc.perform(put("/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updated)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.emp_id").value(1L))
                    .andExpect(jsonPath("$.name").value("Updated Name"));
        }

        // DELETE
        @Test
        void testDeleteEmployee() throws Exception {
            Mockito.doNothing().when(empService).deleteEmployee(1L);

            mockMvc.perform(delete("/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Employee deleted with ID: 1"));
        }

}
