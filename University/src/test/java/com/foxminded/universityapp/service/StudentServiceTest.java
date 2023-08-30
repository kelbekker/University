package com.foxminded.universityapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.foxminded.universityapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.foxminded.universityapp.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getStudentByFirstNameWhenNormalStudentNameShouldReturnStudent() {
        Student student = new Student(2L, "John", "Cristy");

        when(studentRepository.findByStudentName(anyString())).thenReturn(student);

        Student resultStudent = studentService.getStudentByFirstName("John");

        assertEquals(Long.valueOf(2L), resultStudent.getId());
    }

    @Test
    public void createStudentWhenNormalStudentShouldReturnStudent() {
        Student student = new Student();

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createStudent(student);

        assertEquals(student, result);
    }

    @Test
    public void getStudentByIdWhenNormalStudentIdShouldReturnStudent() {
        long studentId = 1L;
        Student expectedStudent = new Student();
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

        Student result = studentService.getStudentById(studentId);

        assertEquals(expectedStudent, result);
    }

    @Test
    public void updateStudentWhenNormalStudentShouldReturnStudent() {
        Student student = new Student();

        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.updateStudent(student);

        assertEquals(student, result);
    }

    @Test
    public void deleteByIdWhenNormalStudentIdShouldDeleteStudent() {
        Long studentId = 1L;

        studentService.deleteById(studentId);

        verify(studentRepository, times(1)).deleteById(studentId);
    }

    @Test
    public void getStudentsWhenNormalShouldReturnAllStudents() {
        List<Student> expectedStudents = new ArrayList<>();
        when(studentRepository.findAll()).thenReturn(expectedStudents);

        List<Student> result = studentService.getStudents();

        assertEquals(expectedStudents, result);
    }
}