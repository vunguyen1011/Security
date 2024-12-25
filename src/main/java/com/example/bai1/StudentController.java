/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bai1;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/students")
public class StudentController {
    private List<Student> students =new ArrayList<>(List.of(
            new Student(1,"Vũ"),
            new Student(2, "Vũ2")
    ));
    @GetMapping
    public List<Student> getAll(){
        return students;
    }
    @PostMapping
    public  void addStudent(Student student){
        students.add(student);
    }
}
