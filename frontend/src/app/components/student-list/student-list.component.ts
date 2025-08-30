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


/**
 * =================================================================
 * Student List Component
 * =================================================================
 * Purpose:
 * This component is responsible for displaying a list of all students
 * in a table and providing the UI for adding, editing, and deleting students.
 * =================================================================
 */
@Component({
  selector: 'app-student-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule
  ],
  templateUrl: './student-list.component.html',
  styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {
  // An observable to hold the list of students, subscribed to in the template using the async pipe.
  students$: Observable<Student[]>;
  // Defines the columns to be displayed in the Angular Material table.
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email', 'actions'];

  constructor(
    private studentService: StudentService,
    public dialog: MatDialog // Injects the MatDialog service to open modal dialogs.
  ) {
    // Subscribes the local students$ observable to the one in the service.
    this.students$ = this.studentService.students$;
  }

  ngOnInit(): void {
    this.studentService.loadStudents().subscribe();
  }

  /**
   * Opens the StudentFormComponent in a dialog for either creating a new student
   * (if student is null) or editing an existing one.
   * @param student The student object to edit, or null to create a new one.
   */
  openStudentDialog(student: Student | null): void {
    const dialogRef = this.dialog.open(StudentFormComponent, {
      width: '400px',
      data: { student: student }
    });

    // After the dialog is closed, check if it returned a 'true' value (meaning success).
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // If the form was saved successfully, reload the student list to show changes.
        this.studentService.loadStudents().subscribe();
      }
    });
  }

  /**
   * Opens a confirmation dialog before deleting a student.
   * @param studentId The ID of the student to be deleted.
   */
  openDeleteConfirmDialog(studentId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Are you sure you want to delete this student?' }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // If the user confirmed, call the delete method and reload the list.
        this.studentService.deleteStudent(studentId).subscribe(() => {
          this.studentService.loadStudents().subscribe();
        });
      }
    });
  }
}
