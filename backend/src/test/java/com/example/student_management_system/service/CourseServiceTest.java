package com.example.student_management_system.service;

import com.example.student_management_system.exception.ResourceNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private CourseService service;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setName("Math");
    }

    @DisplayName("getAllCourses returns all courses")
    @org.junit.jupiter.api.Test
    void getAllCourses_ReturnsAllCourses() {
        List<Course> courses = Arrays.asList(course);
        when(courseRepository.findAll()).thenReturn(courses);
        List<Course> result = service.getAllCourses();
        assertEquals(courses, result);
    }

    @DisplayName("createCourse saves and returns course")
    @org.junit.jupiter.api.Test
    void createCourse_SavesAndReturnsCourse() {
        when(courseRepository.save(course)).thenReturn(course);
        Course result = service.createCourse(course);
        assertEquals(course, result);
    }

    @DisplayName("getCourseById returns course when exists")
    @org.junit.jupiter.api.Test
    void getCourseById_ReturnsCourse_WhenExists() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Course result = service.getCourseById(1L);
        assertEquals(course, result);
    }

    @DisplayName("getCourseById throws exception when not found")
    @org.junit.jupiter.api.Test
    void getCourseById_ThrowsException_WhenNotFound() {
        when(courseRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getCourseById(2L));
    }
}

