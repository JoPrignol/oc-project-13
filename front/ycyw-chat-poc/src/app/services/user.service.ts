import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {}
  private apiUrl = 'http://localhost:8080/api/user/me';

  getUsername(): Observable<string> {
      return this.http.get<string>(this.apiUrl, { withCredentials: true });
    }
}
