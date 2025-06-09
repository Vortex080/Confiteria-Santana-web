import { Component, ElementRef, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Product } from '../../../shared/interface/product';
import { rxResource } from '@angular/core/rxjs-interop';
import { ProductosService } from '../../../shared/service/productos.service';
import { CategoryService } from '../../../shared/service/Category.service';
import { Category } from '../../../shared/interface/category';



@Component({
  selector: 'app-tpv',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tpv.component.html',
})
export class TpvComponent {

  constructor(private productosService: ProductosService, private categoriaService: CategoryService) { }

  productos = rxResource({
    loader: () => this.productosService.getallProduct(),
  })


  carrito: any[] = [];

  agregarProducto(producto: any) {
    const item = this.carrito.find(p => p.id === producto.id);
    if (item) {
      item.cantidad++;
    } else {
      this.carrito.push({ ...producto, cantidad: 1 });
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
    if (item && nuevoPrecio >= 0) item.precio = nuevoPrecio;
  }

  total(): number {
    return this.carrito.reduce((s, p) => s + p.precio * p.cantidad, 0);
  }

  realizarVenta() {
    alert('Venta realizada');
    this.carrito = [];
  }

  busqueda: string = '';

  get productosFiltrados() {
    return this.productos.value()?.filter(p =>
      p.name.toLowerCase().includes(this.busqueda.toLowerCase())
    );
  }

  categorias = rxResource({
    loader: () => this.categoriaService.getallCategory(),
  })

  categoriaSeleccionada: any = null;


  seleccionarCategoria(categoria: Category) {
    this.categoriaSeleccionada = categoria;
  }

  deseleccionarCategoria() {
    this.categoriaSeleccionada = null;
  }

  cerrarTPV() {
    alert('Cerrar TPV'); // Aquí luego defines el comportamiento real
  }

  @ViewChild('tablaContainer') tablaContainer!: ElementRef;

  scrollArriba() {
    const contenedor = this.tablaContainer.nativeElement;
    contenedor.scrollTop -= 60; // Ajusta la cantidad de scroll si quieres
  }

  scrollAbajo() {
    const contenedor = this.tablaContainer.nativeElement;
    contenedor.scrollTop += 60;
  }

  productoSeleccionado: any = null;

  seleccionarProducto(producto: any) {
    this.productoSeleccionado = producto;
  }

  incrementarCantidadSeleccionado() {
    if (this.productoSeleccionado) {
      this.productoSeleccionado.cantidad++;
    }
  }

  @ViewChild('scrollZona') scrollZona!: ElementRef;

  scrollZonaArriba() {
    this.scrollZona.nativeElement.scrollTop -= 60;
  }

  scrollZonaAbajo() {
    this.scrollZona.nativeElement.scrollTop += 60;
  }

  entradaTactil: string = '';
  modoEntrada: 'cantidad' | 'precio' | 'descuento' | null = null;



  sumarCantidad() {
    if (this.productoSeleccionado) {
      this.productoSeleccionado.cantidad++;
    }
  }

  restarCantidad() {
    if (this.productoSeleccionado && this.productoSeleccionado.cantidad > 1) {
      this.productoSeleccionado.cantidad--;
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
        this.productoSeleccionado.precio = valor;
        break;
      case 'descuento':
        // Aplica tu lógica si añades descuento más adelante
        break;
    }

    // Limpiar entrada y modo
    this.entradaTactil = '';
    this.modoEntrada = null;
  }

  mostrarBuscador: boolean = false;

  get productosFiltradosPorCategoria(): Product[] {
    if (!this.categoriaSeleccionada) return this.productos.value() || [];
    console.log(this.categoriaSeleccionada);
    console.log(this.categorias.value());
    console.log(this.productos.value());
    return this.productos.value()!.filter(p => p.category === this.categoriaSeleccionada.id) || [];

  }

  vaciarCarrito() {
    this.carrito = [];
    this.productoSeleccionado = null; // opcional: deselecciona también el producto activo
  }


}
