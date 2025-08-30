import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import {StudentService} from '../../services/student';
import {Student} from '../../model/student';


@Component({
  selector: 'app-student-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './student-form.component.html',
  styleUrl: './student-form.component.scss'
})
export class StudentFormComponent implements OnInit {
  studentForm: FormGroup;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private studentService: StudentService,
    private dialogRef: MatDialogRef<StudentFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { student: Student | null }
  ) {
    this.studentForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });

    if (data && data.student) {
      this.isEditMode = true;
      this.studentForm.patchValue(data.student);
    }
  }

  ngOnInit(): void {}

  onSave(): void {
    if (this.studentForm.invalid) {
      return;
    }

    const formData = this.studentForm.value;
    if (this.isEditMode && this.data.student) {
      this.studentService.updateStudent(this.data.student.id, formData).subscribe({
        next: () => this.dialogRef.close(true)
      });
    } else {
      this.studentService.createStudent(formData).subscribe({
        next: () => this.dialogRef.close(true)
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
