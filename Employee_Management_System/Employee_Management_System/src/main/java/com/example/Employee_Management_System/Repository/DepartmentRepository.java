package com.example.Employee_Management_System.Repository;

import com.example.Employee_Management_System.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
