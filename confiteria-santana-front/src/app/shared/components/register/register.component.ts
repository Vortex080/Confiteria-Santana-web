import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../interface/user';
import { RegisterService } from '../../service/register.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  registerForm: FormGroup;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private registerService: RegisterService, private router: Router) {
    this.registerForm = this.fb.group({
      firstPart: this.fb.group({
        nombre: ['', [Validators.required, Validators.minLength(2)]],
        usuario: ['', [Validators.required, Validators.minLength(3)]],
        apellidos: ['', [Validators.required]],
        correo: ['', [Validators.required, Validators.email]],
        contrasena: ['', [Validators.required, Validators.minLength(6)]],
        confirmarContrasena: ['', Validators.required],
      }, { validators: this.matchPasswords }),

      secondPart: this.fb.group({
        calle: ['', [Validators.required]],
        numero: ['', [Validators.required]],
        piso: [''],
        puerta: [''],
        ciudad: ['', [Validators.required]],
        provincia: ['', [Validators.required]],
        pais: ['', [Validators.required]],
        codigoPostal: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      }),
    });
  }

  matchPasswords(group: FormGroup) {
    const pass = group.get('contrasena')?.value;
    const confirm = group.get('confirmarContrasena')?.value;
    return pass === confirm ? null : { notMatching: true };
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const formValue = this.registerForm.value;
      const usuario: User = {
        id: 0,
        username: formValue.firstPart.usuario,
        name: formValue.firstPart.nombre,
        lastname: formValue.firstPart.apellidos,
        photo: '', // Puedes asignar una foto predeterminada aquí si es necesario
        email: formValue.firstPart.correo,
        address: {
          id: '',
          street: formValue.secondPart.calle,
          number: formValue.secondPart.numero,
          flat: formValue.secondPart.piso,
          door: formValue.secondPart.puerta,
          city: formValue.secondPart.ciudad,
          state: formValue.secondPart.provincia,
          country: formValue.secondPart.pais,
          postalCode: formValue.secondPart.codigoPostal,
        },
        rol: 'user', // Asigna el rol según sea necesario
        phone: 0, // Puedes agregar un campo de teléfono en el formulario si es necesario
        pass: formValue.firstPart.contrasena,
      };
      console.log('Usuario a registrar:', usuario);
      this.registerService.registrarUsuario(usuario).subscribe({
        next: (response: { body: any; }) => {
          this.errorMessage = null;
          this.router.navigate(['/home']);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error al registrar usuario:', error);
          this.handleError(error);
        }
      });
    } else {
      this.registerForm.markAllAsTouched();
    }
  }

  handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.errorMessage = 'No se pudo conectar con el servidor. Por favor, inténtalo más tarde.';
    } else if (error.status >= 400 && error.status < 500) {
      this.errorMessage = 'Hubo un error con tu solicitud. Por favor, revisa los datos e inténtalo de nuevo.';
    } else if (error.status >= 500) {
      this.errorMessage = 'Hubo un problema en el servidor. Por favor, inténtalo más tarde.';
    } else {
      this.errorMessage = 'Ocurrió un error inesperado. Por favor, inténtalo más tarde.';
    }
  }

  get fp() {
    return (this.registerForm.get('firstPart') as FormGroup).controls;
  }

  get sp() {
    return (this.registerForm.get('secondPart') as FormGroup).controls;
  }
}
