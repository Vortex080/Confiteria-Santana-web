import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menulateral',
  imports: [CommonModule],
  templateUrl: './menulateral.component.html',
  styleUrl: './menulateral.component.css'

})
export class MenulateralComponent {


  collapsed = false;
  sidebarVisible = false;

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
  }

  isSidebarVisible(): boolean {
    return this.sidebarVisible || window.innerWidth >= 768;
  }

  // Método que alterna la visibilidad del sideba
  categorias: string[] = ['Galletas', 'Bolleria', 'Tartas', 'Reposteria']; // Asegúrate de que coincida con el padre

  scrollToCategory(categoria: string) {
    const el = document.getElementById(categoria);
    if (el) {
      el.scrollIntoView({ behavior: 'smooth' });
      if (window.innerWidth < 768) {
        this.sidebarVisible = false;
      }
    }
  }

}
