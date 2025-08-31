package com.example.student_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * =================================================================
 * Student Entity
 * =================================================================
 * Purpose:
 * This class represents a "Student" in our application. As a JPA Entity,
 * it is mapped to a database table named "student". Each instance of this
 * class corresponds to a single row in that table.
 *
 * How it will be used:
 * - The `StudentRepository` will use this entity for all database operations.
 * - The `StudentService` will use it to handle business logic.
 * - The `StudentController` will accept and return `Student` objects (as JSON) in API requests.
 * - It manages the relationship with the `Course` entity, defining which student is enrolled in which courses.
 * =================================================================
 */
@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    // Validation annotations to ensure email is not empty and has a valid format.
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    /* This annotation defines a many-to-many relationship between Student and Course.
     * One student can enroll in many courses, and one course can have many students.
     * - fetch = FetchType.LAZY: This is a performance optimization. The 'courses' data will only be loaded from the database when it's explicitly accessed.
     * - cascade: Defines how operations on a Student affect its associated Courses.
    */
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "student_courses", // The name of the linking table in the database.
            joinColumns = { @JoinColumn(name = "student_id") }, // The foreign key column in the linking table that refers to the Student.
            inverseJoinColumns = { @JoinColumn(name = "course_id") } // The foreign key column that refers to the Course.
    )
    private Set<Course> courses = new HashSet<>();
}