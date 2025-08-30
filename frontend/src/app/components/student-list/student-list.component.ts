import { Component, OnInit } from '@angular/core';
import { AsyncPipe, CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { StudentFormComponent } from '../student-form/student-form.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import {Student} from '../../model/student';
import {StudentService} from '../../services/student'; // Import this

@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [
    CommonModule,
    AsyncPipe,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {
  students$: Observable<Student[]>;
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'actions'];

  constructor(
    private studentService: StudentService,
    public dialog: MatDialog
  ) {
    this.students$ = this.studentService.students$;
  }

  ngOnInit(): void {
    this.studentService.loadStudents().subscribe();
  }

  openStudentDialog(student: Student | null): void {
    const dialogRef = this.dialog.open(StudentFormComponent, {
      width: '400px',
      data: { student: student }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.studentService.loadStudents().subscribe();
      }
    });
  }

  // Add this new method
  openDeleteConfirmDialog(studentId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Are you sure you want to delete this student?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.studentService.deleteStudent(studentId).subscribe(() => {
          this.studentService.loadStudents().subscribe();
        });
      }
    });
  }
}
