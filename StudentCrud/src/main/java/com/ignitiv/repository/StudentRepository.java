package com.ignitiv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ignitiv.Entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	Student findByName(String name);
	Student findByRollNo(int rollNo);
	Student findById(int id);
}
