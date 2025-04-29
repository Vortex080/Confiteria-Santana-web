import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RegisterService } from '../../service/register.service';
import { Router } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'; // Necesario para RegisterService
import { provideHttpClient } from '@angular/common/http'; // Para standalone
import { User } from '../../interface/user';

@Component({
  selector: 'app-register',
  standalone: true, // <-- LE FALTABA standalone: true
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  providers: [RegisterService], // <-- AÑADIMOS RegisterService en providers
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'] // <-- styleUrls (plural), no "styleUrl"
})
export class RegisterComponent {
  registerForm: FormGroup;
  submitted = false;
  errorMessage: string | null = null;
  currentSection = 1; // Comienza en la sección 1

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      name: ['', [Validators.required, Validators.minLength(3)]],
      lastname: ['', [Validators.required, Validators.minLength(3)]],
      address: ['', [Validators.required]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]+$')]], // Validación para solo números
      photo: [''],
      email: ['', [Validators.required, Validators.email]],
      pass: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],

      // Campos adicionales para la dirección
      street: ['', [Validators.required]],
      number: ['', [Validators.required]],
      floor: [''],
      door: [''],
      city: ['', [Validators.required]],
      province: ['', [Validators.required]],
      country: ['', [Validators.required]],
      postalCode: ['', [Validators.required, Validators.pattern('^[0-9]{5}$')]], // Valida 5 dígitos para el código postal
    }, { validators: this.checkPasswords });
  }

  nextSection() {
    this.currentSection++;
  }

  previousSection() {
    this.currentSection--;
  }


  checkPasswords(group: FormGroup) {
    const password = group.get('pass')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { notSame: true };
  }

  enviarUsuario() {
    this.submitted = true;
    if (this.registerForm.valid) {
      const usuarioData: User = {
        username: this.registerForm.value.username,
        name: this.registerForm.value.name,
        lastname: this.registerForm.value.lastname,
        address: {
          street: this.registerForm.value.street,
          number: this.registerForm.value.number,
          flat: this.registerForm.value.floor,
          door: this.registerForm.value.door,
          city: this.registerForm.value.city,
          state: this.registerForm.value.province,
          country: this.registerForm.value.country,
          postalCode: this.registerForm.value.postalCode,
        },
        rol: '',
        phone: this.registerForm.value.phone,
        photo: this.registerForm.value.photo, // Se puede dejar vacío o añadir una URL predeterminada
        email: this.registerForm.value.email,
        pass: this.registerForm.value.pass
      };

      console.log('Datos del usuario:', usuarioData);

      this.registerService.registrarUsuario(usuarioData).subscribe(
        response => {
          console.log('Usuario registrado exitosamente:', response);
          this.router.navigate(['/']);
        },
        error => {
          if (error.status === 409) {
            console.error('Error 409: El usuario o email ya existe.');
            this.errorMessage = 'El usuario o email ya existe.';
          } else {
            console.error('Error inesperado', error);
            this.errorMessage = 'Ha ocurrido un error. Inténtalo de nuevo más tarde.';
          }
        }
      );
    }
  }

  get username() { return this.registerForm.get('username'); }
  get name() { return this.registerForm.get('name'); }
  get lastname() { return this.registerForm.get('lastname'); }
  get address() { return this.registerForm.get('address'); }
  get phone() { return this.registerForm.get('phone'); }
  get photo() { return this.registerForm.get('photo'); }
  get email() { return this.registerForm.get('email'); }
  get pass() { return this.registerForm.get('pass'); }
  get confirmPassword() { return this.registerForm.get('confirmPassword'); }

  // Obtener campos de dirección
  get street() { return this.registerForm.get('street'); }
  get number() { return this.registerForm.get('number'); }
  get floor() { return this.registerForm.get('floor'); }
  get door() { return this.registerForm.get('door'); }
  get city() { return this.registerForm.get('city'); }
  get province() { return this.registerForm.get('province'); }
  get country() { return this.registerForm.get('country'); }
  get postalCode() { return this.registerForm.get('postalCode'); }
}
