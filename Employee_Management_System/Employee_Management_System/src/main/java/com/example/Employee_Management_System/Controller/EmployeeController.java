package com.example.Employee_Management_System.Controller;

import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService emp;
@GetMapping("/allemployee")
    public List<Employee> getEmployee(){
    return emp.getEmployees();
}

}
