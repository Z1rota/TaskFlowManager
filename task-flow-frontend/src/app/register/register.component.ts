import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router'; 
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  styleUrl: "./register.component.css",
  templateUrl: './register.component.html'
})

export class RegisterComponent {
  form: any = {  
    username: '',
    email: '',
    password: ''
  };
  errorMessage = '';

   constructor(private authService: AuthService, private router: Router) { }

  

 onSubmit(): void {
    this.authService.register(this.form).subscribe({
      next: data => {
        console.log(data);
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error('Полный объект ошибки:', err); 
        if (err.error && err.error.message) {
          this.errorMessage = err.error.message;
        } 

        else if (typeof err.error === 'string') {
          this.errorMessage = err.error;
        } 
        else {
          this.errorMessage = `Ошибка регистрации (${err.status})`;
        }
      }
    });
  }
}