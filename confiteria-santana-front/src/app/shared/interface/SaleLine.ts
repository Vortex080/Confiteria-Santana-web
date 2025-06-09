import { Product } from "./product";

export interface SaleLine {
    id: number;
    product: Product;
    cuantity: number;
    price: number;
    subtotal: number;
}