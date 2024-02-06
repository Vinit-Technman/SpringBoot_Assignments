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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<Employee> data=departments.stream().flatMap(e->e.getEmpList().stream()).collect(Collectors.toList());
        return new ApiManager<List<Employee>> (data,HttpStatus.OK,"Data Received");
//        return new ApiManager<>(HttpStatus.OK,"Data not found","Department Data not found ");
    }

    public ApiManager<Department> deleteDepartmentById(Long id) {
        try {
            Optional<Department> dept = rep.findById(id);
            if (dept.isPresent()) {
                List<Employee> employeeList = dept.get().getEmpList();
                if (!employeeList.isEmpty()) {
                    return new ApiManager<>(HttpStatus.BAD_REQUEST, "Error", "Employees are exist in department with id: " + id);
                }
                rep.deleteById(id);
                return new ApiManager<>(HttpStatus.OK, "Success", "Department Deleted Successfully with dept id: " + id);
            }
            else{
                return new ApiManager<>(HttpStatus.NOT_FOUND,"No Data Found","No Department exists with id: "+id);
            }
        }
        catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Map<String,Double>> getTotalSalaryEachDepartment(){
        try{
            Map<String,Double> TotalSalary=new HashMap<>();
            List<Department>departmentList=rep.findAll();
            for(Department depart:departmentList){
                String departmentName=depart.getName();
                List<Employee>empList=depart.getEmpList();
                double totalSalary=empList.stream().mapToDouble(Employee::getSalary).sum();
                TotalSalary.put(departmentName,totalSalary);
            }
            return new ApiManager<>(TotalSalary,HttpStatus.OK,"Total salary by department retrieved successfully");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }
}
