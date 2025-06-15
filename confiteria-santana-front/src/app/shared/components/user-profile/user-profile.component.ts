import { Component, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';
import { User } from '../../interface/user';
import { LocalStorageService } from '../../service/LocalStorage.service';
import { UserService } from '../../service/User.service';
import { OrderService } from '../../service/Order.service';
import { OrderResponse } from '../../interface/Order';
import { Sale } from '../../interface/Sale';
import { SaleService } from '../../service/Sale.service';

@Component({
  standalone: true,
  selector: 'app-user-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  isEditing = false;
  modoEdicion = false;
  showPasswordChange = false;

  tab: 'perfil' | 'direccion' | 'metodos' | 'pedidos' = 'perfil';

  user!: User;
  editedUser!: User;

  passwordForm = {
    current: '',
    new: '',
    confirm: ''
  };

  // Pedidos
  allOrders = rxResource<OrderResponse[], any>({
    loader: () => this.orderService.getallPedido()
  });

  sales = rxResource<Sale[], any>({
    loader: () => this.saleService.getallSale(),
  });

  userOrders = computed(() =>
    this.allOrders.value()?.filter(order => order.userId === this.user?.id) ?? []
  );

  constructor(
    private localStorageService: LocalStorageService,
    private userService: UserService,
    private orderService: OrderService,
    private saleService: SaleService
  ) { }

  ngOnInit(): void {
    const localUser = this.localStorageService.getUser();

    if (localUser?.email) {
      this.userService.getallUser().subscribe(users => {
        const found = users.find(u => u.email === localUser.email);
        if (found) {
          this.user = structuredClone(found);
          this.editedUser = structuredClone(found);
          this.allOrders.reload(); // Carga inicial de pedidos
        } else {
          console.error('Usuario no encontrado con email:', localUser.email);
        }
      });
    }
  }

  getSaleForPedido(pedidoId: number): Sale | undefined {
    return this.sales.value()?.find(sale => sale.id === pedidoId);
  }


  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.editedUser = structuredClone(this.user);
    }
    this.modoEdicion = !this.modoEdicion;
  }

  saveChanges(): void {
    if (this.showPasswordChange) {
      if (!this.passwordForm.current || !this.passwordForm.new || !this.passwordForm.confirm) {
        alert('Por favor completa todos los campos de contraseña.');
        return;
      }

      if (this.passwordForm.new !== this.passwordForm.confirm) {
        alert('Las contraseñas nuevas no coinciden.');
        return;
      }

      // Lógica futura para cambio de contraseña.
      console.log('Cambiando contraseña:', this.passwordForm);
    }

    this.userService.updateUser(this.user).subscribe({
      next: res => {
        alert('Perfil actualizado con éxito');
        this.localStorageService.setUser({
          name: this.user.name,
          email: this.user.email
        });
        this.isEditing = false;
        this.showPasswordChange = false;
      },
      error: err => {
        alert('Error al guardar los cambios');
        console.error(err);
      }
    });
  }


  filtroFecha: string = ''; // formato 'YYYY-MM'

  pedidosFiltrados = computed(() => {
    if (!this.userOrders()) return [];

    if (!this.filtroFecha) return this.userOrders();

    const [año, mes] = this.filtroFecha.split('-').map(Number);

    return this.userOrders().filter(pedido => {
      const fecha = new Date(pedido.created_at);
      return fecha.getFullYear() === año && (fecha.getMonth() + 1) === mes;
    });
  });

}
