package com.foxminded.universityapp.service;

import com.foxminded.universityapp.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.repository.StudentRepository;
import java.util.List;
@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Transactional
	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	@Transactional
	public Student getStudentById(long id) {
		return studentRepository.findById(id).orElse(null);
	}

	@Transactional
	public Student updateStudent(Student student) {
		return studentRepository.save(student);
	}

	@Transactional
	public void deleteById(Long id) {
		studentRepository.deleteById(id);
	}

	@Transactional
	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public Student getStudentByFirstName(String studentName) {
		return studentRepository.findByStudentName(studentName);
	}

}