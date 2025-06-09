import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreaPedidoComponent } from '../crea-pedido/crea-pedido.component';
import { OrderService } from '../../../shared/service/Order.service';
import { rxResource } from '@angular/core/rxjs-interop';
import { UserService } from '../../../shared/service/User.service';
import { PaymentMethodService } from '../../../shared/service/PaymentMethod.service';
import { SaleService } from '../../../shared/service/Sale.service';
import { SaleLine } from '../../../shared/interface/SaleLine';
import { Order } from '../../../shared/interface/Order';
import { ProductosService } from '../../../shared/service/productos.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule, CreaPedidoComponent],
  templateUrl: './pedidos.component.html',
})
export class PedidosComponent {
  filtro = '';
  pedidoExpandidoId: number | null = null;
  modalCrearPedidoVisible = false;

  constructor(
    private pedidosService: OrderService,
    private userService: UserService,
    private paymentMedthod: PaymentMethodService,
    private ventaService: SaleService,
    private productsService: ProductosService
  ) { }

  pedidos = rxResource<Order[], any>({
    loader: () =>
      this.pedidosService.getallPedido().pipe(
        map(pedidos =>
          pedidos.map(pedido => ({
            ...pedido,
            // parseamos createdAt (string) a Date
            createdAt: new Date(pedido.created_at as unknown as string)
          }))
        )
      ),
  });

  users = rxResource({
    loader: () => this.userService.getallUser(),
  });

  paymentsMethods = rxResource({
    loader: () => this.paymentMedthod.getallMethod(),
  });

  sales = rxResource({
    loader: () => this.ventaService.getallSale(),
  });

  products = rxResource({
    loader: () => this.productsService.getallProduct(),
  });

  getNombreUsuario(userId: number): string {
    const user = this.users.value()?.find(u => u.id === userId);
    return user ? user.name : '';
  }

  getNombrePaymentMethod(paymentMethodId: number): string {
    const paymentMethod = this.paymentsMethods.value()?.find(p => p.id === paymentMethodId);
    return paymentMethod ? paymentMethod.brand : '';
  }

  getDireccionUsuario(userId: number): string {
    const user = this.users.value()?.find(u => u.id === userId);
    return user?.address?.street || 'Dirección no disponible';
  }

  getProductoDeLinea(linea: SaleLine): any {
    return this.products?.value()?.find(p => p.id === linea.product.id);
  }

  getLineasDePedido(pedido: Order): SaleLine[] {
    const venta = this.sales.value()?.find(s => s.id === pedido.sale);
    return venta?.line || [];
  }

  get pedidosFiltrados() {
    const filtroLower = this.filtro.toLowerCase();
    return this.pedidos.value()?.filter((p) =>
      this.getNombreUsuario(p.user).toLowerCase().includes(filtroLower) ||
      this.getNombrePaymentMethod(p.paymentMethod).toLowerCase().includes(filtroLower)
    );
  }

  esPedidoExpandido(pedidoId: number): boolean {
    return this.pedidoExpandidoId === pedidoId;
  }

  toggleDetallePedido(pedidoId: number): void {
    this.pedidoExpandidoId = this.pedidoExpandidoId === pedidoId ? null : pedidoId;
  }

  eliminarPedido(pedidoId: number) {
    console.log('Eliminar pedido', pedidoId);
  }

  crearPedido() {
    this.modalCrearPedidoVisible = true;
  }

  editarPedido(pedidoId: number) {
    const pedido = this.pedidos.value()?.find(p => p.id === pedidoId);
    if (pedido) {
      console.log('Abrir modal para editar pedido:', pedido);
    }
  }

  cerrarModalPedido() {
    this.modalCrearPedidoVisible = false;
  }

  onPedidoCreado(nuevoPedido: any) {
    this.pedidos.value()?.push({
      ...nuevoPedido,
      id: Date.now(),
    });
    this.modalCrearPedidoVisible = false;
  }

  ngOnInit(): void {
    setTimeout(() => {
      console.log('Pedidos recibidos:', this.pedidos.value());
      console.log('Usuarios recibidos:', this.users.value());
      console.log('Métodos de pago recibidos:', this.paymentsMethods.value());
      console.log('Ventas recibidas:', this.sales.value());
      console.log('Productos recibidos:', this.products.value());
    }, 1000); // esperar a que se carguen
  }
}
