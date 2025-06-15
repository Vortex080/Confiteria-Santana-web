import { Component, inject, OnInit } from '@angular/core';
import { ProductosService } from '../../service/productos.service';
import { Product } from '../../interface/product';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  productosRecomendados: Product[] = [];
  private productosService = inject(ProductosService);

  ngOnInit(): void {
    this.productosService.getallProduct().subscribe((productos) => {
      this.productosRecomendados = productos.slice(0, 8); // Los primeros 8 como recomendados
    });
  }
}
