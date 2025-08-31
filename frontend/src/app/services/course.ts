import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Course} from '../model/course';
/**
 * =================================================================
 * Course Service
 * =================================================================
 * Purpose:
 * This service is responsible for all HTTP interactions with the backend
 * for course-related data. It provides methods to fetch and create courses.
 * =================================================================
 */
@Injectable({
  providedIn: 'root'
})
export class CourseService {
  // The base URL of the backend courses API.
  private apiUrl = 'http://localhost:8080/api/courses';

  constructor(private http: HttpClient) { }

  /**
   * Fetches all courses from the backend.
   * @returns An Observable array of Course objects.
   */
  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(this.apiUrl);
  }

  /**
   * Sends a request to the backend to create a new course.
   * @param course An object containing the name of the new course.
   * @returns An Observable of the newly created Course object.
   */
  createCourse(course: Partial<Course>): Observable<Course> {
    return this.http.post<Course>(this.apiUrl, course);
  }
}
