package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.DTOs.ProjectDTO;
import com.example.Employee_Management_System.Model.Department;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Model.Project;
import com.example.Employee_Management_System.Model.Status;
import com.example.Employee_Management_System.Repository.ProjectRepository;
import com.example.Employee_Management_System.Repository.Repository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository rep;

    @Autowired
    Repository empRep;
    public ApiManager<Project> createProject(Project p){
        try{
            Project project=rep.save(p);
            return new ApiManager<>(project,HttpStatus.OK,"Project Created Successfully");
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Project> getProjectById(Long id){
        try{
            Optional<Project>project=rep.findById(id);
            if(project.isPresent())
            {
                return new ApiManager<>(project.get(),HttpStatus.OK,"Project Data Retrieved Successfully");
            }
            else
        return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","Project Not Exists with id: "+id);
        }
        catch (Exception e)
        {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

//    public ApiManager<Project> assignProjectToTeam(Long id, List<Long> empIds){
//        Optional<Project>p=rep.findById(id);
//        if(p.isPresent())
//        {
//            List<Employee>empList=empIds.stream().map(eId->empRep.findById(eId).orElseThrow(()->new ResourceNotFoundException("Employee not found with id:"+eId))).collect(Collectors.toList());
//            System.out.println(empList);
//            p.get().getTeam().addAll(empList);
//
//            return new ApiManager<>(p.get(),HttpStatus.OK,"Team Members added Successfully");
////                    .orElseThrow(()->new ApiManager<Project>(HttpStatus.NOT_FOUND,"No Data Found","No Employee exist with id:"+eId)))
//
//
//        }
//        return new ApiManager<>(HttpStatus.NOT_FOUND,"Project not found","No Project Data Found with id:"+id);
//
//    }

    public List<Project> getAllProjects(){
        return rep.findAll();
    }

    public ApiManager<List<ProjectDTO>> getAllProjectsWithTeam(){
        try{
            List<Project>projects=rep.findAll();
            List<ProjectDTO> projectDTOs = projects.stream().map(ProjectDTO::new).distinct().collect(Collectors.toList());
            return new ApiManager<>(projectDTOs,HttpStatus.OK,"Data Received Successfully");

//            List<String,Object> employees=empRep.findAll().stream().flatMap(e->e.getProjects().stream()
//                    .map(project->{
//                        Map<String,Object> pMap=new HashMap<>();
//                        pMap.put("projectId",project.getId());
//                        pMap.put("projectName",project.getName());
//                        pMap.put("employeeId",e.getId());
//                        pMap.put("employeeName",e.getName());
//                        return pMap;
//                    }))
//            List<Project>empList=empRep.findAll().stream().flatMap(e->e.getProjects().distinct().collect(Collectors.toList());
//            List<Project>TeamProjects=projects.stream().map(e->e.getTeam()).toList().stream().map(Employee::getName).toList()).collect(Collectors.toList());
//            return new ApiManager<>(projects,HttpStatus.OK,"Data Received");

        }
        catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<String> deleteProjectById(Long id){
        try{
            Optional<Project>pro=rep.findById(id);
            if(pro.isPresent()){

                Project project=pro.get();
                if(project.getEnd_date().isBefore(LocalDate.now())) {
                    List<Employee> emp = project.getTeam();
                    for (Employee employee : emp) {
                        employee.getProjects().remove(project);
                        empRep.save(employee);
                    }
                    rep.deleteById(id);
                    return new ApiManager<>("Project Deleted Successfully",HttpStatus.OK,"Delete the Project having id: "+id);
                }
                else{
                    return new ApiManager<>(HttpStatus.BAD_REQUEST,"Can't Delete the Project","You Can't Delete the Project because It is still Running.");
                }
            }
            else{
                return new ApiManager<>(HttpStatus.NOT_FOUND,"Not Found","No Project found with id:"+id);
            }
        }
        catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<String>updateStatusById(Long id,Status status){
        try
        {

            Optional<Project>pro=rep.findById(id);
        if(pro.isPresent()){
            Project project=pro.get();
            project.setStatus(status);
            rep.save(project);

            return new ApiManager<>("Status Updated",HttpStatus.OK,"Status Updated Successfully");
        }
        else {
            return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","Can't Find the Project with id: "+id);
        }
        }
        catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }
    }

    public ApiManager<Double>getBudgetOnEmployeeSalary(Long id){
        try{
            Optional<Project> pro = rep.findById(id);
            if(pro.isPresent()) {
                Project project=pro.get();
                double totalSalary=project.getTeam().stream().mapToDouble(Employee::getSalary).sum();

                return new ApiManager<>(totalSalary,HttpStatus.OK,"Total Budget for Project: "+project.getName());
            } else {
                return new ApiManager<>(HttpStatus.NOT_FOUND,"Data Not Found","Can't Find the Project with id: "+id);
            }
        }
        catch (Exception e){
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),"Internal Server Error");
        }

    }

    public ApiManager<List<Project>>getNewProject(){
        try{
        List<Project>projects=rep.findByStatus(Status.NEW);
        return new ApiManager<>(projects,HttpStatus.OK,"NEW Projects Data Retrieved");
        }
        catch (Exception e) {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Internal Server Error");
        }
        }
    public ApiManager<List<Project>>getOnGoindProject(){
        try{
            List<Project>projects=rep.findByStatus(Status.ON_GOING);
            return new ApiManager<>(projects,HttpStatus.OK,"On Going Projects Data Retrieved");
        }
        catch (Exception e) {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Internal Server Error");
        }
    }
    public ApiManager<List<Project>>getEndedProject(){
        try{
            List<Project>projects=rep.findByStatus(Status.ENDED);
            return new ApiManager<>(projects,HttpStatus.OK,"Ended Projects Data Retrieved");
        }
        catch (Exception e) {
            return new ApiManager<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "Internal Server Error");
        }
    }
}
