import { Component, OnInit, HostListener, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenulateralComponent } from "../menulateral/menulateral.component";
import { Product } from '../../interface/product';
import { ProductosService } from '../../service/productos.service'; // Asegúrate de que la ruta sea correcta
import { rxResource } from '@angular/core/rxjs-interop';
import { CategoryService } from '../../service/Category.service';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [CommonModule, MenulateralComponent, RouterModule],
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css'
})
export class ProductosComponent {
  sidebarVisible: boolean = window.innerWidth >= 768;

  constructor(private productosService: ProductosService, private categoryService: CategoryService) { }

  categories = rxResource({ loader: () => this.categoryService.getallCategory() });
  products = rxResource({ loader: () => this.productosService.getallProduct() });

  productosFiltrados = computed(() => {
    const productos = this.products.value()?.length ? this.products.value()! : [];
    const categorias = this.categories.value()?.length ? this.categories.value()! : [];

    console.log('Productos:', productos);
    console.log('Categorias:', categorias);

    const prodsConCat = productos.map(p => {
      const categoria = categorias.find(c => c.id === p.category.id);
      return {
        ...p,
        categoryName: categoria?.name ?? 'Sin categoría'
      };
    });

    const agrupados = prodsConCat.reduce((acc, producto) => {
      const nombre = producto.categoryName;
      if (!acc[nombre]) acc[nombre] = [];
      acc[nombre].push(producto);
      return acc;
    }, {} as { [key: string]: Product[] });

    console.log('Filtrados:', agrupados);
    return agrupados;
  });


  nombresCategorias = computed(() => Object.keys(this.productosFiltrados()));

  @HostListener('window:resize', [])
  onResize() {
    this.updateSidebarVisibility();
  }

  updateSidebarVisibility(): void {
    this.sidebarVisible = window.innerWidth >= 768;
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }

  isSidebarVisible(): boolean {
    return this.sidebarVisible;
  }

  get categoriasView(): string[] {
    return this.nombresCategorias();
  }

  get productosView(): { [key: string]: Product[] } {
    return this.productosFiltrados();
  }


}