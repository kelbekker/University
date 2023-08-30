package com.foxminded.universityapp.service;

import java.util.List;

import com.foxminded.universityapp.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.foxminded.universityapp.model.Teacher;
import com.foxminded.universityapp.repository.TeacherRepository;
@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRepository;

	public Teacher createTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	public Teacher getTeacherById(Long id) {
		return teacherRepository.findById(id).orElse(null);
	}

	public Teacher updateTeacher(Teacher teacher) {
		return teacherRepository.save(teacher);
	}

	public void deleteById(Long id) {
		teacherRepository.deleteById(id);
	}
	
	public List<Teacher> getTeachers() {
		return teacherRepository.findAll();
	}

	public Teacher getTeacherByFirstName(String teacherName) {
		return teacherRepository.findByTeacherName(teacherName);
	}
}
