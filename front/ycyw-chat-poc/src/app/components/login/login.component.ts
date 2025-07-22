import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: false,
})
export class LoginComponent {

  username = '';
  password = '';
  errorMsg = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    console.log('login est appelée');
    this.authService.login(this.username, this.password).subscribe({
      next: () => {
        console.log('Login réussi');
        this.router.navigate(['/chat']);
      },
      error: () => {
        this.errorMsg = 'Erreur d’authentification';
      }
    });
  }
}
