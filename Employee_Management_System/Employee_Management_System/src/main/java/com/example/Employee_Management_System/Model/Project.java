package com.example.Employee_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="employee",referencedColumnName = "name")
    private List<Employee> team;

    private String Team_lead;


    public enum Status{
        NEW,ON_GOING,ENDED
    }
    private Date start_date;

    private Date end_date;
//    Status enum
//    NEW - By default the Project status will be new till its start_date is after today.
//    ON-GOING - If the today is between start and end date
//    ENDED - if the today is after end-date
//            Start_date

//    End_date
}
