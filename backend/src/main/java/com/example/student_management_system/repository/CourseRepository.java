package com.example.student_management_system.repository;

import com.example.student_management_system.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * =================================================================
 * Course Repository
 * =================================================================
 * Purpose:
 * This interface defines the data access layer for the Course entity.
 * It is responsible for all database interactions related to courses.
 * =================================================================
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
