import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ProductosService } from '../../service/productos.service';
import { Product } from '../../interface/product';
import { CartService } from '../../service/CartService.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-producto',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './producto.component.html',
  styleUrl: './producto.component.css'
})
export class ProductoComponent implements OnInit {
  producto!: Product;
  cantidad: number = 1;

  private route: ActivatedRoute = inject(ActivatedRoute);
  private productosService: ProductosService = inject(ProductosService);
  private cartService = inject(CartService);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.productosService.getProductById(+id).subscribe(product => {
        this.producto = product;
      });
    }
  }

  anadirACesta(producto: Product): void {
    this.cartService.addProduct(producto, this.cantidad); // usar cantidad

    const icon = document.querySelector('#cart-icon');
    if (icon) {
      icon.classList.add('animate-bounce');
      setTimeout(() => icon.classList.remove('animate-bounce'), 500);
    }

    console.log(`Añadido ${this.cantidad}x`, producto);
  }
}

