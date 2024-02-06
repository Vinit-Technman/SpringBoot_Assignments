package com.example.Employee_Management_System.Repository;

import com.example.Employee_Management_System.Model.Project;
import com.example.Employee_Management_System.Model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByStatus(Status status);
}
