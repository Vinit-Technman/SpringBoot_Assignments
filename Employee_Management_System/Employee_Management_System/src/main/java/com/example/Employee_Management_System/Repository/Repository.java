package com.example.Employee_Management_System.Repository;

import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Employee,Long> {

    List<Employee> findTop3ByOrderBySalaryDesc();

//    @Query(value = "SELECT * FROM employee.employee e WHERE e.department_id = ?1 ORDER BY e.salary DESC LIMIT 1", nativeQuery = true)
    @Query(value = "SELECT name,salary FROM employee WHERE department_id=:department_id order by salary DESC", nativeQuery = true)
    List<Object[]> findByDepartmentOrderBySalaryDesc(@Param("department_id") Long departmentId);
}


