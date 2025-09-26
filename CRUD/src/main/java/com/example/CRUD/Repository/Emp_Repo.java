package com.example.CRUD.Repository;


import com.example.CRUD.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Emp_Repo extends JpaRepository<Employee,Long> {

}
