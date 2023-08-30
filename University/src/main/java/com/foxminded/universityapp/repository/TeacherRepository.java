package com.foxminded.universityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.foxminded.universityapp.model.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{

    @Query("SELECT t FROM Teacher t WHERE t.firstName = :teacherName")
    Teacher findByTeacherName(@Param("teacherName") String teacherName);
}
