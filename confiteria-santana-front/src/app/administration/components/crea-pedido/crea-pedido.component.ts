import { Component, EventEmitter, Output, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crea-pedido',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crea-pedido.component.html'
})
export class CreaPedidoComponent {
  @Input() show = false;
  @Output() close = new EventEmitter<void>();
  @Output() pedidoCreado = new EventEmitter<any>();

  pedido = {
    user: { name: '' },
    shippingAddress: { street: '' },
    billingAddress: { street: '' },
    shipping: 0,
    paymentMethod: '',
    venta: {
      lineas: [] as any[]
    },
    date: new Date(),
    total: 0
  };

  agregarProducto() {
    this.pedido.venta.lineas.push({
      nombreProducto: '',
      cantidad: 1,
      precioUnitario: 0
    });
  }

  eliminarProducto(i: number) {
    this.pedido.venta.lineas.splice(i, 1);
  }

  calcularTotal(): number {
    return this.pedido.venta.lineas.reduce((sum, l) => sum + l.cantidad * l.precioUnitario, 0) + this.pedido.shipping;
  }

  guardar() {
    this.pedido.total = this.calcularTotal();
    this.pedido.date = new Date(); // ✅ ahora sí es tipo Date
    this.pedidoCreado.emit(this.pedido);
    this.pedido = this.resetPedido();
  }

  cancelar() {
    this.close.emit();
  }

  private resetPedido() {
    return {
      user: { name: '' },
      shippingAddress: { street: '' },
      billingAddress: { street: '' },
      shipping: 0,
      paymentMethod: '',
      venta: {
        lineas: []
      },
      date: new Date(),
      total: 0
    };
  }
}
