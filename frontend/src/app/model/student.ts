import {Course} from './course';

export interface Student {
  id: number;
  firstname:string;
  lastname:string;
  email:string;
  courses: Course[];
}
