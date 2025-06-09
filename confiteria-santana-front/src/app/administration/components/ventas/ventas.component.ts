import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CreaVentaComponent } from '../crea-venta/crea-venta.component';
import { rxResource } from '@angular/core/rxjs-interop';
import { SaleService } from '../../../shared/service/Sale.service';
import { Sale } from '../../../shared/interface/Sale';
import { map } from 'rxjs/operators';
import { ProductosService } from '../../../shared/service/productos.service';
import { SaleLine } from '../../../shared/interface/SaleLine';


type Venta = Omit<Sale, 'date'> & { date: Date; showLineas: boolean };


@Component({
  selector: 'app-ventas',
  templateUrl: './ventas.component.html',
  imports: [FormsModule, CommonModule, CreaVentaComponent],
  styleUrls: ['./ventas.component.css'] // o .scss
})
export class VentasComponent {


  // Set para llevar los IDs de las ventas expandidas
  expandedIds = new Set<number>();


  constructor(private ventaService: SaleService, private productsService: ProductosService) { }
  filtro: string = '';
  modalVentaVisible = false;

  ventas = rxResource({
    loader: () =>
      this.ventaService.getallSale().pipe(
        map((dtos: Sale[]) =>
          dtos.map(dto => ({
            ...dto,
            date: new Date(dto.date),
            showLineas: false    // <-- aquí!
          }))
        )
      ),
  });


  products = rxResource({
    loader: () => this.productsService.getallProduct(),
  });

  get ventasFiltradas(): Venta[] {
    const ventas = this.ventas.value();
    if (!ventas) {
      return [];
    }

    const fl = this.filtro.toLowerCase();
    return ventas.filter(v =>
      v.metodoPago.toLowerCase().includes(fl) ||
      v.date.toLocaleDateString().includes(this.filtro)
    );
  }

  toggleLineas(venta: Venta): void {
    venta.showLineas = !venta.showLineas;
  }


  getProductoDeLinea(linea: SaleLine): any {
    return this.products?.value()?.find(p => p.id === linea.product.id);
  }

  modalCrearVentaVisible = false;

  abrirModalVenta() {
    this.modalCrearVentaVisible = true;
  }

  cerrarModalVenta() {
    this.modalCrearVentaVisible = false;
  }

  onVentaCreada(ventaDto: Sale) {
    const nueva: Venta = {
      ...ventaDto,
      date: new Date(ventaDto.date),
      showLineas: false      // <-- y aquí
    };
    this.ventas.value()!.push(nueva);
    this.modalCrearVentaVisible = false;
  }

  actualizarVentas() {
    this.ventas.reload();
  }


}
