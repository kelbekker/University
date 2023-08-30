package com.foxminded.universityapp.service;

import com.foxminded.universityapp.model.Teacher;
import com.foxminded.universityapp.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTeacherByFirstNameWhenNormalTeacherNameShouldReturnTeacher() {
        Teacher teacher = new Teacher(2L, "John", "Cristy");
        teacherRepository.save(teacher);

        when(teacherRepository.findByTeacherName(anyString())).thenReturn(teacher);

        Teacher resultTeacher = teacherService.getTeacherByFirstName("John");

        assertEquals(Long.valueOf(2L), resultTeacher.getId());
    }

    @Test
    public void createTeacherWhenNormalTeacherShouldReturnTeacher() {
        Teacher teacher = new Teacher();

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.createTeacher(teacher);

        assertEquals(teacher, result);
    }

    @Test
    public void getTeacherByIdWhenNormalTeacherIdShouldReturnTeacher() {
        Long teacherId = 1L;
        Teacher expectedTeacher = new Teacher();
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(expectedTeacher));

        Teacher result = teacherService.getTeacherById(teacherId);

        assertEquals(expectedTeacher, result);
    }

    @Test
    public void updateTeacherWhenNormalTeacherShouldReturnTeacher() {
        Teacher teacher = new Teacher();
        // Set properties of teacher object

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        Teacher result = teacherService.updateTeacher(teacher);

        assertEquals(teacher, result);
    }

    @Test
    public void deleteByIdWhenNormalTeacherIdShouldDeleteTeacher() {
        Long teacherId = 1L;

        teacherService.deleteById(teacherId);

        verify(teacherRepository, times(1)).deleteById(teacherId);
    }

    @Test
    public void getTeachersWhenNormalShouldReturnAllTeacher() {
        List<Teacher> expectedTeachers = new ArrayList<>();
        when(teacherRepository.findAll()).thenReturn(expectedTeachers);

        List<Teacher> result = teacherService.getTeachers();

        assertEquals(expectedTeachers, result);
    }
}