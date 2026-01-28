import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/tasks'; 

export interface Task {
  id?: number;
  title: String;
  description: String;
  status: 'TODO' | 'IN_PROGRESS' | 'DONE';
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  constructor(private http: HttpClient) { }

  getAll(): Observable<Task[]> {
    return this.http.get<Task[]>(API_URL);
  }

  create(task: Task): Observable<Task> {
    return this.http.post<Task>(API_URL, task);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${API_URL}/${id}`);
  }
}