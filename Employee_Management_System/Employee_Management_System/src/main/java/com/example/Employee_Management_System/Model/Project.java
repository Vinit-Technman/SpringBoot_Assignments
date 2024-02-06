package com.example.Employee_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @JsonIgnore
    @ManyToMany(mappedBy ="projects")
    private List<Employee> team=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public Status status;

    private LocalDate start_date;

    private LocalDate end_date;

//    @PrePersist
//    @PreUpdate
//    private void updateStatus(){
//        LocalDate currentDate=LocalDate.now();
//
//        if(start_date!=null && end_date!=null){
//            if(currentDate.isBefore(start_date)){
//                status=Status.NEW;
//            }
//            else if(currentDate.isAfter(end_date)){
//                status=Status.ENDED;
//            }
//            else{
//                status=Status.ON_GOING;
//            }
//        }
//    }

}
