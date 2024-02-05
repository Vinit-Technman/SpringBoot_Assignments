package com.example.Employee_Management_System.Controller;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Services.DepartmentService;
import com.example.Employee_Management_System.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService emp;
    DepartmentService dept;
    @GetMapping("/allemployee")
    public List<Employee> getEmployee() {
        return emp.getEmployees();
    }

    @PostMapping("/createemployee")
    public Employee createEmployee(@Validated @RequestBody Employee e) {
        return emp.createEmployee(e);
    }


    @PutMapping("/updateemployee/{id}/{did}")
    public Employee updateEmployee(@PathVariable(value = "id") Long id, @PathVariable(value = "did") Long details) {
        return emp.updateEmployeeById(id, details);
    }

    @PutMapping("/assigndepartment/{id}/{did}")
    public Employee assignDept(@PathVariable(value = "id")Long id,@PathVariable(value="did") Long did)
    {
        return emp.assignDepttoEmp(id,did);
    }

    @GetMapping("/getemployee/{id}")
    public Employee getEmployeeById(@PathVariable(value="id")Long id){
        return emp.getEmployeeById(id);
    }

    @DeleteMapping("/deleteemployee/{id}")
    public String deleteEmployeeById(@PathVariable(value = "id")Long id){
        return emp.deleteEmployeeById(id);
    }

    @GetMapping("/getdepartment/{id}")
    public ApiManager<Department> getDepartmentDetailsByEmpId(@PathVariable(value ="id")Long empId)
    {
    return emp.getDepartmentByEmpId(empId);
    }
}