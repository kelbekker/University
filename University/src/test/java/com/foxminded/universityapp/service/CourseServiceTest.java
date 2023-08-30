package com.foxminded.universityapp.service;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.model.Teacher;
import com.foxminded.universityapp.repository.CourseRepository;
import com.foxminded.universityapp.repository.StudentRepository;
import com.foxminded.universityapp.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCoursesByTeacherIdWhenNormalIdShouldReturnCourseList() {
        Teacher teacher = new Teacher(1L, "Tom", "Kesh");
        Student student = new Student(2L, "Agata", "Cristy");

        Course course = new Course();
        course.setId(4L);
        course.setName("Physics");
        course.setDate(LocalDateTime.of(2023, 9, 1, 10, 0));
        course.setTeacher(teacher);
        course.setStudent(student);

        when(courseRepository.findByTeacherId(1L)).thenReturn(List.of(course));

        List<Course> resultCourses = courseService.getCoursesByTeacherId(1L);

        assertEquals(1, resultCourses.size());
        assertEquals("Physics", resultCourses.get(0).getName());
    }

    @Test
    public void getCoursesByStudentIdWhenNormalIdShouldReturnCourseList() {
        Teacher teacher = new Teacher(1L, "Tom", "Kesh");
        Student student = new Student(2L, "Agata", "Cristy");

        Course course = new Course();
        course.setId(4L);
        course.setName("Physics");
        course.setDate(LocalDateTime.of(2023, 9, 1, 10, 0));
        course.setTeacher(teacher);
        course.setStudent(student);

        when(courseRepository.findByStudentId(2L)).thenReturn(List.of(course));

        List<Course> resultCourses = courseService.getCoursesByStudentId(2L);

        assertEquals(1, resultCourses.size());
        assertEquals("Physics", resultCourses.get(0).getName());
    }

    @Test
    public void createCourseWhenNormalCourseShouldReturnCourse() {
        Course course = new Course();

        courseService.createCourse(course);

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void getCourseByIdWhenNormalCourseIdShouldReturnCourse() {
        Long courseId = 1L;
        Course expectedCourse = new Course();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(expectedCourse));

        Course result = courseService.getCourseById(courseId);

        assertEquals(expectedCourse, result);
    }

    @Test
    public void updateCourseWhenNormalCourseShouldReturnCourse() {
        Course course = new Course();

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.updateCourse(course);

        assertEquals(course, result);
    }

    @Test
    public void deleteByIdWhenNormalCourseIdShouldDeleteCourse() {
        Long courseId = 1L;

        courseService.deleteById(courseId);

        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void getCoursesWhenNormalShouldReturnAllCourses() {
        List<Course> expectedCourses = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(expectedCourses);

        List<Course> result = courseService.getCourses();

        assertEquals(expectedCourses, result);
    }
}
