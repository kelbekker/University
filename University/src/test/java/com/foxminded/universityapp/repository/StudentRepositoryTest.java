package com.foxminded.universityapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.foxminded.universityapp.model.Student;
import org.testng.AssertJUnit;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		StudentRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepository;

	@BeforeEach
	public void setup() {
		Student student = new Student(2L, "Agata", "Cristy");
		studentRepository.save(student);
	}

	@Test
	void findByStudentNameWhenNormalStudentNameShouldReturnStudent() throws SQLException {
		Student student = studentRepository.findByStudentName("Agata");

		AssertJUnit.assertEquals(Long.valueOf(2L), student.getId());
		AssertJUnit.assertEquals("Agata", student.getFirstName());
		AssertJUnit.assertEquals("Cristy", student.getLastName());
	}
}