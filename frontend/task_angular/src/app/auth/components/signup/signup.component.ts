import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { error } from 'console';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  signupForm!: FormGroup;
  hidePassword = true;

  constructor(private fb: FormBuilder,
    private authService:AuthService,
    private snackbar: MatSnackBar,
    private router:Router
  ){
    this.signupForm = this.fb.group({
      name:[null,[Validators.required]],
      email:[null,[Validators.required, Validators.email]],
      password:[null,[Validators.required]],
      confirmPassword:[null,[Validators.required]],
    })
  }


  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword
  }

  onSubmit(){
    console.log(this.signupForm.value);
   const password = this.signupForm.get("password")?.value;
   const confirmPassword = this.signupForm.get("confirmPassword")?.value;
    if(password !==confirmPassword){
      this.snackbar.open("Пароли не совпадает", "Закрыть",{duration:3000,panelClass:"error-snackbar"})
      return;
    }

    this.authService.signup(this.signupForm.value).subscribe((res)=>{
      console.log(res);
      if (res.id != null){
        this.snackbar.open("Регистрация успешна", "Закрыть", {duration:3000});
        this.router.navigateByUrl("/login");
      }else{
        this.snackbar.open("Регистрация не прошла. Попробуй еще раз", "Закрыть",{duration:3000,panelClass:"error-snackbar"})
      }
    })

  }

}
