package com.foxminded.universityapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.repository.CourseRepository;
import java.util.List;
@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public void createCourse(Course course) {
		courseRepository.save(course);
	}

	public Course getCourseById(Long id) {
		return courseRepository.findById(id).orElse(null);
	}

	public Course updateCourse(Course course) {
		return courseRepository.save(course);
	}

	public void deleteById(Long id) {
		courseRepository.deleteById(id);
	}

	public List<Course> getCourses() {
		return courseRepository.findAll();
	}

	public List<Course> getCoursesByTeacherId(Long id) {
		return courseRepository.findByTeacherId(id);
	}

	public List<Course> getCoursesByStudentId(Long id) {
		return courseRepository.findByStudentId(id);
	}
}
