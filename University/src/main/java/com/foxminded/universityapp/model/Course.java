package com.foxminded.universityapp.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
        @Id
        @Column(name = "id")
        Long id;

        @Column(name = "name", length = 50)
        String name;

        @Column(name = "date")
        LocalDateTime date;

        @ManyToOne
        @JoinColumn(name = "student_id")
        Student student;

        @ManyToOne
        @JoinColumn(name = "teacher_id")
        Teacher teacher;
}
