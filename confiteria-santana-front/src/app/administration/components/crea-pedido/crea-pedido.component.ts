import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';

import { User } from '../../../shared/interface/user';
import { SaleLine } from '../../../shared/interface/SaleLine';
import { Product } from '../../../shared/interface/product';
import { Order } from '../../../shared/interface/Order';

import { UserService } from '../../../shared/service/User.service';
import { SaleService } from '../../../shared/service/Sale.service';
import { OrderService } from '../../../shared/service/Order.service';
import { PaymentMethodService } from '../../../shared/service/PaymentMethod.service';
import { PaymentMethod } from '../../../shared/interface/PaymentMethod';
import { ProductosService } from '../../../shared/service/productos.service';
import { OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-crea-pedido',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crea-pedido.component.html',
})
export class CreaPedidoComponent implements OnChanges {
  @Input() show = false;
  @Output() close = new EventEmitter<void>();
  @Output() pedidoCreado = new EventEmitter<void>();
  @Input() pedidoInicial: Order | null = null;

  modoEdicion = false;





  users = rxResource({ loader: () => this.userService.getallUser() });
  paymentMethods = rxResource({ loader: () => this.paymentMethodService.getallMethod() });
  products = rxResource({ loader: () => this.productosService.getallProduct() });

  pedido: Order = this.resetPedido();
  lineaSeleccionadaIndex: number | null = null;
  filtroProducto = '';
  filtroCliente = '';
  animacionSalida = false;
  erroresFormulario: string[] = [];

  constructor(
    private userService: UserService,
    private ventaService: SaleService,
    private orderService: OrderService,
    private paymentMethodService: PaymentMethodService,
    private productosService: ProductosService
  ) { }

  ngOnChanges(): void {
    if (this.pedidoInicial) {
      this.pedido = JSON.parse(JSON.stringify(this.pedidoInicial)); // clon profundo para evitar mutaciones
      this.modoEdicion = true;
    } else {
      this.pedido = this.resetPedido();
      this.modoEdicion = false;
    }
  }





  compareById(o1: any, o2: any): boolean {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  abrirSelectorProducto(index: number) {
    this.lineaSeleccionadaIndex = index;
  }

  cerrarSelectorProducto() {
    this.lineaSeleccionadaIndex = null;
    this.filtroProducto = '';
  }

  seleccionarProducto(producto: Product) {
    if (this.lineaSeleccionadaIndex === null) return;

    const linea = this.pedido.sale.line[this.lineaSeleccionadaIndex];
    linea.product = producto;
    linea.price = producto.price;
    linea.subtotal = linea.cuantity * linea.price;

    this.cerrarSelectorProducto();
  }

  productosFiltrados(): Product[] {
    const todos = this.products.value() ?? [];
    return todos.filter(p =>
      p.name.toLowerCase().includes(this.filtroProducto.toLowerCase())
    );
  }

  clientesFiltrados(): User[] {
    const todos = this.users.value() ?? [];
    return todos.filter(u =>
      `${u.name} ${u.lastname}`.toLowerCase().includes(this.filtroCliente.toLowerCase())
    );
  }

  seleccionarCliente(user: User) {
    this.pedido.user = user;
    this.pedido.shipping = { ...user.address };
    this.pedido.billingAddress = { ...user.address };
    this.filtroCliente = '';
  }

  agregarProducto() {
    const nuevaLinea: SaleLine = {
      id: 0,
      product: {} as Product,
      cuantity: 1,
      price: 0,
      subtotal: 0
    };

    this.pedido.sale.line.push(nuevaLinea);
  }

  eliminarProducto(i: number) {
    this.pedido.sale.line.splice(i, 1);
  }

  calcularTotal(): number {
    this.pedido.sale.line.forEach(l => {
      l.subtotal = l.cuantity * l.price;
    });
    return this.pedido.sale.line.reduce((sum, l) => sum + l.subtotal, 0);
  }

  guardar() {
    this.erroresFormulario = [];

    // Validaciones
    if (!this.pedido.user.id) {
      this.erroresFormulario.push('Debes seleccionar un cliente');
    }

    if (this.pedido.paymentMethod.id === undefined || this.pedido.paymentMethod.id === null) {
      this.erroresFormulario.push('Debes seleccionar un método de pago');
    }

    if (this.pedido.sale.line.length === 0 || this.pedido.sale.line.some(l => !l.product?.id)) {
      this.erroresFormulario.push('Debes añadir al menos un producto al pedido');
    }

    if (this.erroresFormulario.length > 0) return;

    // Calcular valores
    this.pedido.sale.total = this.calcularTotal();
    this.pedido.sale.date = new Date().toISOString();
    this.pedido.created_at = new Date().toISOString();

    const subtotalProductos = this.calcularTotal();
    const costeEnvio = Number(this.pedido.sale.total) || 0;

    this.pedido.sale.total = subtotalProductos; // subtotal sin envío
    this.pedido.total = subtotalProductos + costeEnvio; // total con envío


    // Obtener método de pago completo si no es efectivo
    let paymentMethod = this.pedido.paymentMethod;
    if (paymentMethod.id !== 0) {
      const encontrado = this.paymentMethods.value()?.find(pm => pm.id === paymentMethod.id);
      if (!encontrado) {
        this.erroresFormulario.push('No se encontró el método de pago del usuario');
        return;
      }
      paymentMethod = encontrado;
    }

    const pedidoAEnviar: Order = {
      id: this.pedido.id,
      user: this.pedido.user,
      shipping: this.pedido.shipping,
      billingAddress: this.pedido.billingAddress,
      paymentMethod: {
        id: paymentMethod.id,
        userId: paymentMethod.userId,
        provider: paymentMethod.provider,
        token: paymentMethod.token,
        type: paymentMethod.type,
        last4: paymentMethod.last4,
        expiryMonth: paymentMethod.expiryMonth,
        expiryYear: paymentMethod.expiryYear,
        brand: paymentMethod.brand
      },
      total: this.pedido.total,
      sale: this.pedido.sale,
      created_at: this.pedido.created_at
    };

    if (this.modoEdicion) {
      // Actualizar pedido existente
      this.orderService.updateOrder(pedidoAEnviar).subscribe({
        next: () => {
          this.pedido = this.resetPedido();
          this.pedidoCreado.emit(); // o puedes emitir un evento diferente si lo prefieres
        },
        error: (err) => alert('Error al actualizar pedido: ' + err.message)
      });
    } else {
      // Crear nuevo pedido
      this.orderService.crearOrder(pedidoAEnviar).subscribe({
        next: () => {
          this.pedido = this.resetPedido();
          this.pedidoCreado.emit();
        },
        error: (err) => alert('Error al guardar pedido: ' + err.message)
      });
    }
  }


  getMetodosDePagoFiltrados(): PaymentMethod[] {
    const todos = this.paymentMethods.value() ?? [];
    const userId = this.pedido.user?.id;

    if (!userId) return todos.filter(pm => pm.id === 0);
    return todos.filter(pm =>
      pm.id === 0 || Number((pm as any).userId ?? (pm as any).user?.id) === Number(userId)
    );
  }




  cancelar() {
    this.close.emit();
  }

  limpiarCliente() {
    this.pedido.user = {} as User;
    this.pedido.paymentMethod = {
      id: 0,
      provider: '',
      token: '',
      type: '',
      last4: '',
      expiryMonth: 0,
      expiryYear: 0,
      brand: '',
      userId: 0
    };
    this.pedido.shipping = this.resetPedido().shipping;
    this.pedido.billingAddress = this.resetPedido().billingAddress;
  }


  private resetPedido(): Order {
    return {
      id: 0,
      user: {} as User,
      shipping: {
        id: '',
        street: '',
        number: '',
        flat: '',
        door: '',
        city: '',
        state: '',
        country: '',
        postalCode: ''
      },
      billingAddress: {
        id: '',
        street: '',
        number: '',
        flat: '',
        door: '',
        city: '',
        state: '',
        country: '',
        postalCode: ''
      },
      paymentMethod: {
        id: 0,
        provider: '',
        token: '',
        type: '',
        last4: '',
        expiryMonth: 0,
        expiryYear: 0,
        brand: '',
        userId: 0
      },
      total: 0,
      sale: {
        id: 0,
        date: '',
        metodoPago: '',
        total: 0,
        line: []
      },
      created_at: ''
    };
  }
}