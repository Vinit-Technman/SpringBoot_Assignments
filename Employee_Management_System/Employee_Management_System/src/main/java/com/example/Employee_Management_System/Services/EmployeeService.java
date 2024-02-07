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
    public ApiManager<List<Employee>> getEmployees(){
        try {
            return new ApiManager<>(rep.findAll(),HttpStatus.OK,"Retrieved All Employees Data");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }

    }

    public ApiManager<Employee> createEmployee(Employee emp){
        try{
            rep.save(emp);
        return new ApiManager<>(emp,HttpStatus.OK,"Employee Created Successfully");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Employee> updateEmployeeById(Long EmployeeId,Employee updatedEmployeeData){
        try{

        Optional<Employee>employee = rep.findById(EmployeeId);
        if(employee.isPresent()) {

            employee.get().setName(updatedEmployeeData.getName());
            employee.get().setSalary(updatedEmployeeData.getSalary());
            employee.get().setAddress(updatedEmployeeData.getAddress());
            Employee e = rep.save(employee.get());
            return new ApiManager<>(e,HttpStatus.OK,"Employee Data updated Successfully");
        }
        return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","No Employee ");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Employee> assignDepttoEmp(Long EmployeeId,Long DepartmentId){

        try{
            Optional<Department> dp = dep.findById(DepartmentId);
            Optional<Employee>emp = rep.findById(EmployeeId);
            if (dp.isPresent() && emp.isPresent()) {
                Employee updatedEmployee=emp.get();
                System.out.println(updatedEmployee.getId());
                updatedEmployee.setDid(dp.get());
                List<Employee> empList = dp.get().getEmpList();
                empList.add(updatedEmployee);
                dep.save(dp.get());
                rep.save(updatedEmployee);
                return new ApiManager<>(updatedEmployee, HttpStatus.OK, "Employee assigned to department having did: " + DepartmentId);
            }
            return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","Employee or Department is Not Exist");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Employee> getEmployeeById(@PathVariable(value="id") Long empId){
        try{
        Optional<Employee>getEmp=rep.findById(empId);
        if(getEmp.isPresent())
        {
            return new ApiManager<>(getEmp.get(),HttpStatus.OK,"Employee Data Retrieved with empId: "+empId);
        }
        else
        {
            return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Found","Employee not Found with id:"+empId);
        }
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<String> deleteEmployeeById(@PathVariable(value="id")Long empId){
        try
        {
            Optional<Employee>emp=rep.findById(empId);
            if(emp.isPresent())
            {
        rep.deleteById(empId);
        return new ApiManager<>("Success",HttpStatus.OK,"Employee Deleted Successfully");
            }
            else {
                return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","No Employee Exist with id: "+empId);
            }
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
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
