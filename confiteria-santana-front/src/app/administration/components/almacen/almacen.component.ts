import { CommonModule } from '@angular/common';
import { Component, ElementRef, ViewChild, computed, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { rxResource } from '@angular/core/rxjs-interop';

import { Stock } from '../../../shared/interface/Stock';
import { StockService } from '../../../shared/service/Stock.service';
import { StockMovementsService } from '../../../shared/service/StockMovements.service';
import { MovementReason } from '../../../shared/interface/MovementReason';
import { MovementType } from '../../../shared/interface/MovementType';
import { Product } from '../../../shared/interface/product';
import { ProductosService } from '../../../shared/service/productos.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { StockMovementsInput, StockMovementsOutput } from '../../../shared/interface/StockMovements';

// Interfaz para vista
interface StockMovementView extends Omit<StockMovementsInput, 'create_at'> {
  create_at: Date | null;
}

@Component({
  selector: 'app-almacen',
  standalone: true,
  imports: [CommonModule, FormsModule, NgSelectModule],
  templateUrl: './almacen.component.html'
})
export class AlmacenComponent {
  @ViewChild('modalRef') modalRef!: ElementRef<HTMLDialogElement>;

  constructor(
    private stockService: StockService,
    private stockMovementsService: StockMovementsService,
    private productService: ProductosService
  ) { }

  // Datos cargados
  stockRaw = rxResource({ loader: () => this.stockService.getAllStock() });
  movementsRaw = rxResource({ loader: () => this.stockMovementsService.getAllMovements() });
  productsRaw = rxResource({ loader: () => this.productService.getallProduct() });

  stockList = computed(() => this.stockRaw.value() ?? []);
  movements = computed<StockMovementView[]>(() =>
    (this.movementsRaw.value() ?? []).map((m: any) => {
      const fecha = new Date(m.created_at);
      return {
        ...m,
        create_at: isNaN(fecha.getTime()) ? null : fecha
      };
    }).reverse()
  );
  products = computed<Product[]>(() => this.productsRaw.value() ?? []);

  // Filtros de stock
  stockFilterText = signal('');
  stockFilterId = signal<number | null>(null);

  // Filtros de movimientos
  movementFilterProductId = signal<number | null>(null);
  movementFilterType = signal<MovementType | ''>('');
  movementFilterDate = signal<string>(''); // formato YYYY-MM-DD

  // Ordenación
  sortField = signal<keyof StockMovementView | null>(null);
  sortDirection = signal<'asc' | 'desc'>('asc');

  ordenarPor(campo: keyof StockMovementView) {
    if (this.sortField() === campo) {
      this.sortDirection.set(this.sortDirection() === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortField.set(campo);
      this.sortDirection.set('asc');
    }
  }

  // Computados filtrados y ordenados
  filteredStockList = computed(() => {
    const text = this.stockFilterText().toLowerCase().trim();
    const id = this.stockFilterId();

    return this.stockList().filter(item => {
      const matchName = item.product.name.toLowerCase().includes(text);
      const matchId = id == null || item.id === id;
      return matchName && matchId;
    });
  });

  filteredMovements = computed(() => {
    const productId = this.movementFilterProductId();
    const type = this.movementFilterType();
    const date = this.movementFilterDate();

    let result = this.movements().filter(m => {
      const matchProduct = productId == null || m.product.id === productId;
      const matchType = type === '' || m.type === type;
      const matchDate = date === '' || (m.create_at && m.create_at.toISOString().startsWith(date));
      return matchProduct && matchType && matchDate;
    });

    const campo = this.sortField();
    const direccion = this.sortDirection();

    if (campo) {
      result = result.sort((a, b) => {
        const valA = a[campo];
        const valB = b[campo];

        const compA = valA instanceof Date ? valA.getTime() : valA;
        const compB = valB instanceof Date ? valB.getTime() : valB;

        if (compA! < compB!) return direccion === 'asc' ? -1 : 1;
        if (compA! > compB!) return direccion === 'asc' ? 1 : -1;
        return 0;
      });
    }

    return result;
  });

  // Formulario nuevo movimiento
  newMovement = signal<{
    product: Product | null;
    type: MovementType;
    quantity: number;
    unit: string;
    reason: MovementReason | '';
  }>({
    product: null,
    type: 'ENTRADA',
    quantity: 1,
    unit: '',
    reason: ''
  });

  openModal(): void {
    this.modalRef.nativeElement.showModal();
  }

  closeModal(): void {
    this.modalRef.nativeElement.close();
  }

  addMovement(): void {
    const form = this.newMovement();
    if (!form.product || !form.unit || !form.reason || !form.quantity) {
      alert('Completa todos los campos requeridos');
      return;
    }

    const movimiento: StockMovementsOutput = {
      id: 0,
      product: form.product.id,
      quantity: form.quantity,
      unit: form.unit,
      type: form.type,
      reason: form.reason as MovementReason
    };

    this.stockMovementsService.crearStockMovements(movimiento).subscribe({
      next: () => {
        this.movementsRaw.reload();
        this.stockRaw.reload();
        this.closeModal();
        this.newMovement.set({
          product: null,
          type: 'ENTRADA',
          quantity: 1,
          unit: '',
          reason: ''
        });
      },
      error: err => alert('Error al guardar el movimiento: ' + err.message)
    });
  }

  productSearch = '';
  productosFiltrados: Product[] = [];

  filtrarProductos(): void {
    const query = this.productSearch.toLowerCase().trim();
    this.productosFiltrados = this.products().filter(p =>
      p.name.toLowerCase().includes(query)
    );
  }

  seleccionarProducto(prod: Product): void {
    this.newMovement.update(m => ({ ...m, product: prod }));
    this.productSearch = prod.name;
    this.productosFiltrados = [];
  }

  getProductName(productId: number): string {
    return this.products().find(p => p.id === productId)?.name ?? 'Desconocido';
  }
}
