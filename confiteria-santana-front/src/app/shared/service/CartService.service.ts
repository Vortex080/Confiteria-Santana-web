// cart.service.ts
import { Injectable } from '@angular/core';
import { Product } from '../interface/product';

export interface CartItem {
    product: Product;
    quantity: number;
}

@Injectable({ providedIn: 'root' })
export class CartService {
    private readonly key = 'cart';
    private items: CartItem[] = [];

    constructor() {
        this.load();
    }

    private load() {
        const data = localStorage.getItem(this.key);
        this.items = data ? JSON.parse(data) : [];
    }

    private save() {
        localStorage.setItem(this.key, JSON.stringify(this.items));
    }

    getItems(): CartItem[] {
        return this.items;
    }

    addProduct(product: Product, quantity = 1) {
        const existing = this.items.find(i => i.product.id === product.id);
        if (existing) {
            existing.quantity += quantity;
        } else {
            this.items.push({ product, quantity });
        }
        this.save();
    }

    removeItem(index: number): void {
        if (index >= 0 && index < this.items.length) {
            this.items.splice(index, 1);
            this.save();
        }
    }

    clearCart(): void {
        this.items = [];
        this.save();
    }

    updateItem(index: number, quantity: number): void {
        if (index >= 0 && index < this.items.length) {
            this.items[index].quantity = quantity;
            this.save();
        }
    }

    getCount(): number {
        return this.items.reduce((acc, item) => acc + item.quantity, 0);
    }
}
