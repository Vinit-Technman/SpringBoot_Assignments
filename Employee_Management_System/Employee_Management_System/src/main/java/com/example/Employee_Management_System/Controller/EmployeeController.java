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
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService emp;
    DepartmentService dept;
    @GetMapping("/allemployee")
    public ApiManager<List<Employee>> getEmployee() {
        return emp.getEmployees();
    }

    @PostMapping("/createemployee")
    public ApiManager<Employee> createEmployee(@Validated @RequestBody Employee e) {
        return emp.createEmployee(e);
    }


    @PutMapping("/updateemployee/{id}")
    public ApiManager<Employee> updateEmployee(@PathVariable(value = "id") Long id,@RequestBody Employee details) {
        return emp.updateEmployeeById(id, details);
    }

    @PutMapping("/assigndepartment/{id}/{did}")
    public ApiManager<Employee> assignDept(@PathVariable(value = "id")Long id,@PathVariable(value="did") Long did)
    {
        return emp.assignDepttoEmp(id,did);
    }

    @GetMapping("/getemployee/{id}")
    public ApiManager<Employee> getEmployeeById(@PathVariable(value="id")Long id){
        return emp.getEmployeeById(id);
    }

    @DeleteMapping("/deleteemployee/{id}")
    public ApiManager<String> deleteEmployeeById(@PathVariable(value = "id")Long id){
        return emp.deleteEmployeeById(id);
    }

    @GetMapping("/getdepartment/{id}")
    public ApiManager<Department> getDepartmentDetailsByEmpId(@PathVariable(value ="id")Long empId)
    {
    return emp.getDepartmentByEmpId(empId);
    }

    @PutMapping("/{empid}/project/{p_id}")
    public ApiManager<Employee> assignProjectToEmployee(@PathVariable(value="empid") Long empId,@PathVariable(value="p_id") Long ProId )
    {
        return emp.assignProjectToEmployee(empId,ProId);
    }

    @GetMapping("/highest-salary")
    public ApiManager<Employee>getHighestSalary(){
        return emp.getHighestSalary();
    }

    @GetMapping("/second-highest-salary-holds-by-department")
    public ApiManager<Map<String, Object[]>>getSecondHighestSalaryHoldsByDepartment(){
        return emp.getSecondHighestSalaryHolderGroupByDepartment();
    }
}