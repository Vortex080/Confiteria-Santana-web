import { Component, signal, effect, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CreaPedidoComponent } from '../crea-pedido/crea-pedido.component';
import { OrderService } from '../../../shared/service/Order.service';
import { rxResource } from '@angular/core/rxjs-interop';
import { UserService } from '../../../shared/service/User.service';
import { PaymentMethodService } from '../../../shared/service/PaymentMethod.service';
import { SaleService } from '../../../shared/service/Sale.service';
import { ProductosService } from '../../../shared/service/productos.service';
import { SaleLine } from '../../../shared/interface/SaleLine';
import { Order, OrderResponse } from '../../../shared/interface/Order';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [CommonModule, FormsModule, CreaPedidoComponent, ConfirmDialogComponent],
  templateUrl: './pedidos.component.html',
})
export class PedidosComponent implements OnInit, OnDestroy {
  pedidoParaEditar: Order | null = null;
  filtro = '';
  pedidoExpandidoId: number | null = null;
  modalCrearPedidoVisible = false;
  pedidosTransformados = signal<Order[]>([]);
  pedidoAEliminarId = signal<number | null>(null);


  private intervaloRecarga: any;

  constructor(
    private pedidosService: OrderService,
    private userService: UserService,
    private paymentMedthod: PaymentMethodService,
    private ventaService: SaleService,
    private productsService: ProductosService
  ) { }

  users = rxResource({ loader: () => this.userService.getallUser() });
  paymentsMethods = rxResource({ loader: () => this.paymentMedthod.getallMethod() });
  sales = rxResource({ loader: () => this.ventaService.getallSale() });
  products = rxResource({ loader: () => this.productsService.getallProduct() });

  pedidosRaw = rxResource<OrderResponse[], any>({
    loader: () => this.pedidosService.getallPedido()
  });

  ngOnInit() {
    this.iniciarRecargaAutomatica();
  }

  private sincronizarPedidosEffect = effect(() => {
    const raw = this.pedidosRaw.value() ?? [];
    const users = this.users.value() ?? [];
    const methods = this.paymentsMethods.value() ?? [];
    const sales = this.sales.value() ?? [];

    const transformados = raw.map(p => {
      const user = users.find(u => u.id === p.userId)!;
      const paymentMethod = methods.find(pm => pm.id === p.paymentMethodId)!;
      const sale = sales.find(s => s.id === p.saleId)!;
      return {
        ...p,
        user,
        paymentMethod,
        sale
      };
    });

    this.pedidosTransformados.set(transformados);
  });


  ngOnDestroy() {
    clearInterval(this.intervaloRecarga);
  }

  iniciarRecargaAutomatica() {
    this.intervaloRecarga = setInterval(() => {
      this.pedidosRaw.reload();
    }, 10000);
  }

  get pedidosFiltrados(): Order[] {
    const filtroLower = this.filtro.toLowerCase();
    return this.pedidosTransformados().filter(p =>
      p.user?.name?.toLowerCase().includes(filtroLower) ||
      p.paymentMethod?.brand?.toLowerCase().includes(filtroLower)
    );
  }



  getLineasDePedido(pedido: Order): SaleLine[] {
    return pedido.sale?.line ?? [];
  }

  getProductoDeLinea(linea: SaleLine): any {
    return this.products?.value()?.find(p => p.id === linea.product.id);
  }

  esPedidoExpandido(pedidoId: number): boolean {
    return this.pedidoExpandidoId === pedidoId;
  }

  toggleDetallePedido(pedidoId: number): void {
    this.pedidoExpandidoId = this.pedidoExpandidoId === pedidoId ? null : pedidoId;
  }

  eliminarPedido(pedidoId: number) {
    this.pedidoAEliminarId.set(pedidoId);
  }

  confirmarEliminacion() {
    const id = this.pedidoAEliminarId();
    if (id !== null) {
      this.pedidosService.deleteOrder(id).subscribe({
        next: () => {
          this.pedidosRaw.reload();
          this.pedidoAEliminarId.set(null);
        },
        error: (err) => {
          alert('Error al eliminar el pedido');
          this.pedidoAEliminarId.set(null);
        }
      });
    }
  }

  cancelarEliminacion() {
    this.pedidoAEliminarId.set(null);
  }


  crearPedido() {
    this.pedidoParaEditar = null;
    this.modalCrearPedidoVisible = true;
  }

  editarPedido(pedidoId: number) {
    const pedido = this.pedidosTransformados().find(p => p.id === pedidoId);
    if (pedido) {
      this.pedidoParaEditar = pedido;
      this.modalCrearPedidoVisible = true;
    }
  }

  cerrarModalPedido() {
    this.modalCrearPedidoVisible = false;
  }

  onPedidoCreado(nuevoPedido: any) {
    this.pedidosRaw.reload();
    this.sales.reload();
    this.modalCrearPedidoVisible = false;
  }
}
