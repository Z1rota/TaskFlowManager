import { Component, OnInit } from '@angular/core';
import { CommonModule, AsyncPipe } from '@angular/common'; 
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Task, TaskService } from '../services/task.service';
import { HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule, AsyncPipe],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
 
  private tasksSubject = new BehaviorSubject<Task[]>([]);
  
 
  tasks$: Observable<Task[]> = this.tasksSubject.asObservable();

  newTask: Task = { title: '', description: '', status: 'TODO' };
  errorMessage = '';

  constructor(private taskService: TaskService, private router: Router) { }

  ngOnInit(): void {
    this.loadTasks();
  }

  loadTasks(): void {
    this.taskService.getAll().subscribe({
      next: (data) => {
       
        this.tasksSubject.next(data);
        console.log('Задачи загружены:', data);
      },
      error: (err) => {
        console.error('Ошибка загрузки:', err);
        if (err.status === 401) {
          this.router.navigate(['/login']); 
        }
      }
    });
  }

  createTask(): void {
    if (!this.newTask.title) return;

    this.taskService.create(this.newTask).subscribe({
      next: (createdTask) => {
        console.log('Создана задача:', createdTask);
       
        const currentTasks = this.tasksSubject.value;
        
   
        const updatedTasks = [...currentTasks, createdTask];
        
    
        this.tasksSubject.next(updatedTasks);
        
        this.newTask = { title: '', description: '', status: 'TODO' };
      },
      error: (err: HttpErrorResponse) => {
        console.error('Ошибка создания:', err);
        alert('Не удалось создать задачу: ' + (err.error?.message || err.statusText));
      }
    });
  }

  deleteTask(id: number): void {
    this.taskService.delete(id).subscribe({
      next: () => {
    
        const currentTasks = this.tasksSubject.value;
        const updatedTasks = currentTasks.filter(t => t.id !== id);
        
        this.tasksSubject.next(updatedTasks);
      },
      error: (err) => console.error('Ошибка удаления:', err)
    });
  }

  logout(): void {
    window.sessionStorage.clear();
    this.router.navigate(['/login']);
  }
}