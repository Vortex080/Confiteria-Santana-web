import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenulateralComponent } from "../menulateral/menulateral.component";

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, MenulateralComponent],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent implements OnInit {

  productos = [
    { id: 1, nombre: 'Producto 1', categoria: 'Galletas' },
    { id: 2, nombre: 'Producto 2', categoria: 'Galletas' },
    { id: 3, nombre: 'Producto 3', categoria: 'Bolleria' },
    { id: 4, nombre: 'Producto 4', categoria: 'Bolleria' },
    { id: 5, nombre: 'Producto 5', categoria: 'Tartas' },
    { id: 6, nombre: 'Producto 6', categoria: 'Tartas' },
    { id: 7, nombre: 'Producto 7', categoria: 'Reposteria' },
    { id: 8, nombre: 'Producto 8', categoria: 'Reposteria' },
    { id: 9, nombre: 'Producto 9', categoria: 'Reposteria' }
  ];

  categorias: string[] = [];
  productosFiltrados: { [key: string]: any[] } = {};

  ngOnInit() {
    this.productosFiltrados = this.productos.reduce((acc, producto) => {
      if (!acc[producto.categoria]) {
        acc[producto.categoria] = [];
      }
      acc[producto.categoria].push(producto);
      return acc;
    }, {} as { [key: string]: any[] });

    this.categorias = Object.keys(this.productosFiltrados);
  }
  sidebarVisible: boolean = window.innerWidth >= 768; // Mostrar si es >= md


  // Escuchar cambios en el tamaño de la pantalla
  @HostListener('window:resize', [])
  onResize() {
    this.updateSidebarVisibility();
  }

  updateSidebarVisibility(): void {
    if (window.innerWidth >= 768) {
      this.sidebarVisible = true;  // Siempre visible en pantallas grandes
    }
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }

  isSidebarVisible(): boolean {
    return this.sidebarVisible;
  }
}
