package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.model.Teacher;
import com.foxminded.universityapp.service.CourseService;
import com.foxminded.universityapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
@WithMockUser(username = "student", roles = { "STUDENT" })
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @InjectMocks
    private StudentController studentController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"STUDENT"})
    void studentListWithStudentRoleShouldReturnStudents() {
        Model model = new BindingAwareModelMap();

        String viewName = studentController.studentList(model);

        assertEquals("studentList", viewName);
        assertTrue(model.containsAttribute("students"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void studentListWithAdminRoleShouldReturnStudents() {
        Model model = new BindingAwareModelMap();

        String viewName = studentController.studentList(model);

        assertEquals("studentList", viewName);
        assertTrue(model.containsAttribute("students"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void studentManagerWithAdminRoleShouldReturnStudentManage() {
        String viewName = studentController.studentManager();

        assertEquals("studentManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findStudentByIdWithAdminRoleShouldReturnStudentManage() {
        Long studentId = 1L;
        Model model = new BindingAwareModelMap();

        String viewName = studentController.findStudentById(studentId, model);

        assertEquals("student", viewName);
        assertTrue(model.containsAttribute("student"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addStudentWithAdminRoleWhenStudentShouldReturnStudentManage() {
        Model model = new BindingAwareModelMap();
        Student student = new Student();

        String viewName = studentController.addStudent(student);

        assertEquals("studentManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateStudentWithAdminRoleWhenStudentShouldReturnStudentManage() {
        Model model = new BindingAwareModelMap();
        Student student = new Student();

        String viewName = studentController.updateStudent(student);

        assertEquals("studentManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteStudentByIdWithAdminRoleWhenNormalIdShouldReturnStudentManage() {
        Long studentId = 1L;

        String viewName = studentController.deleteStudentById(studentId);

        assertEquals("studentManage", viewName);
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void getScheduleByStudentIdWithStudentRoleWhenNormalUserDetailAndModelShouldReturnSchedule() {
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        Model model = mock(Model.class);

        Student student = new Student(1L, "testUser", "");
        Course course1 = new Course(1L, "Course 1",
                LocalDateTime.of(2023, 9, 1, 10, 0), student, new Teacher());
        Course course2 = new Course(2L, "Course 2",
                LocalDateTime.of(2023, 9, 1, 10, 0), student, new Teacher());

        when(studentService.getStudentByFirstName(userDetails.getUsername())).thenReturn(student);
        when(courseService.getCoursesByStudentId(student.getId())).thenReturn(Arrays.asList(course1, course2));

        when(model.addAttribute(eq("courses"), anyList())).thenReturn(model);

        String viewName = studentController.getScheduleByStudentId(userDetails, model);

        assertEquals("schedule", viewName);
    }
}
