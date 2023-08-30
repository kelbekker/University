package com.foxminded.universityapp.repository;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import static org.testng.AssertJUnit.assertEquals;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        CourseRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@BeforeEach
	public void setup() {
		Teacher teacher = new Teacher(1L, "Tom", "Kesh");
		teacherRepository.save(teacher);
		Student student = new Student(2L, "Agata", "Cristy");
		studentRepository.save(student);

		Course course = new Course();
		course.setId(4L);
		course.setName("Physics");
		course.setDate(LocalDateTime.of(2023, 9, 1, 10, 0));
		course.setTeacher(teacher);
		course.setStudent(student);
		courseRepository.save(course);
	}

	@Test
	void findByTeacherIdWhenNormalTeacherIdShouldReturnCourseList() throws SQLException {
		List<Course> teacherCoursesList = courseRepository.findByTeacherId(1L);

		assertEquals(1, teacherCoursesList.size());
		assertEquals("Tom", teacherCoursesList.get(0).getTeacher().getFirstName());
		assertEquals("Physics", teacherCoursesList.get(0).getName());
	}

	@Test
	void findByStudentIdWhenNormalStudentIdShouldReturnCourseList() throws SQLException {
		List<Course> teacherCoursesList = courseRepository.findByStudentId(2L);

		assertEquals(1, teacherCoursesList.size());
		assertEquals("Agata", teacherCoursesList.get(0).getStudent().getFirstName());
		assertEquals("Physics", teacherCoursesList.get(0).getName());
	}
}
