package com.example.Employee_Management_System.Controller;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService dep;

    //Create GET api to get all Department data (it will not have employee data in it)
    @GetMapping("/getdepartment")
    public List<Department> getDepartment(){
        return dep.getDepartments();
    }
//    Create POST api to create a department
    @PostMapping("/createdepartment")
    public Department createDepartment(@Validated @RequestBody Department d){
        return dep.createDepartment(d);
    }

//    Create PUT api to update the department data
    @PutMapping("/updatedepartment/{id}")
    public Department updateDepartment(@PathVariable(value = "id") Long id, @RequestBody Department details){
        return dep.updateDepartment(id,details);
    }

    //Create GET api to get department data along with all the employees(employee data) into it.
    @GetMapping("/getdepartmentwithemployee")
    public ApiManager<List<Employee>> getDepartmentWithEmployee(){
        return dep.getDepartmentWithEmployeeData();
    }

//    Create delete API to delete department,we can not delete department if an employee
//    is in the department show error that employee belongs to this department can’t delete
//    @DeleteMapping("/deleteDepartment")
//    public ApiManager<Department> DeleteDepartmentById(Long id){
//
//    }
}
