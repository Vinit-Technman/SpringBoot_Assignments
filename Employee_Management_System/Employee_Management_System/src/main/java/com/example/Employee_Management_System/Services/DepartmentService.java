package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.Controller.DepartmentController;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Repository.DepartmentRepository;
import com.example.Employee_Management_System.Repository.Repository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository rep;

    public Department createDepartment(@Validated @RequestBody Department d)
    {
        return rep.save(d);
    }
    public List<Department> getDepartments(){
        return rep.findAll();
    }

    public Department updateDepartment(@PathVariable(value = "did") Long DepartmentId, @RequestBody Department deptDetails){
        Department d=rep.findById(DepartmentId).orElseThrow(()->new ResourceNotFoundException("No Department Exist With id:"+DepartmentId));

        d.setName(deptDetails.getName());

        Department updatedDept=rep.save(d);

        return updatedDept;
    }

//    public void addEmployeeInDepartment(Employee emp,Long did){
//        Optional<Department> dept=rep.findById(did);
//        if(dept.isPresent()) {
//            List<Employee> empList = dept.get().getEmpList();
//            empList.add(emp);
//            rep.save(dept.get());
//        }
//    }

    public ApiManager<List<Employee>> getDepartmentWithEmployeeData(){
        List<Department> departments=rep.findAll();
//        if(departments.isPresent())
//        {

//        List<Department> listOfDeptData=departments.stream().map().collect(Collectors.toList());
//        ApiManager<Department> listOfDeptData;
//        }
        List<Employee> data=departments.stream().flatMap(e->e.getEmpList().stream()).collect(Collectors.toList());
        return new ApiManager<List<Employee>> (data,HttpStatus.OK,"Data Received");
//        return new ApiManager<>(HttpStatus.OK,"Data not found","Department Data not found ");
    }

//    public ApiManager<Department> DeleteDepartmentById(Long id){
//
//        Optional<Department> dept=rep.findById(id);
//        if(dept.isPresent()){
//
//        }
//
//    }
}
