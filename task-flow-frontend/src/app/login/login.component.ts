import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: '',
    password: ''
  };
  errorMessage = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    
  }

  onSubmit(): void {
    const { username, password } = this.form;
    
    this.authService.login(this.form).subscribe({
      next: data => {
       window.sessionStorage.setItem('auth-token', data.token); 
        
        window.sessionStorage.setItem('auth-user', JSON.stringify(data));
        
       
        this.router.navigate(['/dashboard']);
      },
      error: err => {
  console.error('Ошибка:', err);
  
  if (err.status === 401) {
    this.errorMessage = 'Неверный логин или пароль';
  } 
 
  else if (err.error && err.error.message) {
    this.errorMessage = err.error.message;
  }

  else {
    this.errorMessage = 'Произошла ошибка: ' + err.status;
  }
      }
    });
  }
}