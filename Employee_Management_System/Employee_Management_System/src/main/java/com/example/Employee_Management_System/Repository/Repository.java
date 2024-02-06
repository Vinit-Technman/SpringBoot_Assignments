package com.example.Employee_Management_System.Repository;

import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee,Long> {

    List<Employee> findTop3ByOrderBySalaryDesc();

//    List<Employee> findByDepartmentOrderBySalaryDesc(Department d);
}


