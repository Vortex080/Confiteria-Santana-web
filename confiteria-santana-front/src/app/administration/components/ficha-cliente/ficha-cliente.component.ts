import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../shared/service/User.service';
import { User } from '../../../shared/interface/user';

@Component({
  selector: 'app-ficha-cliente',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ficha-cliente.component.html'
})
export class FichaClienteComponent {

  constructor(private userService: UserService) { }


  @Input() cliente: any;
  @Input() modoEdicion: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<any>();

  errores: {
    username?: string;
    email?: string;
    name?: string;
    lastname?: string;
  } = {};

  mostrarDireccion: boolean = false;

  ngOnInit() {
    // Asegúrate de que existe la propiedad address si viene vacío
    if (!this.cliente.address) {
      this.cliente.address = {
        street: '',
        number: '',
        flat: '',
        door: '',
        city: '',
        state: '',
        country: '',
        postalCode: ''
      };
    }
  }


  onSave() {
    this.errores = {};

    if (!this.cliente.username?.trim()) this.errores.username = 'El usuario es obligatorio.';
    if (!this.cliente.email?.trim()) this.errores.email = 'El email es obligatorio.';
    if (!this.cliente.name?.trim()) this.errores.name = 'El nombre es obligatorio.';
    if (!this.cliente.lastname?.trim()) this.errores.lastname = 'Los apellidos son obligatorios.';

    if (Object.keys(this.errores).length > 0) return;

    const user: User = {
      id: this.cliente.id ?? 0,
      username: this.cliente.username,
      name: this.cliente.name,
      lastname: this.cliente.lastname,
      photo: this.cliente.photo ?? '', // si no hay, cadena vacía
      email: this.cliente.email,
      address: this.cliente.address,
      rol: this.cliente.rol ?? 'cliente',
      phone: this.cliente.phone ?? 0,
      pass: this.cliente.pass ?? ''
    };


    const peticion = user.id === 0
      ? this.userService.crearUser(user)
      : this.userService.updateUser(user);

    peticion.subscribe({
      next: (response) => {
        this.save.emit(user); // o simplemente `user` si prefieres
        this.close.emit(); // Emitimos el evento de cierre
      },
      error: (err) => {
        console.error('Error al guardar usuario:', err);
      }
    });
    
  }



}
