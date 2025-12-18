package com.ignitiv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ignitiv.Entity.Student;
import com.ignitiv.service.StudentService;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@PostMapping("/add")
	public String addStudent(@RequestBody Student stud) {
		return studentService.addStudent(stud);
	}
	
	@PutMapping("/update/{rollNo}")
	public String updateStudent(@RequestBody Student stud, @PathVariable int rollNo) {
		return studentService.updateStudent(stud, rollNo);
	}
	
	@DeleteMapping("/delete/{rollNo}")
	public String deleteStudent(@PathVariable int rollNo) {
		return studentService.deleteStudent(rollNo);
	}
	
	@GetMapping("/get")
	public Student getStudent(@RequestParam String name) {
		return studentService.getStudentByName(name);
	}
}
