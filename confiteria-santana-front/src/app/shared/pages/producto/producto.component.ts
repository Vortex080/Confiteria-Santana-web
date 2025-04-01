import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-producto',
  imports: [],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css'
})
export class ProductoComponent {
  @Input() producto!: {
    nombre: string;
    foto: string;
    descripcion: string;
    ingredientes: string[];
    alergenos?: string[];
  };

  anadirACesta(producto: any): void {
    // Aquí puedes conectarte a un servicio de carrito, emitir un evento, etc.
    console.log('Producto añadido a la cesta:', producto);
    // Ejemplo de servicio:
    // this.carritoService.agregarProducto(producto);
  }
}