package com.example.student_management_system.config;


import com.example.student_management_system.model.Course;
import com.example.student_management_system.model.Student;
import com.example.student_management_system.repository.CourseRepository;
import com.example.student_management_system.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, StudentRepository studentRepository) {
        return args -> {
            // Create and save courses
            Course course1 = new Course();
            course1.setName("Introduction to Programming");
            course1.setCourseCode("CS101");
            course1.setProfessor("Dr. Ada Lovelace");
            course1.setDescription("Learn the fundamentals of programming using Java.");
            course1.setCredits(3);

            Course course2 = new Course();
            course2.setName("Calculus I");
            course2.setCourseCode("MATH201");
            course2.setProfessor("Dr. Isaac Newton");
            course2.setDescription("An introduction to differential calculus.");
            course2.setCredits(4);

            Course course3 = new Course();
            course3.setName("World History");
            course3.setCourseCode("HIST101");
            course3.setProfessor("Dr. Herodotus");
            course3.setDescription("A survey of major global events and civilizations.");
            course3.setCredits(3);

            courseRepository.saveAll(List.of(course1, course2, course3));

            // Create and save students
            Student student1 = new Student();
            student1.setFirstName("Alice");
            student1.setLastName("Johnson");
            student1.setEmail("alice.j@example.com");

            Student student2 = new Student();
            student2.setFirstName("Bob");
            student2.setLastName("Smith");
            student2.setEmail("bob.s@example.com");

            Student student3 = new Student();
            student3.setFirstName("Charlie");
            student3.setLastName("Brown");
            student3.setEmail("charlie.b@example.com");

            studentRepository.saveAll(List.of(student1, student2, student3));
        };
    }
}
