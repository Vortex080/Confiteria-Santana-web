import { Component, input, Input } from '@angular/core';
import { LocalStorageService } from '../../service/LocalStorage.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { CartService } from '../../service/CartService.service';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  sanitizedPhoto: SafeHtml | null = null;

  cartCount = 0;

  constructor(private cartService: CartService) {
    this.updateCartCount();
    window.addEventListener('storage', () => this.updateCartCount());
  }

  updateCartCount() {
    this.cartCount = this.cartService.getCount();
  }


  user = input<any>(null);
  token = input<string | null>(null);

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    location.reload(); // o usar router.navigate(['/']) para volver a inicio
  }


}
