package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.service.CourseService;
import com.foxminded.universityapp.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.ui.Model;
import com.foxminded.universityapp.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.support.BindingAwareModelMap;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"TEACHER"})
    void teacherListWithTeacherRoleWhenModelShouldReturnTeacherList() {
        Model model = new BindingAwareModelMap();

        String viewName = teacherController.teacherList(model);

        assertEquals("teacherList", viewName);
        assertTrue(model.containsAttribute("teachers"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void teacherListWithAdminRoleWhenModelShouldReturnTeacherList() {
        Model model = new BindingAwareModelMap();

        String viewName = teacherController.teacherList(model);

        assertEquals("teacherList", viewName);
        assertTrue(model.containsAttribute("teachers"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void teacherManagerWithAdminRoleShouldReturnTeacherManager() {
        String viewName = teacherController.teacherManager();

        assertEquals("teacherManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findTeacherByIdWithAdminRoleWhenNormalIdAndModelShouldReturnTeacher() {
        Long teacherId = 1L;
        Model model = new BindingAwareModelMap();

        String viewName = teacherController.findTeacherById(teacherId, model);

        assertEquals("teacher", viewName);
        assertTrue(model.containsAttribute("teacher"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addTeacherWithAdminRoleWhenTeacherShouldReturnTeacherManager() {
        Model model = new BindingAwareModelMap();
        Teacher teacher = new Teacher();

        String viewName = teacherController.addTeacher(teacher);

        assertEquals("teacherManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateTeacherWithAdminRoleWhenTeacherShouldReturnTeacherManager() {
        Model model = new BindingAwareModelMap();
        Teacher teacher = new Teacher();

        String viewName = teacherController.updateTeacher(teacher);

        assertEquals("teacherManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteTeacherByIdWithAdminRoleWhenNormalIdShouldReturnTeacherManager() {
        Long teacherId = 1L;

        String viewName = teacherController.deleteTeacherById(teacherId);

        assertEquals("teacherManage", viewName);
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    public void getScheduleByTeacherIdWithTeacherRoleWhenNormalUserDetailAndModelShouldReturnSchedule() {
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

        Model model = mock(Model.class);

        Teacher teacher = new Teacher(1L, "testTeacher", "");
        Course course1 = new Course(1L, "Course 1",
                LocalDateTime.of(2023, 9, 1, 10, 0), new Student(), teacher);
        Course course2 = new Course(2L, "Course 2",
                LocalDateTime.of(2023, 9, 1, 10, 0), new Student(), teacher);

        when(teacherService.getTeacherByFirstName(userDetails.getUsername())).thenReturn(teacher);
        when(courseService.getCoursesByStudentId(teacher.getId())).thenReturn(Arrays.asList(course1, course2));

        when(model.addAttribute(eq("courses"), anyList())).thenReturn(model);

        String viewName = teacherController.getScheduleByTeacherId(userDetails, model);

        assertEquals("schedule", viewName);
    }
}
