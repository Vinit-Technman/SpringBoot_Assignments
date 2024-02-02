package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {

    @Autowired
    Repository rep;

    public List<Employee> getEmployees(){
        return rep.findAll();
    }

}
