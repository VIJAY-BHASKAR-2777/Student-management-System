
import { Routes } from '@angular/router';
import { StudentListComponent } from './components/student-list/student-list.component';
import {CourseListComponent} from './components/course-list/course-list.component';

export const routes: Routes = [
  { path: 'students/:id', component: StudentDetailComponent },
  { path: 'students', component: StudentListComponent },
  { path: 'courses', component: CourseListComponent }, // We will add this later
  { path: '', redirectTo: '/students', pathMatch: 'full' }, // Default route
  { path: '**', redirectTo: '/students' } // Wildcard route for any other URL
];
