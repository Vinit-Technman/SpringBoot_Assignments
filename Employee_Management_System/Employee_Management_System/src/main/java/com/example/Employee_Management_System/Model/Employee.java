package com.example.Employee_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private long Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  double salary;


    private String designation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="department_id",referencedColumnName = "id")
    private Department did;

    private String address;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="project_employee",
            joinColumns = @JoinColumn(name="project_id"),
    inverseJoinColumns = @JoinColumn(name="employee_id"))

    private List<Project> projects=new ArrayList<>();
}
