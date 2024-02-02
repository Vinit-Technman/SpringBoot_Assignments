package com.example.Employee_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

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
    @JoinColumn(name="department",referencedColumnName = "id")
    private Department dId;

    private String address;
}
