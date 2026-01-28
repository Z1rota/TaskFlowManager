import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { LoginSuccessComponent } from './login-success/login-success.component';
import { authGuard } from './utils/guard';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' }, 
  { path: 'login-success', component: LoginSuccessComponent },
   { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [authGuard]
  },
];