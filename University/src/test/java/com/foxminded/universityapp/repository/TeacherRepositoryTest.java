package com.foxminded.universityapp.repository;

import com.foxminded.universityapp.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.testng.AssertJUnit;
import java.sql.SQLException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		TeacherRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {

	@Autowired
	private TeacherRepository teacherRepository;

	@BeforeEach
	public void setup() {
		Teacher teacher = new Teacher(1L, "Tom", "Kesh");
		teacherRepository.save(teacher);
	}

	@Test
	void findByTeacherNameWhenNormalTeacherNameShouldReturnTeacher() throws SQLException {
		Teacher teacher = teacherRepository.findByTeacherName("Tom");

		AssertJUnit.assertEquals(Long.valueOf(1L), teacher.getId());
		AssertJUnit.assertEquals("Tom", teacher.getFirstName());
		AssertJUnit.assertEquals("Kesh", teacher.getLastName());
	}
}