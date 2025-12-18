package com.ignitiv.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ignitiv.Entity.Student;
import com.ignitiv.repository.StudentRepository;

@Service
public class StudentService {
	
//	@Autowired
//	Student student;
	
	@Autowired
	StudentRepository studentRepository;
	
	public String addStudent(Student student) {
		studentRepository.save(student);
		return "Student added";	
	}
	
	public Student getStudentByName(String name) {
		Student stud = studentRepository.findByName(name);
		if(stud == null) {
			System.out.println("Student not found");
			return null;
		}
		return stud;
	}
	
	public String updateStudent(Student student, int rollNo) {
		Student stud = studentRepository.findByRollNo(rollNo);
		if(stud == null) {
			return "Student not found";
		}
		
		stud.setName(student.getName());
		stud.setRollNo(student.getRollNo());
		stud.setAddress(student.getAddress());
		stud.setDegree(student.getDegree());
		stud.setDepartment(student.getDepartment());
		stud.setMobNo(student.getMobNo());
		
		studentRepository.save(stud);
		return "Student Updated";
		
	}
	
	public String deleteStudent(int rollNo) {
		Student stud = studentRepository.findByRollNo(rollNo);
		if(stud == null) {
			return "Student not found";
		}
		studentRepository.delete(stud);
		return "Student Deleted";
	}
}
