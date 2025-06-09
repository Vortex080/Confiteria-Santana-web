import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-confirm-dialog',
  standalone: true,
  imports: [CommonModule],
  template: `
    <dialog open class="modal modal-open">
      <div class="modal-box">
        <h3 class="font-bold text-lg text-error">Confirmar eliminación</h3>
        <p class="py-4">
          ¿Estás seguro de que quieres eliminar el producto
          <strong>{{ nombre }}</strong>?
        </p>
        <div class="modal-action">
          <button class="btn btn-outline" (click)="cancelar()">Cancelar</button>
          <button class="btn btn-error" (click)="confirmar()">Eliminar</button>
        </div>
      </div>
    </dialog>
  `
})
export class ConfirmDialogComponent {
  @Input() nombre: string = '';
  @Output() onConfirm = new EventEmitter<void>();
  @Output() onCancel = new EventEmitter<void>();

  confirmar() {
    this.onConfirm.emit();
  }

  cancelar() {
    this.onCancel.emit();
  }
}
