import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import {Student} from '../model/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiUrl = 'http://localhost:8080/api/students';

  private studentsSubject = new BehaviorSubject<Student[]>([]);
  public students$ = this.studentsSubject.asObservable();

  constructor(private http: HttpClient) { }

  loadStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.apiUrl).pipe(
      tap(students => this.studentsSubject.next(students))
    );
  }

  getStudentById(id: number): Observable<Student> {
    return this.http.get<Student>(`${this.apiUrl}/${id}`);
  }

  createStudent(student: Omit<Student, 'id' | 'courses'>): Observable<Student> {
    return this.http.post<Student>(this.apiUrl, student);
  }

  updateStudent(id: number, studentDetails: Partial<Student>): Observable<Student> {
    return this.http.put<Student>(`${this.apiUrl}/${id}`, studentDetails);
  }

  deleteStudent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  enrollStudentInCourse(studentId: number, courseId: number): Observable<Student> {
    return this.http.post<Student>(`${this.apiUrl}/${studentId}/enroll/${courseId}`, null);
  }

  unenrollStudentFromCourse(studentId: number, courseId: number): Observable<Student> {
    return this.http.delete<Student>(`${this.apiUrl}/${studentId}/unenroll/${courseId}`);
  }
}
