package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.service.CourseService;
import com.foxminded.universityapp.service.StudentService;
import com.foxminded.universityapp.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {
    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private ModelMapper modelMapper;

    @Mock
    private Model model;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void courseListWhenModelShouldReturnCourseList() {
        Model model = new BindingAwareModelMap();

        String viewName = courseController.getCourseList(model);

        assertEquals("courseList", viewName);
        assertTrue(model.containsAttribute("courses"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void courseManageWithAdminRoleWhenModelShouldReturnCourseManage() {
        Model model = new BindingAwareModelMap();

        String viewName = courseController.courseManage(model);

        assertEquals("courseManage", viewName);
        assertTrue(model.containsAttribute("course"));
        assertTrue(model.containsAttribute("students"));
        assertTrue(model.containsAttribute("teachers"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void findCourseByIdWithAdminRoleWhenNormalIdAndModelShouldReturnCourse() {
        Long courseId = 1L;
        Model model = new BindingAwareModelMap();

        String viewName = courseController.findCourseById(courseId, model);

        assertEquals("course", viewName);
        assertTrue(model.containsAttribute("course"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void addCourseWithAdminRoleWhenCourseShouldReturnCourseManage() {
        Model model = new BindingAwareModelMap();
        Course course = new Course();

        String viewName = courseController.addCourse(course);

        assertEquals("courseManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void updateCourseWithAdminRoleWhenCourseShouldReturnCourseManage() {
        Model model = new BindingAwareModelMap();
        Course course = new Course();

        String viewName = courseController.updateCourse(course);

        assertEquals("courseManage", viewName);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteCourseByIdWithAdminRoleWhenNormalIdShouldReturnCourseManage() {
        Long courseId = 1L;

        String viewName = courseController.deleteCourseById(courseId);

        assertEquals("courseManage", viewName);
    }

    @Test
    void getCoursePublicListWhenModelShouldReturnPublicCourseList() {
        Model model = new BindingAwareModelMap();

        String viewName = courseController.getCoursePublicList(model);

        assertEquals("coursePublicList", viewName);
        assertTrue(model.containsAttribute("courses"));
    }
}
