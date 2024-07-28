import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { StorageService } from '../../services/storage/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loginForm!: FormGroup;
  hidePassword = true;

  constructor(private fb: FormBuilder,
    private authService:AuthService,
    private snackbar:MatSnackBar,
    private router:Router
  ){
    this.loginForm = this.fb.group({
      email:[null,[Validators.required, Validators.email]],
      password:[null,[Validators.required]],
    })
  }


  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword
  }

  onSubmit() {
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm.value).subscribe(
      (res) => {
        console.log(res);
        if (res.userId != null) {
          const user = {
            id: res.userId,
            role: res.userRole,
          };
          StorageService.saveUser(user);
          StorageService.saveToken(res.jwt);
          if (StorageService.isAdminLoggedIn()) {
            this.router.navigateByUrl("/admin/dashboard");
          } else if (StorageService.isEmployeeLoggedIn()) {
            this.router.navigateByUrl("/employee/dashboard");
          }
          this.snackbar.open("Успешный вход", "Закрыть", { duration: 3000 });
        } else {
          this.snackbar.open("Некорректный вход. Попробуй еще раз", "Закрыть", {
            duration: 3000,
            panelClass: "error-snackbar",
          });
        }
      },
      (error) => {
        if (error.status === 403) {
          this.snackbar.open("Неправильный логин или пароль", "Закрыть", {
            duration: 3000,
            panelClass: "error-snackbar",
          });
        } else {
          this.snackbar.open("Произошла ошибка. Попробуйте снова позже", "Закрыть", {
            duration: 3000,
            panelClass: "error-snackbar",
          });
        }
      }
    );
  }

  }

