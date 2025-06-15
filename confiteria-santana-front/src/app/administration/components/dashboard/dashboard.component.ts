import { Component, inject, OnInit, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { rxResource } from '@angular/core/rxjs-interop';
import { NgApexchartsModule } from 'ng-apexcharts';

import { SaleService } from '../../../shared/service/Sale.service';
import { StockService } from '../../../shared/service/Stock.service';
import { OrderService } from '../../../shared/service/Order.service';
import { ProductosService } from '../../../shared/service/productos.service';

import { Product } from '../../../shared/interface/product';
import { ChartOptions } from '../../../shared/interface/ChartOptions';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NgApexchartsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  private saleService = inject(SaleService);
  private stockService = inject(StockService);
  private orderService = inject(OrderService);
  private productService = inject(ProductosService);

  // Recursos reactivos
  ventas = rxResource({ loader: () => this.saleService.getallSale() });
  pedidos = rxResource({ loader: () => this.orderService.getallPedido() });
  stock = rxResource({ loader: () => this.stockService.getAllStock() });

  // Señal de opciones de gráfico
  chartOptions = computed<Partial<ChartOptions>>(() => {
    const datos = this.ventasSemanales();
    if (!datos.length) return {};

    return {
      series: [
        {
          name: 'Ventas',
          data: datos.map(d => d.total)
        }
      ],
      chart: {
        type: 'line',
        height: 300
      },
      title: {
        text: 'Ventas últimos 7 días',
        align: 'left'
      },
      xaxis: {
        categories: datos.map(d => d.fecha)
      },
      dataLabels: {
        enabled: false
      },
      stroke: {
        curve: 'smooth'
      }
    };
  });

  productosMasVendidos = computed(() => {
    const ventas = this.ventas.value();
    if (!ventas) return [];

    const contador = new Map<number, { product: Product; unidades: number }>();
    for (const venta of ventas) {
      for (const linea of venta.line) {
        if (!linea.product || !linea.product.id) continue;
        const id = linea.product.id;
        const actual = contador.get(id);

        if (actual) {
          actual.unidades += linea.cuantity;
        } else {
          contador.set(id, { product: linea.product, unidades: linea.cuantity });
        }
      }
    }

    return Array.from(contador.values())
      .sort((a, b) => b.unidades - a.unidades)
      .slice(0, 4);
  });

  ingresosTotales = computed(() => {
    const ventas = this.ventas.value();
    if (!ventas) return 0;
    return ventas.reduce((suma, venta) => suma + venta.total, 0);
  });

  pedidosHoy = computed(() => {
    const pedidos = this.pedidos.value();
    if (!pedidos) return 0;

    const hoy = new Date().toISOString().slice(0, 10);
    return pedidos.filter(p => p.created_at.startsWith(hoy)).length;
  });

  productosStockBajo = computed(() => {
    const stock = this.stock.value();
    if (!stock) return [];
    const umbral = 10;
    return stock.filter(item => item.quantity < umbral);
  });

  ventasSemanales = computed(() => {
    const ventas = this.ventas.value();
    if (!ventas) return [];

    const hoy = new Date();
    const dias: { [fecha: string]: number } = {};

    for (let i = 6; i >= 0; i--) {
      const fecha = new Date(hoy);
      fecha.setDate(hoy.getDate() - i);
      const key = fecha.toISOString().slice(0, 10);
      dias[key] = 0;
    }

    for (const venta of ventas) {
      const fechaVenta = venta.date.slice(0, 10);
      if (dias.hasOwnProperty(fechaVenta)) {
        dias[fechaVenta] += venta.total;
      }
    }

    return Object.entries(dias).map(([fecha, total]) => ({ fecha, total }));
  });

  ngOnInit(): void {
    this.ventas.reload();
    this.pedidos.reload();
    this.stock.reload();
  }

  filtrar() {
    console.log('Filtrar clicado');
  }

  exportar() {
    console.log('Exportar clicado');
  }
}
