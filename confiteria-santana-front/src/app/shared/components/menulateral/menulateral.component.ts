import { Component, input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menulateral',
  imports: [CommonModule],
  templateUrl: './menulateral.component.html',
  styleUrl: './menulateral.component.css'

})
export class MenulateralComponent {


  // Variable que mantiene el estado de visibilidad del sidebar
  sidebarVisible: boolean = true;

  // Retorna el estado actual del sidebar
  isSidebarVisible(): boolean {
    return this.sidebarVisible;
  }

  // Método que alterna la visibilidad del sidebar
  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }
  categorias: string[] = ['Galletas', 'Bolleria', 'Tartas', 'Reposteria']; // Asegúrate de que coincida con el padre

  scrollToCategory(categoria: string) {
    const elemento = document.getElementById(categoria);
    if (elemento) {
      elemento.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
  }
}
