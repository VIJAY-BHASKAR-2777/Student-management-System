package com.example.student_management_system.controller;

import com.example.student_management_system.model.Student;
import com.example.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * =================================================================
 * Student Controller
 * =================================================================
 * Purpose:
 * This class is the main API endpoint for managing students. It exposes
 * RESTful services for all student-related CRUD operations as well as
 * for handling course enrollments.
 * =================================================================
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Handles GET requests to /api/students.
     * @return A list of all students.
     */
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    /**
     * Handles GET requests to /api/students/{id}.
     * @param id The ID of the student, extracted from the URL path.
     * @return A ResponseEntity containing the found Student.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * Handles POST requests to /api/students.
     * @param student The Student object from the request body.
     * @return A ResponseEntity with the created Student and HTTP status 201.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    /**
     * Handles PUT requests to /api/students/{id}.
     * @param id The ID of the student to update.
     * @param studentDetails The updated student data.
     * @return A ResponseEntity with the updated Student.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Handles DELETE requests to /api/students/{id}.
     * @param id The ID of the student to delete.
     * @return A ResponseEntity with no content and HTTP status 204.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles POST requests to /api/students/{studentId}/enroll/{courseId}.
     * Enrolls a student in a course.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return A ResponseEntity with the updated Student object.
     */
    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Student> enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student updatedStudent = studentService.enrollStudentInCourse(studentId, courseId);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * Handles DELETE requests to /api/students/{studentId}/unenroll/{courseId}.
     * Unenrolls a student from a course.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return A ResponseEntity with the updated Student object.
     */
    @DeleteMapping("/{studentId}/unenroll/{courseId}")
    public ResponseEntity<Student> unenrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student updatedStudent = studentService.unenrollStudentFromCourse(studentId, courseId);
        return ResponseEntity.ok(updatedStudent);
    }
}
