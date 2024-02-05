package com.example.Employee_Management_System.Services;

import com.example.Employee_Management_System.Model.Project;
import com.example.Employee_Management_System.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository rep;

    public Project createProject(@Validated @RequestBody Project p){
        return rep.save(p);
    }

    public Project getProjectById(@PathVariable(value = "id")Long id){
        return rep.findById(id).orElseThrow(()->new ResourceNotFoundException("Project Not Exists with id: "+id));
    }

    public List<Project> getAllProjects(){
        return rep.findAll();
    }
}
