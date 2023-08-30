package com.foxminded.universityapp.repository;

import com.foxminded.universityapp.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.foxminded.universityapp.model.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    @Query("SELECT s FROM Student s WHERE s.firstName = :studentName")
    Student findByStudentName(@Param("studentName") String studentName);
}
