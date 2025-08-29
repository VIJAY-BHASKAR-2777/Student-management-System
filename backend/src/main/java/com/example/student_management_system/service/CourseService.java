package com.example.student_management_system.service;

import com.example.student_management_system.exception.ResourceNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * =================================================================
 * Course Service
 * =================================================================
 * Purpose:
 * This class contains the business logic for course-related operations.
 * It acts as an intermediary between the CourseController and the CourseRepository.
 * All operations are transactional to ensure data integrity.
 * =================================================================
 */


@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Retrieves a list of all courses from the database.
     * @return a List of Course objects.
     */
    @Transactional(readOnly = true) // A read-only transaction is more efficient for find operations.
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Creates and saves a new course to the database.
     * @param course The Course object to be saved.
     * @return The saved Course object, including its generated ID.
     */
    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Retrieves a single course by its ID.
     * @param id The ID of the course to retrieve.
     * @return The Course object if found.
     * @throws ResourceNotFoundException if no course with the given ID is found.
     */
    @Transactional(readOnly = true)
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
}