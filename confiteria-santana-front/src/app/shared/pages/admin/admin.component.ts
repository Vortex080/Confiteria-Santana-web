import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterOutlet } from '@angular/router';
import { NgIf, NgClass } from '@angular/common';
import { ProductosService } from '../../service/productos.service';
import { Product } from '../../interface/product';

@Component({
  selector: 'app-admin',
  imports: [RouterOutlet, NgIf, NgClass],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminComponent {
  collapsed = false;
  constructor(private router: Router, private productosService: ProductosService) { }

  productos: Product[] = [];

  goToHome(): void {
    this.router.navigate(['/home']);
  }
}
