package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.service.StudentService;
import com.foxminded.universityapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.foxminded.universityapp.service.CourseService;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user-management/courses/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole(Role.ADMIN.name()) or hasRole(Role.STUDENT.name()) or hasRole(Role.TEACHER.name())")
    @GetMapping("/all-courses")
    public String getCourseList(Model model) {
        List<Course> courses = courseService.getCourses().stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        return "courseList";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @RequestMapping
    public String courseManage(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("students", studentService.getStudents());
        model.addAttribute("teachers", teacherService.getTeachers());
        return "courseManage";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/course-id")
    public String findCourseById(@RequestParam Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "course";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/add")
    public String addCourse(@ModelAttribute Course course) {
        courseService.createCourse(course);
        return "courseManage";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/update")
    public String updateCourse(@ModelAttribute Course course) {
        courseService.updateCourse(course);
        return "courseManage";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/delete")
    public String deleteCourseById(Long id) {
        courseService.deleteById(id);
        return "courseManage";
    }

    @GetMapping("/public-courses")
    public String getCoursePublicList(Model model) {
        List<Course> courses = courseService.getCourses().stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        return "coursePublicList";
    }
}

