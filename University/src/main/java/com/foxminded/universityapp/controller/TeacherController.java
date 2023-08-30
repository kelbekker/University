package com.foxminded.universityapp.controller;

import com.foxminded.universityapp.model.Course;
import com.foxminded.universityapp.model.Teacher;
import com.foxminded.universityapp.service.CourseService;
import com.foxminded.universityapp.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole(Role.TEACHER.name(), Role.ADMIN.name())")
    @GetMapping("/teachers/all-teachers")
    public String teacherList(Model model) {
        List<Teacher> teacherDTOs = teacherService.getTeachers().stream()
                .map(teacher -> modelMapper.map(teacher, Teacher.class))
                .collect(Collectors.toList());

        model.addAttribute("teachers", teacherDTOs);
        return "teacherList";
    }
    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @GetMapping("/admin/teachers")
    public String teacherManager() {return "teacherManage";}

    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/teachers/teacher-id")
    public String findTeacherById(@RequestParam Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "teacher";
    }

    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/teachers/add")
    public String addTeacher(@ModelAttribute Teacher teacher) {
        teacherService.createTeacher(teacher);
        return "teacherManage";
    }

    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/teachers/update")
    public String updateTeacher(@ModelAttribute Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return "teacherManage";
    }

    @PreAuthorize("hasRole(Role.ADMIN.name())")
    @PostMapping("/admin/teachers/delete")
    public String deleteTeacherById(Long id) {
        teacherService.deleteById(id);
        return "teacherManage";
    }

    @PreAuthorize("hasRole(Role.TEACHER.name())")
    @GetMapping("/teachers/schedule")
    public String getScheduleByTeacherId(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String teacherName = userDetails.getUsername();
        Teacher teacher = teacherService.getTeacherByFirstName(teacherName);
        Long teacherId = teacher.getId();
        List<Course> courseDTOs = courseService.getCoursesByTeacherId(teacherId).stream()
                .map(course -> modelMapper.map(course, Course.class))
                .collect(Collectors.toList());

        model.addAttribute("courses", courseDTOs);
        return "schedule";
    }
}
