import { Component, EventEmitter, Output, Input, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';

import { ProductosService } from '../../../shared/service/productos.service';
import { Product } from '../../../shared/interface/product';
import { Sale } from '../../../shared/interface/Sale';
import { SaleLine } from '../../../shared/interface/SaleLine';
import { SaleService } from '../../../shared/service/Sale.service';

@Component({
  selector: 'app-crea-venta',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crea-venta.component.html',
})
export class CreaVentaComponent implements OnInit, OnDestroy {

  constructor(
    private productosService: ProductosService,
    private salesService: SaleService
  ) { }

  @Input() show = false;
  @Output() close = new EventEmitter<void>();
  @Output() ventaCreada = new EventEmitter<Sale>();

  nuevaVenta: Sale = this.getVentaVacia();
  lineaSeleccionadaIndex: number | null = null;

  filtroProducto = '';
  animacionSalida = false;
  mostrarModal = true;
  erroresFormulario: string[] = [];


  products = rxResource({
    loader: () => this.productosService.getallProduct(),
  });

  ngOnInit() {
    document.body.classList.add('modal-open');
  }

  ngOnDestroy() {
    document.body.classList.remove('modal-open');
  }

  private getVentaVacia(): Sale {
    return {
      id: 0,
      date: new Date().toISOString(),
      metodoPago: '',
      total: 0,
      line: [],
    };
  }

  agregarLinea() {
    this.nuevaVenta.line.push({
      id: 0,
      product: {} as Product,
      cuantity: 1,
      price: 0,
      subtotal: 0,
    });
  }

  eliminarLinea(i: number) {
    this.nuevaVenta.line.splice(i, 1);
  }

  abrirSelector(index: number) {
    this.lineaSeleccionadaIndex = index;
  }

  cerrarSelector() {
    this.lineaSeleccionadaIndex = null;
    this.filtroProducto = '';
  }

  productosFiltrados(): Product[] {
    const todos = this.products.value() ?? [];
    return todos.filter(p =>
      p.name.toLowerCase().includes(this.filtroProducto.toLowerCase())
    );
  }

  seleccionarProducto(producto: Product) {
    if (this.lineaSeleccionadaIndex === null) return;

    const linea = this.nuevaVenta.line[this.lineaSeleccionadaIndex];

    const productoFinal = Array.isArray(producto) ? producto[0] : producto;

    linea.id = productoFinal.id;
    linea.product = productoFinal;
    linea.price = productoFinal.price;
    linea.subtotal = linea.cuantity * linea.price;

    this.lineaSeleccionadaIndex = null;
    this.filtroProducto = '';
  }



  actualizarSubtotal(linea: SaleLine) {
    linea.subtotal = linea.cuantity * linea.price;
  }

  get totalVenta(): number {
    return this.nuevaVenta.line.reduce((sum, l) => sum + l.subtotal, 0);
  }

  cerrarConAnimacion() {
    this.animacionSalida = true;
    setTimeout(() => {
      this.show = false;
      this.animacionSalida = false;
      this.close.emit();
      this.nuevaVenta = this.getVentaVacia();
    }, 300);
  }

  cancelar() {
    this.close.emit();
  }

  crearVenta() {
    const errores: string[] = [];

    // Validaciones
    if (!this.nuevaVenta.metodoPago) {
      errores.push('Debes seleccionar un método de pago.');
    }

    if (!this.nuevaVenta.date) {
      errores.push('Debes seleccionar una fecha para la venta.');
    }

    if (this.nuevaVenta.line.length === 0 || this.nuevaVenta.line.some(l => !l.product?.id)) {
      errores.push('Debes añadir al menos un producto a la venta.');
    }

    // Mostrar errores en pantalla
    if (errores.length > 0) {
      this.erroresFormulario = errores;
      return;
    }

    // Si todo está correcto, crear la venta
    const ventaFinal: Sale = {
      ...this.nuevaVenta,
      id: Date.now(),
      date: new Date(this.nuevaVenta.date).toISOString(),
      total: this.totalVenta,
      line: this.nuevaVenta.line.map((l) => ({
        id: l.id,
        cuantity: l.cuantity,
        price: l.price,
        subtotal: l.cuantity * l.price,
        product: Array.isArray(l.product) ? l.product[0] : l.product,
      })),
    };

    console.log('Venta a crear:', ventaFinal);

    this.salesService.crearSale(ventaFinal).subscribe({
      next: () => {
        this.erroresFormulario = [];
        this.ventaCreada.emit(ventaFinal);
        this.cerrarConAnimacion();
      },
      error: err => {
        this.erroresFormulario = ['Error al guardar la venta. Inténtalo de nuevo.'];
      },
    });
  }

}
