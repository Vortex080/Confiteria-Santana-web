import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FichaClienteComponent } from '../ficha-cliente/ficha-cliente.component';
import { UserService } from '../../../shared/service/User.service';
import { rxResource } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, FichaClienteComponent],
  templateUrl: './clientes.component.html'
})
export class ClientesComponent {

  constructor(private userservice: UserService) { }

  defaultAvatarUrl = 'data:image/svg+xml,%3Csvg xmlns=%22http://www.w3.org/2000/svg%22 width=%2224%22 height=%2224%22 viewBox=%220 0 24 24%22%3E%3Cg fill=%22none%22 stroke=%22currentColor%22 stroke-dasharray=%2228%22 stroke-dashoffset=%2228%22 stroke-linecap=%22round%22 stroke-linejoin=%22round%22 stroke-width=%222%22%3E%3Cpath d=%22M4 21v-1c0 -3.31 2.69 -6 6 -6h4c3.31 0 6 2.69 6 6v1%22%3E%3Canimate fill=%22freeze%22 attributeName=%22stroke-dashoffset%22 dur=%220.4s%22 values=%2228;0%22/%3E%3C/path%3E%3Cpath d=%22M12 11c-2.21 0 -4 -1.79 -4 -4c0 -2.21 1.79 -4 4 -4c2.21 0 4 1.79 4 4c0 2.21 -1.79 4 -4 4Z%22%3E%3Canimate fill=%22freeze%22 attributeName=%22stroke-dashoffset%22 begin=%220.4s%22 dur=%220.4s%22 values=%2228;0%22/%3E%3C/path%3E%3C/g%3E%3C/svg%3E';

  filtro: string = '';
  visible: boolean = false;
  editarModal: boolean = false;
  infoModal: boolean = false;

  clientes = rxResource({
    loader: () => this.userservice.getallUser(),
  })


  get clientesFiltrados() {
    const filtroLower = this.filtro.toLowerCase();
    return this.clientes.value()?.filter(c =>
      c.name.toLowerCase().includes(filtroLower) ||
      c.lastname.toLowerCase().includes(filtroLower) ||
      c.username.toLowerCase().includes(filtroLower)
    );
  }


  cerrar() {
    this.visible = false;
  }

  editarCliente() {
    this.editarModal = true;
  }

  infoCliente() {
    this.infoModal = true;
  }


  clienteSeleccionado: any = null;
  modoEdicion: boolean = false;

  abrirFicha(cliente: any, edicion: boolean = false) {
    this.clienteSeleccionado = { ...cliente }; // Clonamos por seguridad
    this.modoEdicion = edicion;
    this.visible = true;
  }

  guardarCambios() {
    this.clientes.reload();
  }


  abrirNuevoCliente() {
    this.clienteSeleccionado = {
      id: '',
      username: '',
      name: '',
      lastname: '',
      email: '',
      phone: null,
      rol: 'USER',
      photo: this.defaultAvatarUrl
    };
    this.modoEdicion = true;
    this.visible = true;



  }


}
