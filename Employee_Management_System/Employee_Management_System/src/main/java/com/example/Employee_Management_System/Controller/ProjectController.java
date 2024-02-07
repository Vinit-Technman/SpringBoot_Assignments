package com.example.Employee_Management_System.Controller;


import com.example.Employee_Management_System.ApiManager;
import com.example.Employee_Management_System.DTOs.ProjectDTO;
import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Model.Project;
import com.example.Employee_Management_System.Model.Status;
import com.example.Employee_Management_System.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController
{
    @Autowired
    ProjectService pro;

    @PostMapping("/createproject")
    public ApiManager<Project> createProject(@Validated @RequestBody Project p){
        return pro.createProject(p);
    }

    @GetMapping("/getproject/{id}")
    public ApiManager<Project> getProjectById(@PathVariable(value = "id")Long id){
        return pro.getProjectById(id);
    }


    @GetMapping("/getallprojectwithteam")
    public ApiManager<List<ProjectDTO>>getAllProjectsWithTeam(){
        return pro.getAllProjectsWithTeam();
    }

    @DeleteMapping("/deleteproject/{id}")
    public ApiManager<String> deleteProjectById(@PathVariable(value = "id")Long id){
        return pro.deleteProjectById(id);
    }

    @PutMapping("/{id}/status")
    public ApiManager<String> updateStatusById(@PathVariable(value = "id")Long id,@RequestParam Status status){
        return pro.updateStatusById(id,status);
    }

    @GetMapping("/budget/{id}")
    public ApiManager<Double> getBudgetOnEmployeeSalary(@PathVariable Long id){
        return pro.getBudgetOnEmployeeSalary(id);
    }

    @GetMapping("/status/new")
    public ApiManager<List<Project>>getNewProject(){
        return pro.getNewProject();
    }

    @GetMapping("/status/ended")
    public ApiManager<List<Project>>getEndedProject(){
        return pro.getEndedProject();
    }
    @GetMapping("/status/ongoing")
    public ApiManager<List<Project>>getOngoingProject(){
        return pro.getOnGoindProject();
    }
}
