import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-task',
  templateUrl: './post-task.component.html',
  styleUrl: './post-task.component.scss'
})
export class PostTaskComponent {

  taskForm!: FormGroup;
  listOfEmployees: any=[];
  listOfPriorities: any=["LOW","MEDIUM", "HIGH"];

  constructor(private adminService: AdminService,
    private fb: FormBuilder,
    private snackBar:MatSnackBar,
    private router: Router
  ){
    this.getUsers();
    this.taskForm = this.fb.group({
      employeeId:[null,[Validators.required]],
      title:[null,[Validators.required]],
      description:[null,[Validators.required]],
      dueDate:[null,[Validators.required]],
      priority:[null,[Validators.required]],

    })
  }

  getUsers(){
    this.adminService.getUsers().subscribe((res)=>{
      this.listOfEmployees = res;
      console.log(res);
    })
  }

  postTask(){
    this.adminService.postTask(this.taskForm.value).subscribe((res)=>{
      if(res.id !=null){
          this.snackBar.open("Задача опубликована","Close",{duration:3000});
          this.router.navigateByUrl("/admin/dashboard");
      } else {
        this.snackBar.open("Что-то пошло не так","Ошибка",{duration:3000});
      }
    })
  }
}
