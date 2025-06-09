import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-user-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent {
  isEditing = false;

  user = {
    username: 'juanperez',
    name: 'Juan',
    lastname: 'Pérez',
    email: 'juan@example.com',
    phone: 123456789,
    photo: 'https://randomuser.me/api/portraits/men/75.jpg',
    pass: '', // Vacío porque no se muestra la contraseña actual
    rol: 'admin',
    address: {
      street: 'Calle Falsa',
      number: '123',
      flat: '2ºA',
      door: '', // puedes poner 'B' si lo deseas
      city: 'Madrid',
      state: 'Madrid',
      country: 'España',
      postalCode: '28080'
    }
  };



  editedUser = { ...this.user };

  toggleEdit() {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.editedUser = { ...this.user };
    }
    this.modoEdicion = !this.modoEdicion;
  }

  saveChanges() {
    if (this.showPasswordChange) {
      if (!this.passwordForm.current || !this.passwordForm.new || !this.passwordForm.confirm) {
        alert('Por favor completa todos los campos de contraseña.');
        return;
      }

      if (this.passwordForm.new !== this.passwordForm.confirm) {
        alert('Las contraseñas nuevas no coinciden.');
        return;
      }

      // Aquí enviarías la nueva contraseña y la actual para verificar y actualizar.
      console.log('Cambiando contraseña:', this.passwordForm);
    }

    // Guardar el resto del perfil
    console.log('Guardando perfil:', this.user);

    this.isEditing = false;
    this.showPasswordChange = false;
  }


  tab: 'perfil' | 'direccion' | 'metodos' | 'pedidos' = 'perfil';
  modoEdicion = false;

  showPasswordChange = false;

  passwordForm = {
    current: '',
    new: '',
    confirm: ''
  };


}
