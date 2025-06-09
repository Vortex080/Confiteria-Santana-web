import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';

/** Interfaces */

interface Stock {
  product: number;
  name: string;
  quantity: number;
}

interface StockMovement {
  product: number;
  name: string;
  type: 'ENTRADA' | 'SALIDA' | 'AJUSTE';
  unit: string;
  reason: string;
  quantity: number;
  date: string;
}

interface NewMovement {
  product: number | null;
  type: 'ENTRADA' | 'SALIDA' | 'AJUSTE';
  quantity: number | null;
  unit: string;
  reason: string;
}

@Component({
  selector: 'app-almacen',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './almacen.component.html'
})
export class AlmacenComponent implements OnInit {
  @ViewChild('modalRef') modalRef!: ElementRef<HTMLDialogElement>;

  stockList: Stock[] = [
    { product: 1, name: 'Chocolate', quantity: 100 },
    { product: 2, name: 'Harina', quantity: 300 },
    { product: 3, name: 'Azúcar', quantity: 200 }
  ];

  movements: StockMovement[] = [
    {
      product: 1,
      name: 'Chocolate',
      type: 'ENTRADA',
      unit: 'kg',
      reason: 'Entrada',
      quantity: 50,
      date: '2025-05-20'
    }
  ];

  newMovement: NewMovement = {
    product: null,
    type: 'ENTRADA',
    quantity: null,
    unit: '',
    reason: ''
  };

  ngOnInit(): void { }

  openModal(): void {
    this.modalRef.nativeElement.showModal();
  }

  closeModal(): void {
    this.modalRef.nativeElement.close();
  }

  addMovement(): void {
    if (this.newMovement.product === null || this.newMovement.quantity === null) return;

    const productName =
      this.stockList.find(p => p.product === this.newMovement.product)?.name || 'Desconocido';

    const newEntry: StockMovement = {
      product: this.newMovement.product,
      name: productName,
      type: this.newMovement.type,
      unit: this.newMovement.unit,
      reason: this.newMovement.reason,
      quantity: this.newMovement.quantity,
      date: new Date().toISOString().split('T')[0]
    };

    this.movements.unshift(newEntry);

    const stockItem = this.stockList.find(p => p.product === this.newMovement.product);
    if (stockItem) {
      if (this.newMovement.type === 'ENTRADA') {
        stockItem.quantity += this.newMovement.quantity;
      } else if (this.newMovement.type === 'SALIDA') {
        stockItem.quantity -= this.newMovement.quantity;
      }
      // AJUSTE no modifica el stock automáticamente por ahora
    }

    this.closeModal();

    this.newMovement = {
      product: null,
      type: 'ENTRADA',
      quantity: null,
      unit: '',
      reason: ''
    };
  }
}
