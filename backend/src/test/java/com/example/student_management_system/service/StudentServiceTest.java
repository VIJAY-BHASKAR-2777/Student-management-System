package com.example.student_management_system.service;

import com.example.student_management_system.exception.ResourceNotFoundException;
import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.StudentRepository;
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
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private StudentService service;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setCourses(new HashSet<>());

        course = new Course();
        course.setId(100L);
        course.setName("Math");
    }

    @DisplayName("getAllStudents returns all students")
    @org.junit.jupiter.api.Test
    void getAllStudents_ReturnsAllStudents() {
        List<Student> students = Arrays.asList(student);
        when(studentRepository.findAll()).thenReturn(students);
        List<Student> result = service.getAllStudents();
        assertEquals(students, result);
    }

    @DisplayName("getStudentById returns student when exists")
    @org.junit.jupiter.api.Test
    void getStudentById_ReturnsStudent_WhenExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student result = service.getStudentById(1L);
        assertEquals(student, result);
    }

    @DisplayName("getStudentById throws exception when not found")
    @org.junit.jupiter.api.Test
    void getStudentById_ThrowsException_WhenNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getStudentById(2L));
    }

    @DisplayName("createStudent saves and returns student")
    @org.junit.jupiter.api.Test
    void createStudent_SavesAndReturnsStudent() {
        when(studentRepository.save(student)).thenReturn(student);
        Student result = service.createStudent(student);
        assertEquals(student, result);
    }

    @DisplayName("updateStudent updates fields and returns updated student")
    @org.junit.jupiter.api.Test
    void updateStudent_UpdatesFieldsAndReturnsUpdatedStudent() {
        Student details = new Student();
        details.setFirstName("Jane");
        details.setLastName("Smith");
        details.setEmail("jane.smith@example.com");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Student result = service.updateStudent(1L, details);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("jane.smith@example.com", result.getEmail());
    }

    @DisplayName("updateStudent throws exception when not found")
    @org.junit.jupiter.api.Test
    void updateStudent_ThrowsException_WhenNotFound() {
        Student details = new Student();
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateStudent(2L, details));
    }

    @DisplayName("deleteStudent deletes student when exists")
    @org.junit.jupiter.api.Test
    void deleteStudent_DeletesStudent_WhenExists() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        service.deleteStudent(1L);
        verify(studentRepository).delete(student);
    }

    @DisplayName("deleteStudent throws exception when not found")
    @org.junit.jupiter.api.Test
    void deleteStudent_ThrowsException_WhenNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteStudent(2L));
    }

    @DisplayName("enrollStudentInCourse adds course to student when both exist")
    @org.junit.jupiter.api.Test
    void enrollStudentInCourse_AddsCourseToStudent_WhenBothExist() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(100L)).thenReturn(Optional.of(course));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Student result = service.enrollStudentInCourse(1L, 100L);
        assertTrue(result.getCourses().contains(course));
    }

    @DisplayName("enrollStudentInCourse throws exception when student not found")
    @org.junit.jupiter.api.Test
    void enrollStudentInCourse_ThrowsException_WhenStudentNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.enrollStudentInCourse(2L, 100L));
    }

    @DisplayName("enrollStudentInCourse throws exception when course not found")
    @org.junit.jupiter.api.Test
    void enrollStudentInCourse_ThrowsException_WhenCourseNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.enrollStudentInCourse(1L, 200L));
    }

    @DisplayName("unenrollStudentFromCourse removes course from student when both exist")
    @org.junit.jupiter.api.Test
    void unenrollStudentFromCourse_RemovesCourseFromStudent_WhenBothExist() {
        student.getCourses().add(course);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(100L)).thenReturn(Optional.of(course));
        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Student result = service.unenrollStudentFromCourse(1L, 100L);
        assertFalse(result.getCourses().contains(course));
    }

    @DisplayName("unenrollStudentFromCourse throws exception when student not found")
    @org.junit.jupiter.api.Test
    void unenrollStudentFromCourse_ThrowsException_WhenStudentNotFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.unenrollStudentFromCourse(2L, 100L));
    }

    @DisplayName("unenrollStudentFromCourse throws exception when course not found")
    @org.junit.jupiter.api.Test
    void unenrollStudentFromCourse_ThrowsException_WhenCourseNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(200L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.unenrollStudentFromCourse(1L, 200L));
    }
}

