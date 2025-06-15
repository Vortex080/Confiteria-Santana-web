import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-unauthorized',
  imports: [CommonModule, RouterModule],
  template: `
    <div class="flex flex-col items-center justify-center min-h-screen bg-base-200 text-center p-8">
      <h1 class="text-6xl font-bold text-error">401</h1>
      <p class="text-xl mt-4">No tienes permisos para acceder a esta página.</p>
      <a routerLink="/" class="btn btn-primary mt-6">Volver al inicio</a>
    </div>
  `
})
export class UnauthorizedComponent {}
