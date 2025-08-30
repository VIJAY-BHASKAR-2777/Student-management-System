import { Component, OnInit } from '@angular/core';
import { AsyncPipe, CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {Student} from '../../model/student';

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [
    CommonModule,
    AsyncPipe,
    MatTableModule,
    MatButtonModule,
    MatIconModule
  ],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {

  students$: Observable<Student[]> = of([
    { id: 1, firstName: 'John', lastName: 'Doe', email: 'john@example.com', courses: [] },
    { id: 2, firstName: 'Jane', lastName: 'Smith', email: 'jane@gmail.com',courses: []},
    { id: 3, firstName: 'Alice', lastName: 'Johnson', email: 'Alice@gmail.com',courses: []}
  ]);
  students: Student[] = [];
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'actions'];

  ngOnInit(): void {
    this.students$.subscribe(data => this.students = data);
  }
}
