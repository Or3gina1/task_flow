import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  listOfTasks: any = [];

  constructor(private service: EmployeeService,
    private snackbar: MatSnackBar
   ) { 
    this.getTasks();
  }

  getTasks(){
    this.service.getEmployeeTasksById().subscribe((res)=>{
      console.log(res);
      this.listOfTasks = res;
    })
  }

  updateStatus(id: number, status: string){
    this.service.updateStatus(id,status).subscribe((res)=>{
      if (res.id!=null){
        this.snackbar.open("Статус задачи успешно обновлен", "Close",{duration:3000})
        this.getTasks();
      }else{
        this.snackbar.open("Произошла ошибка при обновлении задачи", "Close",{duration:3000})
      }
    })
  }

}
