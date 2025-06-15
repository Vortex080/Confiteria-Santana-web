import { Component } from '@angular/core';
import { rxResource } from '@angular/core/rxjs-interop';
import { FormsModule } from '@angular/forms';
import { ProductModalComponent } from '../product-modal/product-modal.component'
import { ProductosService } from '../../../shared/service/productos.service';
import { Product } from '../../../shared/interface/product';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ConfirmDialogComponent } from '../../../shared/components/confirm-dialog/confirm-dialog.component';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-admin-productos',
  imports: [CommonModule, FormsModule, ProductModalComponent, ConfirmDialogComponent],
  templateUrl: './admin-productos.component.html',
  styleUrl: './admin-productos.component.css',
})
export class AdminProductosComponent {
  visible = false;
  productoEditando: Product | null = null;
  visibleEliminar = false;

  constructor(private productosService: ProductosService, private sanitizer: DomSanitizer) { }

  abrir() {
    this.productoEditando = null; // modo creación
    this.visible = true;
  }

  cerrar() {
    this.visible = false;
    this.products.reload();
  }

  editarProducto(id: number) {
    const prod = this.products.value()?.find(p => p.id === id);
    if (prod) {
      this.productoEditando = prod;
      this.visible = true;
    }
  }

  products = rxResource({
    loader: () => this.productosService.getallProduct(),
  });

  filtro = '';
  get productosFiltrados() {
    return this.products.value()?.filter(p =>
      p.name.toLowerCase().includes(this.filtro.toLowerCase())
    );
  }

  eliminarProducto(id: number) {
    this.productosService.deleteProduct(id).subscribe({
      next: (response) => {
        console.log('Producto eliminado:', response);
        this.actualizarProducto();
      }
      , error: (error) => {
        console.error('Error al eliminar el producto:', error);
      }
    });
  }

  actualizarProducto() {
    this.products.reload();
  }


  productoAEliminar: any = null;
  mostrarConfirmacion: boolean = false;

  confirmarEliminacion(producto: any) {
    this.productoAEliminar = producto;
    this.visibleEliminar = true;
  }

  cancelarEliminacion() {
    this.productoAEliminar = null;
    this.visibleEliminar = false;
  }

  eliminarConfirmado() {
    if (this.productoAEliminar) {
      this.eliminarProducto(this.productoAEliminar.id);
      this.productoAEliminar = null;
    }
    this.visibleEliminar = false;
  }


  getSafeImage(url: string): SafeUrl {
    if (!url) return 'assets/img/placeholder.png';

    if (url.startsWith('data:image/')) {
      return this.sanitizer.bypassSecurityTrustUrl(url);
    }

    return url; // ya es una URL válida
  }

  

}
