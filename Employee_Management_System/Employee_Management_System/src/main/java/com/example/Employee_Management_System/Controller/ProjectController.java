package com.example.Employee_Management_System.Controller;


import com.example.Employee_Management_System.Model.Project;
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
    public Project createProject(@Validated @RequestBody Project p){
        return pro.createProject(p);
    }

    @GetMapping("/getproject/{id}")
    public Project getProjectById(@PathVariable(value = "id")Long id){
        return pro.getProjectById(id);
    }

    @GetMapping("/getallproject")
    public List<Project> getAllProject(){
        return pro.getAllProjects();
    }
}
