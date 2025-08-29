package com.example.student_management_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * =================================================================
 *                            Course Entity
 * =================================================================
 * Purpose:
 * This class represents a "Course" in our application. It is a JPA Entity,
 * which means it directly maps to a database table named "course". Each instance
 * of this class corresponds to a single row in that table.
 *
 * How it will be used:
 * - The `CourseRepository` will use this entity to perform CRUD (Create, Read, Update, Delete) operations.
 * - The `Student` entity uses it to establish a many-to-many relationship, allowing us to see which students are enrolled in which courses.
 * - It will be converted to JSON and sent to the frontend to display course information.
 * =================================================================
 */

@Entity // Specifies that this class is an entity and is mapped to a database table.
@Data   // A Lombok annotation that automatically generates boilerplate code like getters, setters, toString(), etc.
public class Course {


    @Id
    // @GeneratedValue specifies that the ID should be generated automatically.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Course name cannot be empty")
    private String name;

    // This defines the "many" side of the many-to-many relationship with the Student entity.
    // 'mappedBy = "courses"' indicates that the Student class is the owner of this relationship
    // (the 'courses' field in the Student class defines the join table).
    @ManyToMany(mappedBy = "courses")
    // @JsonIgnore is crucial here. It tells the JSON serializer to ignore this field when converting the object to JSON.
    // This prevents an infinite recursion loop where Student references Course and Course references Student, causing a stack overflow.
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
}