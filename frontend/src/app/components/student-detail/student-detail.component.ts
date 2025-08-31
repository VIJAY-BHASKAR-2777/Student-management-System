import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';



import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import {Student} from '../../model/student';
import {Course} from '../../model/course';
import {StudentService} from '../../services/student';
import {CourseService} from '../../services/course';

@Component({
  selector: 'app-student-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './student-detail.component.html',
  styleUrl: './student-detail.component.scss'
})
export class StudentDetailComponent implements OnInit {
  student: Student | null = null;
  enrolledCourses: Course[] = [];
  availableCourses: Course[] = [];

  isLoadingStudent = true; // For the main student data
  isLoadingCourses = true; // For the available courses list

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    const studentId = Number(this.route.snapshot.paramMap.get('id'));
    if (!studentId) {
      this.isLoadingStudent = false;
      this.isLoadingCourses = false;
      return;
    }

    // Step 1: Load essential student data first
    this.studentService.getStudentById(studentId).subscribe(student => {
      this.student = student;
      this.enrolledCourses = student.courses;
      this.isLoadingStudent = false; // Stop main spinner

      // Step 2: Now, load all other courses in the background
      this.loadAvailableCourses();
    });
  }

  loadAvailableCourses(): void {
    this.courseService.getAllCourses().subscribe(allCourses => {
      const enrolledCourseIds = new Set(this.enrolledCourses.map(c => c.id));
      this.availableCourses = allCourses.filter(c => !enrolledCourseIds.has(c.id));
      this.isLoadingCourses = false; // Stop the second spinner
    });
  }

  enroll(course: Course): void {
    if (!this.student) return;

    this.studentService.enrollStudentInCourse(this.student.id, course.id).subscribe(() => {
      // Update UI instantly for a better user experience
      this.enrolledCourses.push(course);
      this.availableCourses = this.availableCourses.filter(c => c.id !== course.id);
    });
  }

    unenroll(course: Course): void {
      if (!this.student) return;

    this.studentService.unenrollStudentFromCourse(this.student.id, course.id).subscribe(() => {
      // Update UI instantly
      this.availableCourses.push(course);
      this.enrolledCourses = this.enrolledCourses.filter(c => c.id !== course.id);
    });
  }

}
