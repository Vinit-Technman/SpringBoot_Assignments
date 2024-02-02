package com.example.Employee_Management_System.Repository;

import com.example.Employee_Management_System.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee,Long> {

}
