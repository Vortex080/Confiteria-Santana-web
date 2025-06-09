import { Component } from '@angular/core';

export interface Product {
  name: string;
  description: string;
  price: number;
  unit: string;
  alergens: number[];         // o Long[] si usas otro mapeo
  category: number;
  photos: ProductPhotoDTO[];
}

export interface ProductPhotoDTO {
  id: number;
  url: string;
}

export interface CartItem {
  product: Product;
  quantity: number;
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
})
export class CartComponent {
  cartItems: CartItem[] = [
    {
      product: {
        name: 'Empanada',
        description: 'Empanada de carne',
        price: 3,
        unit: 'unidad',
        alergens: [1, 2],
        category: 1,
        photos: [{ id: 1, url: 'https://via.placeholder.com/80' }]
      },
      quantity: 2
    },
    {
      product: {
        name: 'Croqueta',
        description: 'Croqueta de jamón',
        price: 1.5,
        unit: 'unidad',
        alergens: [3],
        category: 2,
        photos: [{ id: 2, url: 'https://via.placeholder.com/80' }]
      },
      quantity: 4
    }
  ];

  get total(): number {
    return this.cartItems.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
  }

  removeItem(index: number) {
    this.cartItems.splice(index, 1);
  }

  makeOrder() {
    console.log('Pedido realizado:', this.cartItems);
  }
}