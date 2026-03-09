import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './api.service';

@Injectable({ providedIn: 'root' })
export class UsersApiService {
  private readonly baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  createUser(email: string, password: string): Observable<User> {
    return this.http.post<User>(this.baseUrl, { email, password });
  }
}
