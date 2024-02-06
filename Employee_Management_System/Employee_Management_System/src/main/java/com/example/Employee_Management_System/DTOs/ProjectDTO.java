package com.example.Employee_Management_System.DTOs;

import com.example.Employee_Management_System.Model.Employee;
import com.example.Employee_Management_System.Model.Project;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private List<String> teamNames;
//    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.teamNames = project.getTeam().stream().map(Employee::getName).toList();
//        this.status = project.getStatus();
        this.startDate = project.getStart_date();
        this.endDate = project.getEnd_date();
    }

}
