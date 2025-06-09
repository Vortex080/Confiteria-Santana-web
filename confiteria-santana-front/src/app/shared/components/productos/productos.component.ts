import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenulateralComponent } from "../menulateral/menulateral.component";
import { Product } from '../../interface/product';
import { ProductosService } from '../../service/productos.service'; // Asegúrate de que la ruta sea correcta
import { rxResource } from '@angular/core/rxjs-interop';
import { CategoryService } from '../../service/Category.service';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, MenulateralComponent],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent {

  productos: Product[] = [];
  categorias: string[] = [];
  productosFiltrados: { [key: string]: Product[] } = {};

  sidebarVisible: boolean = window.innerWidth >= 768;

  constructor(private productosService: ProductosService, private categoryService: CategoryService) { }

  categories = rxResource({ loader: () => this.categoryService.getallCategory() });

  nombresCategorias: string[] = [];

  filtrarPorCategorias(): void {
    if (!this.categories.hasValue() || this.productos.length === 0) {
      this.productosFiltrados = {};
      this.nombresCategorias = [];
      return;
    }

    const cats = this.categories.value()!;

    // Unimos productos con el nombre de la categoría
    const prodsConCat = this.productos.map(p => {
      const categoria = cats.find(c => c.id === p.category.id);
      return {
        ...p,
        categoryName: categoria?.name ?? 'Sin categoría'
      };
    });

    // Agrupamos por nombre
    this.productosFiltrados = prodsConCat.reduce((acc, producto) => {
      const nombre = producto.categoryName;
      if (!acc[nombre]) {
        acc[nombre] = [];
      }
      acc[nombre].push(producto);
      return acc;
    }, {} as { [nombre: string]: Product[] });

    this.nombresCategorias = Object.keys(this.productosFiltrados);
  }


  @HostListener('window:resize', [])
  onResize() {
    this.updateSidebarVisibility();
  }

  updateSidebarVisibility(): void {
    if (window.innerWidth >= 768) {
      this.sidebarVisible = true;
    }
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }

  isSidebarVisible(): boolean {
    return this.sidebarVisible;
  }
}
