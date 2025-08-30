import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import {Student} from '../model/student';

/**
 * =================================================================
 * Student Service
 * =================================================================
 * Purpose:
 * This service manages all student-related data, including API calls
 * for CRUD operations and state management for the student list.
 *
 * State Management:
 * It uses an RxJS BehaviorSubject (studentsSubject) to hold the current
 * list of students. This allows components to subscribe to a single source
 * of truth (students$) and reactively update whenever the list changes.
 * =================================================================
 */
@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8080/api/students';

  // A private BehaviorSubject to hold and manage the list of students.
  private studentsSubject = new BehaviorSubject<Student[]>([]);
  // A public observable that components can subscribe to for reactive updates.
  public students$ = this.studentsSubject.asObservable();

  constructor(private http: HttpClient) { }

  /**
   * Fetches all students from the API and updates the BehaviorSubject.
   * The 'tap' operator allows us to perform a side effect (updating the subject)
   * without modifying the observable stream itself.
   * @returns An Observable array of Student objects.
   */
  loadStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl).pipe(
      tap(students => this.studentsSubject.next(students))
    );
  }

  /** Fetches a single student by their ID. */
  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/${id}`);
  }

  /** Creates a new student. */
  createStudent(student: Omit<Student, 'id' | 'courses'>): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, student);
  }

  /** Updates an existing student. */
  updateStudent(id: number, studentDetails: Partial<Student>): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/${id}`, studentDetails);
  }

  /** Deletes a student by their ID. */
  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /** Enrolls a student in a course. */
  enrollStudentInCourse(studentId: number, courseId: number): Observable<Student> {
    return this.http.post<Student>(`${this.apiUrl}/${studentId}/enroll/${courseId}`, null);
  }

  /** Unenrolls a student from a course. */
  unenrollStudentFromCourse(studentId: number, courseId: number): Observable<Student> {
    return this.http.delete<Student>(`${this.apiUrl}/${studentId}/unenroll/${courseId}`);
  }
}
