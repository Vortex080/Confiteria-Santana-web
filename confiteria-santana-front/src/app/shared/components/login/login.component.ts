import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { LoginService } from '../../service/login.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { LocalStorageService } from '../../service/LocalStorage.service';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private loginService: LoginService, private router: Router, private localStorageService: LocalStorageService) { }

  onSubmit(form: NgForm): void {
    if (form.invalid) {
      this.errorMessage = 'Por favor, rellena todos los campos.';
      return;
    }

    this.loginService.login(this.email, this.password).subscribe({
      next: response => {
        const body = response.body;
        console.log('Login exitoso:', body);
        this.localStorageService.setToken(body.token);
        this.localStorageService.setUser(body.user);
        this.router.navigate(['/']);

      },
      error: err => {
        console.error('Error en el login:', err);
        this.errorMessage = 'Correo o contraseña incorrectos.';
      }
    });
  }
}
