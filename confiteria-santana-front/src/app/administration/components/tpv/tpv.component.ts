import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../../shared/interface/product';
import { rxResource } from '@angular/core/rxjs-interop';
import { ProductosService } from '../../../shared/service/productos.service';
import { CategoryService } from '../../../shared/service/Category.service';
import { Category } from '../../../shared/interface/category';
import { SaleService } from '../../../shared/service/Sale.service';
import { Sale } from '../../../shared/interface/Sale';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tpv',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tpv.component.html',
})
export class TpvComponent {
  constructor(
    private productosService: ProductosService,
    private categoriaService: CategoryService,
    private saleService: SaleService,
    private router: Router
  ) { }

  metodoPago: 'Efectivo' | 'Tarjeta' = 'Efectivo';
  productos = rxResource({ loader: () => this.productosService.getallProduct() });
  categorias = rxResource({ loader: () => this.categoriaService.getallCategory() });

  carrito: any[] = [];
  productoSeleccionado: any = null;
  mostrarModalVenta = false;
  efectivoEntregado: number | null = null;
  busqueda: string = '';
  categoriaSeleccionada: Category | null = null;
  mostrarBuscador = false;
  entradaTactil: string = '';
  modoEntrada: 'cantidad' | 'precio' | 'descuento' | null = null;

  agregarProducto(producto: any) {
    const item = this.carrito.find(p => p.id === producto.id);
    if (item) {
      item.cantidad++;
    } else {
      this.carrito.push({ ...producto, cantidad: 1, price: producto.price, descuento: 0 });
    }
  }

  eliminarProducto(id: number) {
    this.carrito = this.carrito.filter(p => p.id !== id);
  }

  cambiarCantidad(id: number, nuevaCantidad: number) {
    const item = this.carrito.find(p => p.id === id);
    if (item && nuevaCantidad > 0) item.cantidad = nuevaCantidad;
  }

  cambiarPrecio(id: number, nuevoPrecio: number) {
    const item = this.carrito.find(p => p.id === id);
    if (item && nuevoPrecio >= 0) item.price = nuevoPrecio;
  }

  total(): number {
    return this.carrito.reduce((s, p) => s + p.price * p.cantidad * (1 - (p.descuento || 0) / 100), 0);
  }

  confirmarVenta() {
    const errores: string[] = [];

    if (!this.metodoPago) errores.push('Debes seleccionar un método de pago.');
    if (this.carrito.length === 0) errores.push('Debes añadir productos al carrito.');
    if (this.metodoPago === 'Efectivo' && (this.efectivoEntregado === null || this.efectivoEntregado < this.total())) {
      errores.push('El efectivo entregado es insuficiente.');
    }

    if (errores.length > 0) return;

    const venta: Sale = {
      id: Date.now(),
      date: new Date().toISOString(),
      metodoPago: this.metodoPago,
      total: this.total(),
      line: this.carrito.map(p => ({
        id: 0,
        cuantity: p.cantidad,
        price: p.price,
        subtotal: p.cantidad * p.price * (1 - (p.descuento || 0) / 100),
        product: {
          id: p.id,
          name: p.name,
          description: p.description,
          price: p.price,
          unit: p.unit,
          alergens: p.alergens,
          category: p.category,
          photos: p.photos
        }
      }))
    };

    this.saleService.crearSale(venta).subscribe({
      next: () => {
        this.carrito = [];
        this.efectivoEntregado = null;
        this.mostrarModalVenta = false;
      },
      error: err => {
        console.error('Error al guardar venta:', err);
        alert('Error al guardar la venta. Inténtalo de nuevo.');
      }
    });
  }

  get productosFiltrados() {
    return this.productos.value()?.filter(p =>
      p.name.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  seleccionarCategoria(categoria: Category) {
    this.categoriaSeleccionada = categoria;
  }

  deseleccionarCategoria() {
    this.categoriaSeleccionada = null;
  }

  cerrarTPV() {
    this.router.navigate(['/']);
  }


  realizarVenta() {
    this.mostrarModalVenta = true;
  }

  @ViewChild('tablaContainer') tablaContainer!: ElementRef;
  @ViewChild('scrollZona') scrollZona!: ElementRef;

  scrollArriba() {
    this.tablaContainer.nativeElement.scrollTop -= 60;
  }

  scrollAbajo() {
    this.tablaContainer.nativeElement.scrollTop += 60;
  }

  scrollZonaArriba() {
    this.scrollZona.nativeElement.scrollTop -= 60;
  }

  scrollZonaAbajo() {
    this.scrollZona.nativeElement.scrollTop += 60;
  }

  seleccionarProducto(producto: any) {
    this.productoSeleccionado = producto;
  }

  incrementarCantidadSeleccionado() {
    if (this.productoSeleccionado) {
      this.productoSeleccionado.cantidad++;
    }
  }

  sumarCantidad() {
    if (!this.productoSeleccionado) return;

    switch (this.modoEntrada) {
      case 'cantidad':
        this.productoSeleccionado.cantidad++;
        break;
      case 'precio':
        this.productoSeleccionado.price = Math.round((this.productoSeleccionado.price + 0.1) * 100) / 100;
        break;
      case 'descuento':
        this.productoSeleccionado.descuento = Math.min((this.productoSeleccionado.descuento || 0) + 1, 100);
        break;
      default:
        this.productoSeleccionado.cantidad++;
        break;
    }
  }

  restarCantidad() {
    if (!this.productoSeleccionado) return;

    switch (this.modoEntrada) {
      case 'cantidad':
        if (this.productoSeleccionado.cantidad > 1) this.productoSeleccionado.cantidad--;
        break;
      case 'precio':
        this.productoSeleccionado.price = Math.max(0, Math.round((this.productoSeleccionado.price - 0.1) * 100) / 100);
        break;
      case 'descuento':
        this.productoSeleccionado.descuento = Math.max((this.productoSeleccionado.descuento || 0) - 1, 0);
        break;
      default:
        if (this.productoSeleccionado.cantidad > 1) this.productoSeleccionado.cantidad--;
        break;
    }
  }



  agregarDigito(digito: string) {
    this.entradaTactil += digito;
  }

  setModoEntrada(modo: 'cantidad' | 'precio' | 'descuento') {
    this.modoEntrada = modo;
  }

  confirmarEntrada() {
    const valor = parseFloat(this.entradaTactil);
    if (!this.productoSeleccionado || isNaN(valor) || this.modoEntrada === null) return;

    switch (this.modoEntrada) {
      case 'cantidad':
        this.productoSeleccionado.cantidad = valor;
        break;
      case 'precio':
        this.productoSeleccionado.price = valor;
        break;
      case 'descuento':
        this.productoSeleccionado.descuento = valor;
        break;
    }

    this.entradaTactil = '';
    this.modoEntrada = null;
  }

  get productosFiltradosPorCategoria(): Product[] {
    const productos = this.productos.value() || [];

    if (!this.categoriaSeleccionada) return [];

    return productos
      .filter(p => p.category.id === this.categoriaSeleccionada!.id)
      .filter(p => p.name.toLowerCase().includes(this.busqueda.toLowerCase()));
  }


  vaciarCarrito() {
    this.carrito = [];
    this.productoSeleccionado = null;
  }

  cancelarVenta() {
    this.efectivoEntregado = null;
    this.mostrarModalVenta = false;
  }

  get cambio(): number {
    if (this.efectivoEntregado === null) return 0;
    return this.efectivoEntregado - this.total();
  }
}
