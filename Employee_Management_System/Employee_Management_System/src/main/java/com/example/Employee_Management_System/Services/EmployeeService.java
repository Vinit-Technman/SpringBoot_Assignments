package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Repository.DepartmentRepository;
import com.example.Employee_Management_System.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    Repository rep;
    @Autowired
    DepartmentRepository dep;

    public List<Employee> getEmployees(){
        return rep.findAll();
    }

    public Employee createEmployee(Employee e){

        return rep.save(e);
    }

    public Employee updateEmployeeById(Long EmployeeId,Long DepartmentId){

        Department dp=dep.findById(DepartmentId).get();
        Employee updatedEmployee=rep.findById(EmployeeId).get();
        System.out.println(updatedEmployee.getId());
        updatedEmployee.setDid(dp);
        return rep.save(updatedEmployee);
    }

    public Employee assignDepttoEmp(Long EmployeeId,Long DepartmentId){

        Optional<Department> dp=dep.findById(DepartmentId);
        if(dp.isPresent()) {
            Employee updatedEmployee = rep.findById(EmployeeId).get();
            System.out.println(updatedEmployee.getId());
            updatedEmployee.setDid(dp.get());
            List<Employee> empList = dp.get().getEmpList();
            empList.add(updatedEmployee);
            dep.save(dp.get());
            return rep.save(updatedEmployee);
        }
        return null;
    }

    public Employee getEmployeeById(@PathVariable(value="id") Long empId){
        Employee getEmp=rep.findById(empId).orElseThrow(()->new ResourceNotFoundException("Employee not Found with id:"+empId));
        return getEmp;
    }

    public String deleteEmployeeById(@PathVariable(value="id")Long empId){
        rep.deleteById(empId);
        return "Employee Deleted Successfully";
    }

    public ApiManager<Department> getDepartmentByEmpId(Long id){
        Optional<Employee> emp=rep.findById(id);
        if(emp.isPresent())
        {
            Department dept=emp.get().getDid();
            return new ApiManager<>(dept,HttpStatus.OK,"Department Data Received");
        }
        return new ApiManager<>(HttpStatus.NOT_FOUND,"Employee not found","Not Data found with id: "+id);
    }
}
