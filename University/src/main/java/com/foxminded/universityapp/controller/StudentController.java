package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Student;
import com.foxminded.universityapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import com.foxminded.universityapp.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user-management/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole(Role.STUDENT.name()) or hasRole(Role.ADMIN.name())")
    @GetMapping("students/all-students")
    public String studentList(Model model) {
        List<Student> studentDTOs = studentService.getStudents().stream()
                .map(student -> modelMapper.map(student, Student.class))
                .collect(Collectors.toList());

        model.addAttribute("students", studentDTOs);
        return "studentList";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @GetMapping("/admin/students")
    public String studentManager() {
        return "studentManage";
    }

    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/students/student-id")
    public String findStudentById(Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/students/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.createStudent(student);
        return "studentManage";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/students/update")
    public String updateStudent(@ModelAttribute Student student) {
        studentService.updateStudent(student);
        return "studentManage";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/students/delete")
    public String deleteStudentById( Long id) {
        studentService.deleteById(id);
        return "studentManage";
    }

    @PreAuthorize("hasRole(Role.STUDENT.name())")
    @GetMapping("/students/schedule")
    public String getScheduleByStudentId(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Long studentId = studentService.getStudentByFirstName(userDetails.getUsername()).getId();
        List<Course> courses = courseService.getCoursesByStudentId(studentId).stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());

        model.addAttribute("courses", courses);
        return "schedule";
    }
}