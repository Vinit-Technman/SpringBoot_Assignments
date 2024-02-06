package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Model.Project;
import com.example.Employee_Management_System.Repository.DepartmentRepository;
import com.example.Employee_Management_System.Repository.ProjectRepository;
import com.example.Employee_Management_System.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    Repository rep;
    @Autowired
    DepartmentRepository dep;

    @Autowired
    ProjectRepository proRep;
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
        try
        {

        Optional<Employee> emp=rep.findById(id);
        if(emp.isPresent())
        {
            Department dept=emp.get().getDid();
            return new ApiManager<>(dept,HttpStatus.OK,"Department Data Received");
        }
        return new ApiManager<>(HttpStatus.NOT_FOUND,"Employee not found","Not Data found with id: "+id);
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Employee>assignProjectToEmployee(Long empId,Long p_id){
        try{
            System.out.println("HH");
            Optional<Employee> employeeOptional = rep.findById(empId);
            Optional<Project> projectOptional = proRep.findById(p_id);
            if(employeeOptional.isPresent() && projectOptional.isPresent()){
                Employee emp=employeeOptional.get();
                Project pro=projectOptional.get();
                emp.getProjects().add(pro);
                Employee savedEmp=rep.save(emp);
                return new ApiManager<>(savedEmp,HttpStatus.OK,"Project Assigned to Employee");
            }
            else{
                return new ApiManager<>(HttpStatus.NOT_FOUND, "Employee or project not found", "Employee or project with the ID not found");
            }
        }catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Employee>getHighestSalary(){
        try{
            List<Employee> HighestSalary=rep.findTop3ByOrderBySalaryDesc();
            System.out.println(HighestSalary.get(0));
            return new ApiManager<>(HighestSalary.get(0), HttpStatus.OK,"Highest Salary Received");

        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Map<String,Employee>> getSecondHighestSalaryHolderGroupByDepartment(){
        try{
        Map<String,Employee> secondHighestEmployee=new HashMap<>();
        List<Department> deptList=dep.findAll();
        for(Department dept:deptList){
            System.out.println(dept.getName());
            if(!dept.getEmpList().isEmpty())
            {
                System.out.println("1");
//                List<Employee> employeeList=rep.findByDepartmentOrderBySalaryDesc(dept);
//            if(employeeList.size()>1)
//            {
//                secondHighestEmployee.put(dept.getName(),employeeList.get(1));
//            }
                            }
        }
            return new ApiManager<>(secondHighestEmployee,HttpStatus.OK,"Second Highest Salary Holds By Department found");

        }catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }
}
