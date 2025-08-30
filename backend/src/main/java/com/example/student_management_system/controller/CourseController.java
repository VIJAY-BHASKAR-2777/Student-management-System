package com.example.student_management_system.controller;

import com.example.student_management_system.model.Course;
import com.example.student_management_system.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =================================================================
 * Course Controller
 * =================================================================
 * Purpose:
 * This class serves as the API endpoint for all course-related operations.
 * It handles incoming HTTP requests from the client (our Angular app),
 * delegates the business logic to the CourseService, and returns structured
 * JSON responses.
 * =================================================================
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    @Autowired // Injects the CourseService to handle business logic.
    private CourseService courseService;

    /**
     * Handles GET requests to /api/courses.
     * Fetches and returns a list of all courses.
     * @return A List of Course objects.
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    /**
     * Handles POST requests to /api/courses.
     * Creates a new course based on the provided request body.
     * @param course The Course object from the request body. @Valid triggers validation.
     * @return A ResponseEntity containing the newly created Course and an HTTP status of 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
}
