import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private http: HttpClient, private router: Router) {}

  canActivate(): Observable<boolean> {
    console.log('AuthGuard check...');
    return this.http.get('/api/login', { withCredentials: true }).pipe(
      map(() => {
        console.log('AuthGuard autorisé');
        return true;
      }),
      catchError(() => {
        console.log('AuthGuard refusé, redirection login');
        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }
}
