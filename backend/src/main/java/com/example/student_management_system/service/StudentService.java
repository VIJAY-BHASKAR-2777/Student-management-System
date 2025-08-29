package com.example.student_management_system.service;

import com.example.student_management_system.exception.ResourceNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * =================================================================
 * Student Service
 * =================================================================
 * Operations:
 *  - Manage student records: create, read, update, delete (CRUD)
 *  - Enroll and unenroll students in courses
 *
 *  Methods:
 *  - getAllStudents(): Fetches all students.
 *  - getStudentById(Long id): Fetches a student by ID.
 *  - createStudent(Student): Creates a new student.
 *  - updateStudent(Long id, Student studentDetails): Updates an existing student.
 *  - deleteStudent(Long id): Deletes a student by ID.
 *  - enrollStudentInCourse(Long studentId, Long courseId): Enrolls a student in a course.
 *  - unenrollStudentFromCourse(Long studentId, Long courseId): Unenrolls a student from a course.
 * =================================================================
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Fetches all students from the database.
     * @return A list of all Student objects.
     */
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Fetches a single student by their unique ID.
     * @param id The ID of the student to find.
     * @return The found Student object.
     * @throws ResourceNotFoundException if the student with the specified ID does not exist.
     */
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    /**
     * Persists a new student record to the database.
     * @param student The Student object to be created.
     * @return The newly created Student object with its database-generated ID.
     */
    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Updates an existing student's information.
     * @param id The ID of the student to update.
     * @param studentDetails An object containing the new details for the student.
     * @return The updated Student object.
     * @throws ResourceNotFoundException if the student to update is not found.
     */
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id); // First, find the existing student.
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        return studentRepository.save(student); // Save the updated student.
    }

    /**
     * Deletes a student from the database based on their ID.
     * @param id The ID of the student to be deleted.
     * @throws ResourceNotFoundException if the student to delete is not found.
     */
    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id); // Ensures the student exists before attempting to delete.
        studentRepository.delete(student);
    }

    /**
     * Enrolls a student in a specific course.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return The updated Student object with the new course enrollment.
     * @throws ResourceNotFoundException if either the student or course is not found.
     */
    @Transactional
    public Student enrollStudentInCourse(Long studentId, Long courseId) {
        Student student = getStudentById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        student.getCourses().add(course); // Add the course to the student's set of courses.
        return studentRepository.save(student);
    }

    /**
     * Unenrolls a student from a specific course.
     * @param studentId The ID of the student.
     * @param courseId The ID of the course.
     * @return The updated Student object without the course enrollment.
     * @throws ResourceNotFoundException if either the student or course is not found.
     */
    @Transactional
    public Student unenrollStudentFromCourse(Long studentId, Long courseId) {
        Student student = getStudentById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        student.getCourses().remove(course); // Remove the course from the student's set.
        return studentRepository.save(student);
    }
}